#!/usr/bin/python
DOCUMENTATION = '''
module: db_validation
short_description: "Connect to DB"
author:
- muthukumarc
requirements:
- Jinja2
- requests (for rest requests)
options:
setup_ftp_client:
description:
		"db_ip": {"required": True, "type": "str"},
		"db_port": {"required": True, "type": "str"},
		"db_username": {"required": True, "type": "str"},
		"db_password": {"required": False, "type": "str" },
		"dbname": {"required": False, "type": "str" },
		"as2_expected_txns": {"required": True, "type": "str" },
		"ftp_expected_txns": {"required": True, "type": "str" },
		"sshftp_expected_txns": {"required": True, "type": "str" }
example:  db_validation: db_ip="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
from base64 import b64encode
import os
import yaml
import datetime
import importlib
import mysql.connector
from mysql.connector import Error

mysql_db_records={}
protocols_txns={}
def install_package(db_package):
	try:
		pip.main(['install', db_package])
		importlib.import_module(db_package)
	except Exception, e:
		print "Exception on DB Driver Installation"

def query_dbrecords(ip,port,username,password,dbname,wait_time):
	try:	
		status,res=query_mysql(ip,port,username,password,dbname,wait_time)
		for each_record in res:
			print "Each Protocol Record is",res[each_record][4],res[each_record][1]+'_Txns'
			if((res[each_record][4]) >= (protocols_txns[res[each_record][1]+'_Txns'])):
				protocols_txns[res[each_record][1]+'_Status']='Success'
			else:
				protocols_txns[res[each_record][1]+'_Status']='Failure'
		for key,value in protocols_txns.iteritems():
			print "Each protocols_txns Record is",key,value
			if('Status' in key and value == 'Failure'):
				print "Key ",key,"Value",value
				result={'changed': False, 'msg': res}
				return False,result
		return True,res
	except Exception,e:
		print "Exception on query_dbrecords",e
		
	
def query_mysql(ip,port,username,password,dbname,sleep_time):
	cnt=0
	max_retry=1
	while cnt <= max_retry:
		try:
			print "Count Value-<",cnt
			cnx = mysql.connector.connect(host=ip,port=port,user=username, password=password, database=dbname)
			cursor = cnx.cursor()
			query = ("select VLSerial,Transport,Status,Direction,count(*) as Total from VLTransfers group by VLSerial,Transport,Status,Direction")
			cursor.execute(query)
			results = cursor.fetchall()
			print "DB Results",results
			for row in results:
				print row[1],row[4]
				mysql_db_records[row[1]+'_'+row[3]+'_reocrds']=row
			if(cnt == max_retry):
				cursor.close()
				cnx.close()
				print "MYSQL Records",mysql_db_records
				return True,mysql_db_records
			else:
				cursor.close()
				cnx.close()
				print "Count->",mysql_db_records
				raise ValueError('DB Count Not mathcing with ..so..Raising Exception and Retrying')
		except Exception,e:
			print "Exception on query_mysql",e
			time.sleep(sleep_time)
			cnt+=1
			if(cnt>max_retry):
				raise e,mysql_db_records
		# finally:
			# if(cursor!= None):
				# cursor.close()
			# if(cnx!= None):
				# cnx.close()			
	
def main():
	try:
		fields = {
			"db_ip": {"required": True, "type": "str"},
			"db_port": {"required": True, "type": "str"},
			"db_username": {"required": True, "type": "str"},
			"db_password": {"required": False, "type": "str" },
			"dbname": {"required": False, "type": "str" },
			"as2_expected_txns": {"required": False, "type": "int" },
			"ftp_expected_txns": {"required": False, "type": "int" },
			"sshftp_expected_txns": {"required": False, "type": "int" },
			"wait_time": {"required": False, "type": "int"}
		}
		module = AnsibleModule(argument_spec=fields)
		if(module.params['as2_expected_txns'] > 0):
			protocols_txns['AS2/HTTP_Txns']=module.params['as2_expected_txns']
		if(module.params['ftp_expected_txns'] > 0):
			protocols_txns['FTP_Txns']=module.params['ftp_expected_txns']
			protocols_txns['FTPs_Txns']=module.params['ftp_expected_txns']
		if(module.params['sshftp_expected_txns'] > 0):
			protocols_txns['SSH FTP_Txns']=module.params['sshftp_expected_txns']
		stat,result=query_dbrecords(module.params['db_ip'],module.params['db_port'],module.params['db_username'],module.params['db_password'],module.params['dbname'],module.params['wait_time'])
		if stat:
			module.exit_json(meta=result)
		else:
			module.fail_json(**result)
	except Exception,e:
		print "Exception on Main",e
	
if __name__ == "__main__":
	main()
#!/usr/bin/python
DOCUMENTATION = '''
module: query_jdbc
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
import os
import jaydebeapi
from jaydebeapi import _DEFAULT_CONVERTERS, _java_to_py
_DEFAULT_CONVERTERS.update({'BIGINT': _java_to_py('longValue')})

db_records={}
protocols_txns={}
def install_package(db_package):
	try:
		pip.main(['install', db_package])
		importlib.import_module(db_package)
	except Exception, e:
		print "Exception on DB Driver Installation"

def query_dbrecords(ip,port,driver_str,jdbc_string,jar_path,username,password,dbname,wait_time):
	try:	
		status,res=query_jdbc(ip,port,driver_str,jdbc_string,jar_path,username,password,dbname,wait_time)
		for each_record in res:
			print "Each Protocol Record is",res[each_record][3],res[each_record][0]+'_Txns'
			if((res[each_record][3]) >= (protocols_txns[res[each_record][0]+'_Txns'])):
				protocols_txns[res[each_record][0]+'_Status']='Success'
			else:
				protocols_txns[res[each_record][0]+'_Status']='Failure'
		for key,value in protocols_txns.iteritems():
			print "Each protocols_txns Record is",key,value
			if('Status' in key and value == 'Failure'):
				print "Key ",key,"Value",value
				result={'changed': False, 'msg': res}
				return False,result
		return True,res
	except Exception,e:
		print "Exception on query_dbrecords",e

def query_jdbc(ip,port,driver_str,jdbc_string,jar_path,username,password,dbname,sleep_time):
	print "Querying JDBC based DB"
	cnt=0
	max_retry=1
	while cnt <= max_retry:
		try:
			print "Count Value-<",cnt			
			# jHome = jpype.getDefaultJVMPath()
			# jpype.startJVM(jHome, "-Djava.class.path=/etc/oracle/oracle_jdbc-11.2.0.4.0.jar")
			#jdbc_string="jdbc:oracle:thin:"+username+"/"+password+"@//"+ip+":"+port+"/"+dbname
			#jdbc_string="jdbc:mysql:"+username+"/"+password+"@//"+ip+":"+port+"/"+dbname
			#mysq_jdbc_string="jdbc:mysql://"+ip+":"+port+"/"+dbname
			conn = jaydebeapi.connect(driver_str,jdbc_string,[username,password],jar_path)
			cursor = conn.cursor()
			query = ("select Transport,Status,Direction,count(*) as Total from VLTransfers group by Transport,Status,Direction")
			cursor.execute(query)
			results = cursor.fetchall()
			# yield "DB Results",results
			for row in results:
				#yield row[0],row[3]
				db_records[row[0]+'_'+row[2]+'_reocrds']=row
			if(cnt == max_retry):
				cursor.close()
				conn.close()
				print "Records from DB",db_records
				return True,db_records
			else:
				cursor.close()
				conn.close()
				print "Count->",db_records
				raise ValueError('DB Count Not mathcing with ..so..Raising Exception and Retrying')
		except Exception,e:
			print "Exception on query_jdbc",e
			time.sleep(sleep_time)
			cnt+=1
			if(cnt>max_retry):
				raise e,db_records	

def main():
	try:
		# db_ip="10.80.80.215"
		# db_port="1521"
		# driver_str="oracle.jdbc.OracleDriver"
		# jar_path="/etc/oracle/"
		# db_username="system"
		# db_password="oracle"
		# dbname="xe"
		# sleep_time=10
		# jdbc_string="jdbc:oracle:thin:"+db_username+"/"+db_password+"@//"+db_ip+":"+db_port+"/"+dbname
		# status,result=query_dbrecords(db_ip,db_port,driver_str,jdbc_string,jar_path,db_username,db_password,dbname,sleep_time)
		# print "Returned Status",status
		fields = {
			"db_ip": {"required": True, "type": "str"},
			"db_port": {"required": True, "type": "str"},
			"driver_str": {"required": True, "type": "str" },
			"jdbc_string": {"required": True, "type": "str" },
			"jar_path": {"required": True, "type": "str" },
			"db_username": {"required": True, "type": "str"},
			"db_password": {"required": True, "type": "str" },
			"dbname": {"required": True, "type": "str" },
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
		print "Driver String",module.params['driver_str']
		print "jdbc_string",module.params['jdbc_string']
		print "db_ip",module.params['db_ip']
		print "db_port",module.params['db_port']
		print "db_username",module.params['db_username']
		print "db_password",module.params['db_password']
		print "dbname",module.params['dbname']
		stat,result=query_dbrecords(module.params['db_ip'],module.params['db_port'],module.params['driver_str'],module.params['jdbc_string'],module.params['jar_path'],module.params['db_username'],module.params['db_password'],module.params['dbname'],module.params['wait_time'])
		if stat:
			module.exit_json(meta=result)
		else:
			module.fail_json(**result)
	except Exception,e:
		print "Exception on Main",e
	
if __name__ == "__main__":
	main()
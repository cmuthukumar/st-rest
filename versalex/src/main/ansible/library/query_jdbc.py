#!/usr/bin/python
DOCUMENTATION = '''
module: query_jdbc
short_description: "Validates DB transactions against expected transactins passed by user"
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
	result={}
	try:	
		status,res=query_jdbc(ip,port,driver_str,jdbc_string,jar_path,username,password,dbname,wait_time)
		for each_record in res:
			# if(len(res[each_record][0]) <=1):
				# recd_list=list(res[each_record])
				# recd_list[0]='EMPTY'
				# res[each_record]=tuple(recd_list)
			#print "Each Protocol Record is",res[each_record][3],res[each_record][0]+'_Txns'
			if( (res[each_record][0]+'_Txns') in protocols_txns):
				if((res[each_record][3]) == (protocols_txns[res[each_record][0]+'_Txns'])):
					protocols_txns[res[each_record][0]+'_Status']='Success'
				else:
					protocols_txns[res[each_record][0]+'_Status']='Failure'
			for key,value in protocols_txns.iteritems():
				print "Each protocols_txns Record is",key,value
				if('Status' in key and value == 'Failure'):
					print "Key ",key,"Value",value
					result={'changed': False, 'msg': res}
					return False,result
		result={'changed': True, 'msg': res}
		return True,result
	except Exception,e:
		print "Exception on query_dbrecords",e
		raise e

def query_jdbc(ip,port,driver_str,jdbc_string,jar_path,username,password,dbname,sleep_time):
	print "Querying JDBC based DB"
	cnt=0
	conn = None
	cursor = None
	max_retry=10
	while cnt <= max_retry:
		try:
			print "Count Value-<",cnt			
			# jHome = jpype.getDefaultJVMPath()
			# jpype.startJVM(jHome, "-Djava.class.path=/etc/oracle/oracle_jdbc-11.2.0.4.0.jar")
			#jdbc_string="jdbc:oracle:thin:"+username+"/"+password+"@//"+ip+":"+port+"/"+dbname
			#jdbc_string="jdbc:mysql:"+username+"/"+password+"@//"+ip+":"+port+"/"+dbname
			#mysq_jdbc_string="jdbc:mysql://"+ip+":"+port+"/"+dbname
			print "JDBC String to Connect*****",jdbc_string
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
				print "DB Records->",db_records
				raise ValueError('DB Count Not mathcing with ..so..Raising Exception and Retrying')
		except Exception,e:
			print "Exception on query_jdbc",e
			time.sleep(sleep_time)
			cnt+=1
			if(cnt>max_retry):
				print "Count and Retries REACHED***",cnt
				raise e


def main():
	try:
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
		stat,result=query_dbrecords(module.params['db_ip'],module.params['db_port'],module.params['driver_str'],module.params['jdbc_string'],module.params['jar_path'],module.params['db_username'],module.params['db_password'],module.params['dbname'],module.params['wait_time'])
		if stat:
			module.exit_json(**result)
		else:
			module.fail_json(**result)
	except Exception,e:
		print "Exception on Main",e
	
if __name__ == "__main__":
	main()	
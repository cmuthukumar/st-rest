#!/usr/bin/python
DOCUMENTATION = '''
module: del_conn
short_description: "Create Host Vars Based on User Passed Configs"
author:
  - muthukumarc
requirements:
  - only standard library needed
options:
  usr_src:
    description:
      - user passed file path
      required: true
      default: null   
example:  del_conn: usr_src="user_filepath"  default_src="default_filepath"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
import os

def delete_connection(server_hosts):
	try:
		for srv_host in server_hosts:
			get_url ="http://"+srv_host+":5080/api/connections"
			head ={ 'Content-type':'application/json','Accept':'application/json'}
			results = requests.get(get_url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'))
			json_res=json.loads(results.text)
			print "COUNT",json_res['count']
			for each_id in json_res['resources']:
				print "Each ID",each_id['id']
				del_url=get_url+"/"+each_id['id']
				del_res = requests.delete(del_url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'))
				print "Del Res",del_res
	except Exception,e:
		print "Exception on delete_connection method",e

def delete_certs(server_hosts):
	try:
		print "***********Deleting Certs***********"
		for srv_host in server_hosts:
			get_url ="http://"+srv_host+":5080/api/certs"
			head ={ 'Content-type':'application/json','Accept':'application/json'}
			results = requests.get(get_url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'))
			json_res=json.loads(results.text)
			print "COUNT",json_res['count']
			for each_id in json_res['resources']:
				print "Each ID",each_id['id']
				del_url=get_url+"/"+each_id['id']
				del_res = requests.delete(del_url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'))
				del_json_res=json.loads(del_res.text)
				print "Del Res",del_json_res
	except Exception,e:
		print "Exception on delete_connection method",e

def main():
	server_hosts=["198.199.70.70","192.81.217.179"]
	tphosts=["192.241.141.183","162.243.167.177"]
	delete_certs(server_hosts)
	delete_certs(tphosts)
	delete_connection(server_hosts)
	delete_connection(tphosts)
	
if __name__ == "__main__":
    main()
    

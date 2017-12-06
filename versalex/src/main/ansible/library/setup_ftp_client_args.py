#!/usr/bin/python
DOCUMENTATION = '''
module: setup_ftp_client_args
short_description: "Creates FTP Client Profiles and Setups in Both Server and TP Nodes"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  setup_ftp_client:
    description:
		"host_name": {"required": True, "type": "str"},
		"server_hosts": {"required": True, "type": "list"},
		"proxy_hosts": {"required": False, "type": "list" },
		"tp_hosts": {"required": True, "type": "list" },
		"dataset": {"required": True, "type": "dict" }, 
example:  setup_ftp_client: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
import os
import yaml

json_res= {}
	
def get_totalhost(dataset,protocol_name):
	try:	
		for k,val in dataset.iteritems():
			print "Key",k,"Val",val['total']
			if k == protocol_name:
				total=val['total']				
				return int(total)
	except Exception,e:
		print "Exception on get_totalhost method",e

def setup_connection(hostname,master_ip,partner_ip):
	try:
		print "*****************************setup_connection*****************************"
		conn_json_req['host_name']=hostname
		conn_json_req['partner_ip']=partner_ip
		conn_json_req['partner_port']=9021
		conn_json_path=create_conn_json(hostname,conn_json_req)
		conn_json_results=create_connection(master_ip,conn_json_path)
		print "Connnection Results",conn_json_results
		print "*****************************setup_connection END*****************************"		
	except Exception,e:
		print "Exception on setup_connection method",e		
		
def setup_ftp_args_with_proxy(host_ip,server_hosts,tphosts,dataset,proxy_hosts):
	try:
		tp_cnt=len(tphosts)
		total_host=get_totalhost(dataset,'ftp')
		host_pernode=(total_host/tp_cnt)
		start=1
		end=int(host_pernode)
		for tp_ip in tphosts:
			json_res[tp_ip]={'sender_ip':tp_ip,'partner_ip':proxy_hosts[tphosts.index(tp_ip)],'hst_rnge':str(start)+"-"+str(end)}
			# dataset[tp_ip]={'sender_ip':tp_ip,'partner_ip':proxy_hosts[tphosts.index(tp_ip)],'hst_rnge':str(start)+"-"+str(end)}			
			for i in range(start,end+1):
				print "I Value",i
			start=int(start)+int(host_pernode)
			end=end+int(host_pernode)
		return True,json_res
	except Exception,e:
		print "Exception on setup_ftp_args_with_proxy",e
		return False,e

		
def setup_ftp_args_without_proxy(host_ip,server_hosts,tphosts,dataset):
	try:
		tp_cnt=len(tphosts)
		total_host=get_totalhost(dataset,'ftp')
		host_pernode=(total_host/tp_cnt)
		start=1
		end=int(host_pernode)
		for tp_ip in tphosts:
			print "TP Host Index",tphosts.index(tp_ip)
			print "Server Hosts",server_hosts[tphosts.index(tp_ip)]
			print "TP Hosts",tphosts[tphosts.index(tp_ip)]
			json_res[tp_ip]={'sender_ip':tp_ip,'partner_ip':server_hosts[tphosts.index(tp_ip)],'hst_rnge':str(start)+"-"+str(end)}
			# dataset[tp_ip]={'sender_ip':tp_ip,'partner_ip':server_hosts[tphosts.index(tp_ip)],'hst_rnge':str(start)+"-"+str(end)}			
			for i in range(start,end+1):
				start=start+int(host_pernode)
				end=end+int(host_pernode)
		return True,json_res
	except Exception,e:
		print "Exception on setup_ftp_args_without_proxy",e
		return False,e
		
def main():
	try:
		# host_ip= "208.68.39.162"
		# server_hosts=["67.205.173.252","208.68.38.194"]
		# tp_hosts=["208.68.39.162","192.34.57.241"]
		# dataset={'ftp': { 'total': 30,'filespermin': '40',	'totalmins': '10','threadcount':'10','fileSizes':'5000 10000 20000 40000 80000 100000'}}
		# result=setup_ftp_args_without_proxy(tp_host_ip,server_hosts,tp_hosts,dataset)
		# print "FTP Client Json Args",result
		
		fields = {
			"host_ip": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
		}
		module = AnsibleModule(argument_spec=fields)

		if (len(module.params['proxy_hosts']) >= 1):
			stat,result=setup_ftp_args_with_proxy(module.params['host_ip'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'],module.params['proxy_hosts'])
		else:
			stat,result=setup_ftp_args_without_proxy(module.params['host_ip'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'])
		
		if stat:
			module.exit_json(meta=result)
		else:
			module.fail_json(meta=result)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
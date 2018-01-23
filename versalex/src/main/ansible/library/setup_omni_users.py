#!/usr/bin/python
DOCUMENTATION = '''
module: setup_omni_users
short_description: "Creates AS2 Profiles and Setups in Both Server and TP Nodes"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  setup_as2:
    description:
			"host_name": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
			"default_home_dir" : {"required": True, "type": "str" }		
example:  setup_omni_users: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
import os
import yaml

omni_host_req= {}
omni_mailbox_req={}

def get_jsonoutput(json_path,render_content):
	try:
		path, filename = os.path.split(json_path)
		env = Environment(loader=FileSystemLoader(path or './'))
		template = env.get_template(filename)    
		output= template.render(render_content)
		return output
	except Exception,e:
		print "Exception on get Json Output",e
		
def get_postresults(url,json_file):
	try:
		if type(json_file) is dict:
			json_data=json_file
		else:
			with open(json_file, 'r') as jsfile:
				json_data=json.load(jsfile)
		print "Cert Json DATA-",json_data
		head ={ 'Content-type':'application/json','Accept':'application/json'}
		results = requests.post(url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'),data=json.dumps(json_data))
		json_res=json.loads(results.text)
		print "FINAL ****RES**",json_res
		return json_res
	except Exception,e:
		print "Exception on get_postresults method",e
		
def create_json_file(host_name,json_req_path,json_req):
	try:
		conn_output=get_jsonoutput(json_req_path,json_req)
		# print "Connection Output",conn_output
		if not os.path.exists("./files/"+json_req['host_type']+"omnijsons/"):
			os.makedirs("./files/"+json_req['host_type']+"omnijsons/")
		conn_json_path="./files/"+json_req['host_type']+"omnijsons/"+host_name+".json"
		with open(conn_json_path, 'w+') as jsonfile:
			jsonfile.write(conn_output)
		return conn_json_path
	except Exception, e:
		print "Exception on create_conn_json method",e
		
def post_json(ip_address,json_path,post_url):
	try:
		sender_url ="http://"+ip_address+":5080"+post_url
		print "Json Connection File to POST", json_path
		# json_req['certid']=json_data['id']    
		sender_res = get_postresults(sender_url,json_path)    
		return sender_res
		# print "Final Connec Res",sender_res
	except Exception,e:
		print "Exception on post_json method",e		
		
def get_totalhost(dataset):
	try:	
		for k,val in dataset.iteritems():
			print "Key",k,"Val",val['total']
			if k == omni_host_req['host_type']:
				print "Key is Matching",val['total']
				total=val['total']				
				return int(total)
	except Exception,e:
		print "Exception on get_totalhost method",e

def send_omnihost_json(hostname,master_ip,conn_json_req):
	try:
		print "*****************************send_omnihost_json*****************************"
		json_req_path="./files/OmniHost.json"
		conn_json_path=create_json_file(hostname,json_req_path,conn_json_req)
		conn_json_results=post_json(master_ip,conn_json_path,"/api/authenticators")
		print "Connnection Results",conn_json_results
		return conn_json_results
		print "*****************************send_omnihost_json END*****************************"		
	except Exception,e:
		print "Exception on send_omnihost_json method",e		

def send_omnimailbox_json(hostname,master_ip,conn_json_req):
	try:
		print "*****************************send_omnimailbox_json*****************************"
		json_req_path="./files/OmniMailbox.json"
		conn_json_path=create_json_file(hostname,json_req_path,conn_json_req)
		mailbox_json_results=post_json(master_ip,conn_json_path,("/api/authenticators/"+omni_mailbox_req['id']+"/users"))
		print "Mailbox Results",mailbox_json_results
		return mailbox_json_results
		print "*****************************send_omnimailbox_json END*****************************"		
	except Exception,e:
		print "Exception on send_omnimailbox_json method",e				
		
def setup_omni_host_mailbox(host_name,server_hosts,tphosts,dataset):
	try:
		tp_cnt=len(tphosts)
		master_ip=server_hosts[0]
		total_host=get_totalhost(dataset)
		host_pernode=(total_host/tp_cnt)
		start=1
		print "Total Omni Hosts to Create is",total_host
		for i in range(start,(total_host+1)):
			omni_host_req['hostname']=(host_name+str(i))
			omnihost_results=send_omnihost_json((host_name+str(i)),master_ip,omni_host_req)
			print "OMNI HOST ID Returned",omnihost_results
			omni_mailbox_req['host_type']=omni_host_req['host_type']
			omni_mailbox_req['id']=omnihost_results['id']
			omni_mailbox_req['username']=(host_name+str(i)+"_MBX1")
			omnimbx_results=send_omnimailbox_json((host_name+str(i)+"_MBX1"),master_ip,omni_mailbox_req)
		return True,"success"
	except Exception,e:
		print "Exception on setup_omni_host_mailbox",e
		return False,e
		
def main():
	try:
		# host_name="SSHFTP"
		# server_hosts=["10.20.101.185","10.20.101.193"]
		# proxy_hosts=["10.20.101.186","10.20.101.187"]
		# tphosts=["10.20.101.188","10.20.101.189"]
		# dataset={ "sshftp": { "name": "sshftp" , "total" : 50 } }
		# omni_host_req['host_type']="sshftp"
		# omni_host_req['default_home_dir']="%datashare%/"+omni_host_req['host_type']+"/%username%/"
		fields = {
			"host_name": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
			"host_type" : {"required": True, "type": "str" },
			"default_home_dir" : {"required": True, "type": "str" }
		}
		module = AnsibleModule(argument_spec=fields)
		omni_host_req['host_type']=module.params['host_type']
		omni_host_req['default_home_dir']=module.params['default_home_dir']+"/"+omni_host_req['host_type']+"/%username%/"
		stat,result=setup_omni_host_mailbox(module.params['host_name'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'])			
		if stat:
			module.exit_json(meta=result)
		else:
			module.fail_json(meta=result)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
#!/usr/bin/python
DOCUMENTATION = '''
module: setup_ftp_client
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

conn_json_req= {}

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


def create_conn_json(host_name,json_req):
	try:
		json_req['host_name']=host_name
		conn_output=get_jsonoutput("./files/FTP_Conn.json",json_req)
		# print "Connection Output",conn_output
		conn_json_path="./files/ftpjsons/"+host_name+".json"
		with open(conn_json_path, 'w+') as jsonfile:
			jsonfile.write(conn_output)
		return conn_json_path
	except Exception, e:
		print "Exception on create_conn_json method",e
		
def create_connection(ip_address,json_path):
	try:
		sender_url ="http://"+ip_address+":5080/api/connections"
		print "Json Connection File to POST", json_path
		# json_req['certid']=json_data['id']    
		sender_res = get_postresults(sender_url,json_path)    
		return "success"
		# print "Final Connec Res",sender_res
	except Exception,e:
		print "Exception on create_connection method",e		
		
def get_totalhost(dataset):
	try:	
		for k,val in dataset.iteritems():
			print "Key",k,"Val",val['total']
			if k == 'ftp':
				total=val['total']
				conn_json_req['partner_port']=val['port']
				return int(total)
	except Exception,e:
		print "Exception on get_totalhost method",e

def setup_connection(hostname,master_ip,partner_ip):
	try:
		print "*****************************setup_connection*****************************"
		conn_json_req['host_name']=hostname
		conn_json_req['partner_ip']=partner_ip
		# conn_json_req['partner_port']=9021
		conn_json_path=create_conn_json(hostname,conn_json_req)
		conn_json_results=create_connection(master_ip,conn_json_path)
		print "Connnection Results",conn_json_results
		print "*****************************setup_connection END*****************************"		
	except Exception,e:
		print "Exception on setup_connection method",e		
		

def setup_ftp_with_proxy(host_name,server_hosts,tphosts,dataset,proxy_hosts):
	try:
		tp_cnt=len(tphosts)
		master_ip=server_hosts[0]
		total_host=get_totalhost(dataset)
		host_pernode=(total_host/tp_cnt)
		start=1
		end=int(host_pernode)
		for tp_ip in tphosts:
			print "TP Host Index",tphosts.index(tp_ip)
			print "Server Hosts",server_hosts[tphosts.index(tp_ip)]
			print "TP Hosts",tphosts[tphosts.index(tp_ip)]		
			for i in range(start,end+1):				
				setup_connection((host_name+str(i)+"_MBX1"),tp_ip,proxy_hosts[tphosts.index(tp_ip)])
			start=start+int(host_pernode)
			end=end+int(host_pernode)
		return True,"success"
	except Exception,e:
		print "Exception on setup_as2",e
		return False,e
		
def setup_ftp_without_proxy(host_name,server_hosts,tphosts,dataset):
	try:
		tp_cnt=len(tphosts)
		total_host=get_totalhost(dataset)
		host_pernode=(total_host/tp_cnt)
		start=1
		end=int(host_pernode)
		for tp_ip in tphosts:
			print "TP Host Index",tphosts.index(tp_ip)
			print "Server Hosts",server_hosts[tphosts.index(tp_ip)]
			print "TP Hosts",tphosts[tphosts.index(tp_ip)]		
			for i in range(start,end+1):
				setup_connection((host_name+str(i)+"_MBX1"),tp_ip,server_hosts[tphosts.index(tp_ip)])
			start=start+int(host_pernode)
			end=end+int(host_pernode)
		return True,"success"
	except Exception,e:
		print "Exception on setup_as2",e
		return False,e
		
def main():
	try:
		fields = {
			"host_name": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
		}
		module = AnsibleModule(argument_spec=fields)
		if not os.path.exists("./files/ftpjsons/"):
			os.makedirs("./files/ftpjsons/")
		if (len(module.params['proxy_hosts']) >= 1):
			stat,result=setup_ftp_with_proxy(module.params['host_name'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'],module.params['proxy_hosts'])
		else:
			stat,result=setup_ftp_without_proxy(module.params['host_name'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'])
			
		if stat:
			module.exit_json(meta=result)
		else:
			module.fail_json(meta=result)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
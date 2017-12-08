#!/usr/bin/python
DOCUMENTATION = '''
module: setup_listener_cert
short_description: "Creates Local Listener Certificates and Setups in Both Server and TP Nodes"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  setup_listener_cert.py:
    description:
		"host_ip": {"required": True, "type": "str"},
		"cert_name": {"required": True, "type": "str"},
example:  setup_listener_cert: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''

from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
import subprocess
import os
import yaml

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
		
def create_cert_json(cert_name):
	try:
		alias= {"alias": cert_name}
		cwd = os.getcwd()
		json_out=get_jsonoutput(cwd+"/files/Cert.json",alias)
		json_path=cwd+"/files/as2jsons/"+cert_name+".json"
		with open(json_path, 'w+') as jsonfile:
			jsonfile.write(json_out)
		return json_path
	except Exception, e:
		print "Exception on create_cert_json method",e

		
def create_cert(sender_ip,json_file):
	try:
		sender_url ="http://"+sender_ip+":5080/api/certs"
		print "Cert Json File to POST", json_file,"Sender URL",sender_url
		sender_res = get_postresults(sender_url,json_file)
		print "Cert-Id", sender_res['id']
		return sender_res
	except Exception, e:
		print "Exception on create_cert method",e

def import_cert(partner_ip,cert_code):
	try:
		partner_url ="http://"+partner_ip+":5080/api/certs"
		print "Importing Cert to Receiver", partner_url
		partner_data= { "requestType":"importCert","import":cert_code}
		partner_res = get_postresults(partner_url,partner_data)
		print "Partner-Cert-Id", partner_res['id']
		# print "Partner Results ",partner_res		
		return partner_res
		# print "Final Dump",json_req
	except Exception, e:
		print "Exception on import_cert method",e		
		
	
def setup_local_listener_cert(hostip,cert_name):
	try:
		print "Local Listener Cert to Create",cert_name
		cert_json_path=create_cert_json(cert_name)
		cert_res=create_cert(hostip,cert_json_path)
		# cert_import_res=import_cert(hostip,cert_res['certificate'])
		print "Cert Results",cert_res
		return True,cert_res
	except Exception,e:
		print "Exception on setup_local_listener_cert",e
		return False,e

def setup_cert_commandline(cert_name,tag,value):
	try:
		jmeter_params=''
		k=subprocess.Popen(jmeter_params, env=my_env,shell=True,universal_newlines=True)
		out, err=k.communicate()
		return True,"success"
	except Exception,e:
		print "Exception on setup_cert_commandline ",e
		return False,e
		
def main():
	try:
		fields = {
			"host_ip": {"required": True, "type": "str"},
			"cert_name": {"required": True, "type": "str"},
		}
		module = AnsibleModule(argument_spec=fields)		
		stat,result=setup_local_listener_cert(module.params['host_ip'],module.params['cert_name'])
		if stat:
			module.exit_json(**result)
		else:
			module.fail_json(**result)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
#!/usr/bin/python
DOCUMENTATION = '''
module: create_dropleet_rest
short_description: "Creates AS2 Profiles and Setups in Both Server and TP Nodes"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  create_hosts:
    description:
		"droplet_results": {"required": True, "type": "str"},
example:  create_hosts: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''

from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
import os

droplet_details={}

def get_jsonoutput(json_path,render_content):
	try:
		path, filename = os.path.split(json_path)
		env = Environment(loader=FileSystemLoader(path or './'))
		template = env.get_template(filename)    
		output= template.render(render_content)
		return output
	except Exception,e:
		print "Exception on get Json Output",e

def create_droplet_json(droplet_name,region,ram_size,os_image,sshkey_id,droplet_tag):
	try:
		droplet_details['droplet_name']=droplet_name
		droplet_details['region']=region
		droplet_details['ram_size']=ram_size
		droplet_details['os_image']=os_image
		droplet_details['sshkey_id']=sshkey_id
		droplet_details['droplet_tag']=droplet_tag
		conn_output=get_jsonoutput("./files/Droplet.json",droplet_details)
		# print "Connection Output",conn_output
		if not os.path.exists("./files/dropletjsons/"):
			os.makedirs("./files/dropletjsons/")		
		droplet_json_path="./files/dropletjsons/"+droplet_details['droplet_name']+".json"
		with open(droplet_json_path, 'w+') as jsonfile:
			jsonfile.write(conn_output)
		return droplet_json_path
	except Exception, e:
		print "Exception on create_droplet_json method",e

def get_droplet_details_id(url,droplet_res):
	cnt=0
	max_retry=3
	while cnt < max_retry:
		try:
			print "Droplet ID to Retrieve Details",droplet_res['droplet']['id']
			head ={ 'Content-type':'application/json','Accept':'application/json','Authorization':'Bearer '+droplet_details['do_token']}
			droplet_id_url=url+"/"+str(droplet_res['droplet']['id'])
			print "Droplet URL",droplet_id_url
			droplet_id_results = requests.get(droplet_id_url,headers=head,timeout=120)
			dropet_id_json_res=json.loads(droplet_id_results.text)
			print "DROPLET ID RESULTS*",dropet_id_json_res
			if (dropet_id_json_res['droplet']['networks']['v4']):
				return dropet_id_json_res
			else:
				raise ValueError('Droplet JSON Networks Not found in Response')
		except Exception,e:
			print "Exception on get_droplet_details_id method",e
			time.sleep(10**cnt)			
			cnt += 1
			if cnt >= max_retry:
				raise e
			
			
def get_postresults(url,json_file):
	cnt=0
	max_retry=3
	while cnt < max_retry:
		try:
			if type(json_file) is dict:
				json_data=json_file
			else:
				with open(json_file, 'r') as jsfile:
					json_data=json.load(jsfile)
			print "Json data-",json_data
			bearer_token=droplet_details['do_token']
			head ={ 'Content-type':'application/json','Accept':'application/json','Authorization':'Bearer '+bearer_token}
			results = requests.post(url,headers=head,data=json.dumps(json_data))
			json_res=json.loads(results.text)
			if (json_res['droplet']['id']):
				print "FINAL ****RES**",json_res
				return json_res
			else:
				raise ValueError('Droplet is not Created..Please Retry')
		except Exception,e:
			print "Exception on get_postresults method",e
			time.sleep(10**cnt)			
			cnt += 1
			if cnt >= max_retry:
				raise e
		
def create_machines(json_path):
	try:
		droplet_url ="https://api.digitalocean.com/v2/droplets"
		print "Json Connection File to POST", json_path
		droplet_create_res = get_postresults(droplet_url,json_path) 
		droplet_id_results = get_droplet_details_id(droplet_url,droplet_create_res)		
		return True,droplet_id_results
	except Exception,e:
		print "Exception on create_machines method",e
		return False,'{msg: e}'

def create_hosts(machine_type):
	try:
		if not os.path.exists("./inventories/"+machine_type+"/"):
			os.makedirs("./inventories/"+machine_type+"/")		
		hosts_path="./inventories/"+machine_type+"/hosts"
		with open(hosts_path, 'a+') as jsonfile:
			jsonfile.write(conn_output)
		return droplet_json_path
	except Exception, e:
		print "Exception on create_droplet_json method",e

		
def main():
	try:
		fields = {
			"droplet_name": {"required": True, "type": "str"},
			"region": {"required": True, "type": "str"},
			"ram_size": {"required": True, "type": "str"},
			"os_image": {"required": True, "type": "str"},
			"sshkey_id": {"required": True, "type": "str"},
			"droplet_tag": {"required": True, "type": "str"},
			"do_token": {"required": True, "type": "str"},
		}
		module = AnsibleModule(argument_spec=fields)
		droplet_details['do_token']=module.params['do_token']		
		droplet_json=create_droplet_json(module.params['droplet_name'],module.params['region'],module.params['ram_size'],module.params['os_image'],module.params['sshkey_id'],module.params['droplet_tag'])
		stat,droplet_results=create_machines(droplet_json)
		if stat:
			module.exit_json(**droplet_results)
		else:
			module.fail_json(**droplet_results)
	except Exception,e:
		print "Exception on Main",e
	finally:
		sys.exit()
		
if __name__ == "__main__":
    main()
	
		# for i in range(1,3):
			# droplet_name="servers-test-"+str(i)
			# region="nyc1"
			# ram_size="1gb"
			# os_image="centos-6-x64"
			# sshkey_id= "2b:7f:a2:f9:29:ea:35:2a:e6:74:d2:56:ce:33:b1:4c"
			# droplet_tag= "py_tst"
			# do_token= "3722b41e3aab0efb96af5aa45dbd0c9a358988c8d0bde96ee46b5e82f4aecdb7"
			# droplet_details['do_token']=do_token
			# droplet_json=create_droplet_json(droplet_name,region,ram_size,os_image,sshkey_id,droplet_tag)
			# stat,droplet_results=create_machines(droplet_json)
			# print "DROPLET RES_MU",droplet_results				

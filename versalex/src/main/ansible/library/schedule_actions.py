#!/usr/bin/python
DOCUMENTATION = '''
module: schedule_actions
short_description: "Lists all actions from each node and schedule actions for it"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  setup_ftp_client:
    description:		
		"hosts": {"required": True, "type": "list"},
		example:  schedule_actions:
							hosts: Host IPs to be added for enabling scheduling for actions
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
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

def schedule_actions(server_hosts,schedule_option,action_type):
	try:
		action_json_req= {}
		for srv_host in server_hosts:
			action_base_url="http://"+srv_host+":5080/api/actions"
			get_url ='http://'+srv_host+':5080/api/actions?filter=alias eq "'+action_type+'"'
			#get_url ='http://'+srv_host+':5080/api/actions'
			print "URL to enable Send Actions",get_url
			head ={ 'Content-type':'application/json','Accept':'application/json'}
			results = requests.get(get_url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'))
			json_res=json.loads(results.text)
			print "Total Actions Res",json_res
			if(int(json_res['count']) > 0):
				for each_id in json_res['resources']:
					connection_href=each_id['connection']['href']
					action_json_req['conn_href']=connection_href
					action_json_req['id']=each_id['id']
					action_json_req['schedule_option']=schedule_option
					action_json_req['alias']=action_type
					put_action=action_base_url+"/"+each_id['id']
					action_output=get_jsonoutput("./files/Sched_Action.json",action_json_req)
					sched_action_results = requests.put(put_action,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'),data=action_output)
					print "Schedule Actions Response",sched_action_results					
			else:
					return True,"No Actions are Present"
			return True,"Success"			
	except Exception,e:
		print "Exception on schedule_actions method",e
		return False,e

def main():
 	try:
 		fields = {
 			"hosts": {"required": True, "type": "list"},
			"schedule_option": {"required": True, "type": "str"},
			"action_type": {"required": True, "type": "str"},
 		}
 		module = AnsibleModule(argument_spec=fields)
		stat,result=schedule_actions(module.params['hosts'],module.params['schedule_option'],module.params['action_type'])
 		if stat:
 			module.exit_json(meta=result)
 		else:
 			module.fail_json(meta=result)
 	except Exception,e:
		print "Exception on Main",e
	
if __name__ == "__main__":
    main()
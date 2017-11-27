#!/usr/bin/python
DOCUMENTATION = '''
module: test_conn
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
example:  test_rest: usr_src="user_filepath"  default_src="default_filepath"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
import os


def test_as2(srv_hosts,tphosts,dataset):
	try:
		tp_cnt=len(tphosts)		
		host_pernode=(20/tp_cnt)
		start=1
		end=int(host_pernode)
		print "Host Per Node",host_pernode
		for tp_ip in tphosts:
			print "TP Host Index",tphosts.index(tp_ip)
			print "Server Hosts",srv_hosts[tphosts.index(tp_ip)]
			print "TP Hosts",tphosts[tphosts.index(tp_ip)]
			for i in range(start,end):
				print "Start-",start,"End=-",end
			start=start+int(host_pernode)
			end=end+int(host_pernode)
		return "success",conn_status
	except Exception,e:
		print "Exception on setup_as2",e		
    
def main():
	srv_hosts=["67.205.137.108","67.205.137.109"]
	proxyhosts=[ "192.241.130.81","192.241.146.199"]
	tphosts=["192.241.159.179","192.241.159.180"]
	dataset={'as2': {'action_custom_props': {},'action_range': '1-1','create_certs': 'true','host_custom_props': {},'host_range': '1-20', 'mailbox_custom_props': {},'mailbox_range': '1-1', 'name': 'as2','schedule_actions': 'true','total': 20}}
	result=test_as2(srv_hosts,tphosts,dataset)
	# fields = {
        # "server_master_ip": {"required": True, "type": "str"},
        # "proxyhosts": {"required": True, "type": "list" },
        # "tphosts": {"required": True, "type": "list" },
        # "dataset": {"required": True, "type": "dict" },
    # }
    # module = AnsibleModule(argument_spec=fields)
    # print "ServerMasterIp", module.params['server_master_ip']
    # print "Proxy Hosts", module.params['proxyhosts']
    # print "TPHosts", module.params['tphosts']
    # result=setup_server(module.params['server_master_ip'],module.params['tphosts'],module.params['dataset'])
    # stat, result= build_host_vars(module.params['usr_src'],module.params['hostvars_dest']) 
    # if stat:
        # module.exit_json(meta=json.dumps(result))
    # else:
        # module.fail_json(meta=result)
    
if __name__ == "__main__":
    main()
    

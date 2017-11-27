#!/usr/bin/python
DOCUMENTATION = '''
module: test_rest1
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
  
def main():

	fields = {
		"server_hosts": {"required": True, "type": "list"},
     }
	module = AnsibleModule(argument_spec=fields)
	print "server_hosts--", module.params['server_hosts']

if __name__ == "__main__":
    main()
    

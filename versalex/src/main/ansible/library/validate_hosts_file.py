#!/usr/bin/python
DOCUMENTATION = '''
module: validate_hosts
short_description: "Validation for Hosts file created by ansible"
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
example:  validate_hosts_file: usr_src="user_filepath"  default_src="default_filepath"
'''
from ansible.module_utils.basic import *

import yaml
import os

def validate_hosts(machine_type,hosts_names):
	try:
		usr_src="./files/"+machine_type+".yml"
		srvr_stream = open(usr_src, "r")
		srvrs = yaml.load(srvr_stream)
		parent= {}
		host_missing= None
		for k,v in srvrs.items():
			if k == 'hardware':
				for lk,lv in srvrs[k].items():
					print "LK-",lk,"LV-",lv
					for key in lv:
						print "key-",key
						for dk,dv in key.items():
							if dk == 'qty':
								print "dk-",dk,"-dv-",dv
								for i in range(1,int(dv)+1):
									usr_host_name=machine_type+"-"+lk+"-"+str(i)
									for host_name in hosts_names:
										print "User HostName_",usr_host_name,"SystemName",host_name
										if usr_host_name != host_name:
											host_missing = True
										else:
											host_missing = False
											break
									if host_missing:
										return False,{"msg": "Host is missing -"+usr_host_name}
									else:
										continue
		hostnames_status={"msg":"All Hosts are Present in Host File"}
		return True,hostnames_status
	except Exception,e:
		print "Exception on get Json Output",e
		return False,{"msg":e}
def main():
	# machine_type="servers"
	# hosts_list=["servers-shares-1","servers-proxy-1","servers-proxy-2","servers-versalex-1","servers-versalex-2"]
	# stat,results=validate_hosts(machine_type,hosts_list)
	fields = {
		"machine_type": {"required": True, "type": "str"},
		"host_names": {"required": True, "type": "list" },
	}
	module = AnsibleModule(argument_spec=fields)
	stat,results=validate_hosts(module.params['machine_type'],module.params['host_names']) 
	if stat:
		module.exit_json(meta=results)
	else:
		module.fail_json(meta=results)
    
if __name__ == "__main__":
    main()
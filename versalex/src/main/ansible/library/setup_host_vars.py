#!/usr/bin/python
DOCUMENTATION = '''
module: setup_host_vars
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
example:  setup_host_vars: usr_src="user_filepath"  default_src="default_filepath"
'''
from ansible.module_utils.basic import *

import yaml
import os

def build_host_vars(usr_src,hostvars_dest):
    srvr_stream = open(usr_src, "r")
    srvrs = yaml.load(srvr_stream)
    parent= {}
    for k,v in srvrs.items():
        if k != 'hardware':
            for lk,lv in srvrs[k].items():
                if lk in os.path.basename(hostvars_dest):
                    if k == 'dataset':
                        lk='dataset'                
                    parent[lk] ={}
                    for key in lv:
                        parent[lk][key['name']] ={}
                        for dk,dv in key.items():                   
                            key_name=key['name']
                        # print "Def Val",defa[dk][key_name]
                            parent[lk][key_name][dk] =dv 
    f= open(hostvars_dest, 'w+')  
    try:
        yaml.dump(parent, f, default_flow_style=False)
        result= {"status": "success", "hostVar_path": f.name}
        return True,result
    except yaml.YAMLError as err:
        result= {"status": "Failed", "Err": err}
        return False,result
            
def main():
    
    fields = {
        "usr_src": {"required": True, "type": "str"},
        "hostvars_dest": {"required": True, "type": "str" },
    }
    module = AnsibleModule(argument_spec=fields)
    stat, result= build_host_vars(module.params['usr_src'],module.params['hostvars_dest']) 
    if stat:
        module.exit_json(meta=result)
    else:
        module.fail_json(meta=result)
    
if __name__ == "__main__":
    main()
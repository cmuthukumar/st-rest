#!/usr/bin/python
DOCUMENTATION = '''
module: setup_group_vars
short_description: "create Group Vars Based on User and Defaults Configs"
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
  default_src:
    description:
      - default file path exists in system
      required: true
      default: null      
example:  setup_group_vars: usr_src="user_filepath"  default_src="default_filepath"
'''
from ansible.module_utils.basic import *

import yaml
import os


def build_grp_vars(usr_src,default_src):
    fileDir = os.path.dirname(os.path.realpath('__file__'))   
    # srvr_stream = open(os.path.join(fileDir, 'files/'+usr_src), "r") 
    srvr_stream = open(usr_src, "r")
    srvrs = yaml.load(srvr_stream)
    def_stream = open(default_src, "r")
    defa = yaml.load(def_stream)
    parent= {}
    prod_path = {}
    for k,v in srvrs.items():
        if k != 'hardware':
            for lk,lv in srvrs[k].items():
                if k == 'dataset':
                    lk='dataset'
                parent[lk] ={}
                prod_grp = {}
                prod_grp[lk] = {}
                for key in lv:
                    parent[lk][key['name']] ={}
                    prod_grp[lk][key['name']] ={}
                    for dk,dv in defa.items():                   
                        key_name=key['name']
                        # print "Def Val",defa[dk][key_name]
                        for dpk,dpv in defa[dk][key_name].items():
                            #print "FinalKey",dpk,"FinalVal",dpv
                            parent[lk][key['name']][dpk] =dpv
                            prod_grp[lk][key['name']][dpk] =dpv                            
                            prod_dest = lk + ".yml"
                            prod_fpath=open(os.path.join(fileDir, 'group_vars/'+prod_dest),'w+')
                            try:
                                yaml.dump(prod_grp, prod_fpath, default_flow_style=False)
                                # prod_path.update({lk: prod_fpath })
                            except yaml.YAMLError as err:
                                print err
    f= open(os.path.join(fileDir, 'group_vars/'+os.path.basename(usr_src)), 'w+')  
    try:
        yaml.dump(parent, f, default_flow_style=False)
        result= {"status": "success", "groupVar_path": f.name}
        return True,result
    except yaml.YAMLError as err:
        result= {"status": "Failed", "Err": err}
        return False,result
            
def main():
    
    fields = {
        "usr_src": {"required": True, "type": "str"},
        "default_src": {"required": True, "type": "str" },
    }
    module = AnsibleModule(argument_spec=fields)
    stat, result= build_grp_vars(module.params['usr_src'],module.params['default_src']) 
    if stat:
        module.exit_json(meta=result)
    else:
        module.fail_json(meta=result)
    
if __name__ == "__main__":
    main()
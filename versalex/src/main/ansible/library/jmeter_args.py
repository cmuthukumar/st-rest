#!/usr/bin/python
DOCUMENTATION = '''
module: jmeter_args
short_description: "Lists all actions from each node and schedule actions for it"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  jmeter_args:
    description:		
		"hosts": {"required": True, "type": "list"},
		example:  schedule_actions:
							hosts: Host IPs to be added for enabling scheduling for actions
'''

import os
import yaml
import subprocess
from ansible.module_utils.basic import *

def convert_dict_jmeter_args(protocols,jmx_path):
	try:
		# my_env = os.environ.copy()
		# my_env["PATH"] = "/root/jmeter/apache-jmeter-3.3/bin:" + my_env["PATH"]
		# p=subprocess.Popen(['jmeter','--version'], env=my_env)
		# print p.communicate()
		jmeter_args= {}	
		for k,val in protocols.iteritems():
			print "Key to Test",k
			print "value to test",val
			if('filespermin' in val):
				for key,values in val.iteritems():				
					jmeter_args['-J'+k+'.'+key]=values
				jmeter_args['-J'+k+'.loopcount']=(int(val['filespermin'])*int(val['totalmins'])/int(val['threads']))
		print "Jmeter ARGS Dict",jmeter_args
		jmeter_args_str=str(jmeter_args)
		convert_jmeter_args=jmeter_args_str.replace(':','=').translate(None,'{}')
		convert_jmeter_args=convert_jmeter_args.replace('= ','=').replace(',','')
		jmeter_params='jmeter -n -t '+jmx_path+' '+convert_jmeter_args
		print "Final Jmeter Params",jmeter_params
		# k=subprocess.Popen(jmeter_params, env=my_env,shell=True,universal_newlines=True)
		# out, err=k.communicate()
		return True,jmeter_params
	except Exception,e:
		print(traceback.format_exc())
		print "Exception on Main for Convert Dict to Jmeter Args",e
		return False,e

def main():
 	try:
		#dataset={'as2': { 'filespermin': '40',	'totalmins': '10','threadcount':'10','fileSizes':'5000 10000 20000 40000 80000 100000'}}
		#jmx_path='/root/jmeter/apache-jmeter-3.3/bin/SysTest_Linux_New.jmx'
		#convert_dict_jmeter_args(dataset,jmx_path)
 		fields = {
 			"jmx": {"required": True, "type": "str"},
			"protocols": {"required": True, "type": "dict"}
 		}
 		module = AnsibleModule(argument_spec=fields)
		stat,res=convert_dict_jmeter_args(module.params['protocols'],module.params['jmx'])
 		if stat:
 			module.exit_json(meta=res)
 		else:
 			module.fail_json(meta=res)
 	except Exception,e:
		print "Exception on Main for Convert Dict to Jmeter Args",e
	
if __name__ == "__main__":
    main()
	
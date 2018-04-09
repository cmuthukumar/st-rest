#!/usr/bin/python
DOCUMENTATION = '''
module: setup_ui
short_description: "Setup GUI Interface Groovy Scrript"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  setup_ui:
    description:
			"host_name": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
			"default_home_dir" : {"required": True, "type": "str" }		
example:  setup_ui: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
from distutils.version import LooseVersion, StrictVersion
import os
import yaml

snapshotVersions=[]

def get_results(snap_url):
	cnt=1
	max_retry=10
	while cnt < max_retry:
		try:			
			#url="http://10.80.80.156/nexus/service/local/lucene/search?g=com.cleo.installers&a=Harmony&repositoryId=InstallerSnapshots"
			head ={ 'Content-type':'application/json','Accept':'application/json'}
			results = requests.get(snap_url,headers=head,auth=HTTPBasicAuth('admin', 'admin123'))
			json_res = json.loads(results.text)
			print "FINAL ****RES**",json_res
			return True,json_res
		except Exception,e:
			print "Exception on get_postresults method",e
			time.sleep(10**cnt)			
			cnt += 1
			if cnt >= max_retry:
				raise e		
		
def get_json():
	try:
		sender_url ="http://"+ip_address+":5080"+post_url
		print "Json Connection File to POST", json_path
		# json_req['certid']=json_data['id']    
		sender_res = get_postresults(sender_url,json_path)    
		return sender_res
		# print "Final Connec Res",sender_res
	except Exception,e:
		print "Exception on post_json method",e
		
def setup_ui_groovy(snapshot_url):
	try:
		status,json_res=get_results(snapshot_url)
		for i in range(len(json_res['data'])):
			print "Version is ---",json_res['data'][i]['version']
			ver=json_res['data'][i]['version']
			if( LooseVersion(ver) > LooseVersion("5.4.1") ):
				snapshotVersions.append(json_res['data'][i]['version'])
		print "RESULTS RETURNED***",snapshotVersions			
		snapshotRes = {'changed': True,'msg': snapshotVersions}
		return True,snapshotRes
	except Exception,e:
		print "Exception on setup_ui_groovy ",e
		snapshotRes = {'changed': False,'msg': e}
		return False,snapshotRes

		
def main():
	try:
		fields = {
			"snapshot_url": {"required": True, "type": "str"}
			}
		module = AnsibleModule(argument_spec=fields)		
		stat,result=setup_ui_groovy(module.params['snapshot_url'])
		if stat:
			module.exit_json(**result)
		else:
			module.fail_json(**result)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
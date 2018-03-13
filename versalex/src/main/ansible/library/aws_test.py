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

import boto.ec2
		
def main():
	try:
		conn = boto.ec2.connect_to_region('ap-south-1', aws_access_key_id='AKIAIC46W7N2OC3KTSFA', aws_secret_access_key='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')
		regions = conn.get_all_regions()
		print(regions)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
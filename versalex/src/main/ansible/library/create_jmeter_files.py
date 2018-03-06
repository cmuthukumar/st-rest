#!/usr/bin/python
DOCUMENTATION = '''
module: create_jmeter_files
short_description: "Setup GUI Interface Groovy Scrript"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  create_file_size:
    description:
			"host_name": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
			"default_home_dir" : {"required": True, "type": "str" }		
example:  create_file_size: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''
from ansible.module_utils.basic import *
import os
import os.path
import string
import random

def create_src_files(protocol_type,jmeterPath,filesize_range):
	try:
		fSizes=[]
		if '-' in filesize_range:
			fSizes=filesize_range.split('-')
		else:
			print "File Size Index",fSizes
			fSizes.append(int(filesize_range))
			fSizes.append(int(filesize_range))
			fSizes.append(1)
		print "File Sizes Splitted",fSizes
		for size in range(int(fSizes[0]),int(fSizes[1])+1,int(fSizes[2])):
			print "Size in Bytes by USer",size
			totalsize = (1024 * int(size)-1)
			src_file_path=jmeterPath+protocol_type+"_"+str(size)+"kb.txt"
			create_file_size(src_file_path,totalsize)
			src_csv_path=jmeterPath+protocol_type.upper()+"_Source.csv"
			with open(src_csv_path, 'a+') as srcf:				
				srcf.write(src_file_path+'\n')
		print "Created File Size",totalsize
		return True,"SUCCESS"
	except Exception,e:
		print "Exception on create_src_files ",e
		return False,"FAILURE"

def create_dest_files(protocol_type,destPath,jmeterPath,totalHoststoRun):
	try:
		totalDirs=0
		dirNames=os.listdir(destPath)
		for dir in os.listdir(destPath):			
			if (os.path.isdir(destPath+dir)):
				print "Dir name",dir
				totalDirs=totalDirs+1
		print "Total Dirs ",totalDirs
		hostsLoop=(totalDirs/totalHoststoRun)
		print "Total Hosts Allocated Per Node ",hostsLoop
		for i in range(0,len(dirNames),hostsLoop):
			print "Directory Index", i
			print "Directory name", dirNames[i]
			dest_dir_path=destPath+dirNames[i]+'/'+os.linesep
			dest_csv_path=jmeterPath+protocol_type.upper()+"_Dest.csv"
			with open(dest_csv_path, 'a+') as destf:				
				destf.write(dest_dir_path)
		return True,"SUCCESS"
	except Exception,e:
		print "Exception on create_dest_files ",e
		return False,"FAILURE"
		
def create_file_size(src_file_path,totalsize):
	try:
		with open(src_file_path, 'w+') as f:
			f.seek(totalsize)
			f.write("\0")
			print "Created File Size",totalsize
		return True
	except Exception,e:
		print "Exception on create_file_size ",e
		return False
		
def main():
	try:
		fields = {
			"protocol_dest_path": {"required": True, "type": "str"},
			"jmeter_bin_path": {"required": True, "type": "str"},
			"protocol_type": {"required": True, "type": "str"},
			"protocol_file_sizes": {"required": True, "type": "str"},
			"totalhostsrun": {"required": True, "type": "int"}
			}
		module = AnsibleModule(argument_spec=fields)
		# destPath="/root/share/"
		# jmeterPath="/root/jmeter/apache-jmeter-3.3/bin/"
		# protocol_type="as2"
		# fileSizes="1-100-2"
		# totalHoststoRun=10
		srcstat,srcres=create_src_files(module.params['protocol_type'],module.params['jmeter_bin_path'],module.params['protocol_file_sizes'])
		if srcstat:
			deststat,destres=create_dest_files(module.params['protocol_type'],module.params['protocol_dest_path'],module.params['jmeter_bin_path'],module.params['totalhostsrun'])		
		if deststat:
			module.exit_json(meta=destres)
		else:
			module.fail_json(meta=destres)
	except Exception,e:
		print "Exception on create_jmeter_files Main",e
		
if __name__ == "__main__":
    main()
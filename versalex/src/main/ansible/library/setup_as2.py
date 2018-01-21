#!/usr/bin/python
DOCUMENTATION = '''
module: setup_as2
short_description: "Creates AS2 Profiles and Setups in Both Server and TP Nodes"
author:
  - muthukumarc
requirements:
  - Jinja2
  - requests (for rest requests)
options:
  setup_as2:
    description:
		"host_name": {"required": True, "type": "str"},
		"server_hosts": {"required": True, "type": "list"},
		"proxy_hosts": {"required": False, "type": "list" },
		"tp_hosts": {"required": True, "type": "list" },
		"dataset": {"required": True, "type": "dict" }, 
example:  setup_as2: host_name="Host Name to be passed"  server_hosts="Server Hosts IP's" proxy_hosts="Proxy Hosts IP's" tp_hosts="TP Hosts IP's" dataset="DataSet Passed by User for AS2 Setup"
'''
from ansible.module_utils.basic import *
from jinja2 import Environment, FileSystemLoader
import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode
import os
import yaml

sender_json_req= {}
receiver_json_req={}

def get_jsonoutput(json_path,render_content):
	try:
		path, filename = os.path.split(json_path)
		env = Environment(loader=FileSystemLoader(path or './'))
		template = env.get_template(filename)    
		output= template.render(render_content)
		return output
	except Exception,e:
		print "Exception on get Json Output",e
		
def get_postresults(url,json_file):
	try:
		if type(json_file) is dict:
			json_data=json_file
		else:
			with open(json_file, 'r') as jsfile:
				json_data=json.load(jsfile)
		print "Cert Json DATA-",json_data
		head ={ 'Content-type':'application/json','Accept':'application/json'}
		results = requests.post(url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'),data=json.dumps(json_data))
		json_res=json.loads(results.text)
		print "FINAL ****RES**",json_res
		return json_res
	except Exception,e:
		print "Exception on get_postresults method",e
		
def create_cert_json(cert_name):
	try:
		alias= {"alias": cert_name}
		json_out=get_jsonoutput("./files/AS2_Cert.json",alias)
		json_path="./files/as2jsons/"+cert_name+".json"
		with open(json_path, 'w+') as jsonfile:
			jsonfile.write(json_out)
		return json_path
	except Exception, e:
		print "Exception on create_cert_json method",e

def import_cert(partner_ip,cert_code):
	try:
		partner_url ="http://"+partner_ip+":5080/api/certs"
		print "Importing Cert to Receiver", partner_url
		partner_data= { "requestType":"importCert","import":cert_code}
		partner_res = get_postresults(partner_url,partner_data)
		print "Partner-Cert-Id", partner_res['id']
		# print "Partner Results ",partner_res		
		return partner_res
		# print "Final Dump",json_req
	except Exception, e:
		print "Exception on import_cert method",e
		
def create_cert(sender_ip,json_file):
	try:
		sender_url ="http://"+sender_ip+":5080/api/certs"
		print "Cert Json File to POST", json_file,"Sender URL",sender_url
		sender_res = get_postresults(sender_url,json_file)
		print "Cert-Id", sender_res['id']
		return sender_res
	except Exception, e:
		print "Exception on create_cert method",e

def create_conn_json(host_name,json_req):
	try:
		json_req['host_name']=host_name
		conn_output=get_jsonoutput("./files/AS2_Conn.json",json_req)
		# print "Connection Output",conn_output
		conn_json_path="./files/as2jsons/"+host_name+".json"
		with open(conn_json_path, 'w+') as jsonfile:
			jsonfile.write(conn_output)
		return conn_json_path
	except Exception, e:
		print "Exception on create_conn_json method",e
		
def create_connection(ip_address,json_path):
	try:
		sender_url ="http://"+ip_address+":5080/api/connections"
		print "Json Connection File to POST", json_path
		# json_req['certid']=json_data['id']    
		sender_res = get_postresults(sender_url,json_path)    
		return "success"
		# print "Final Connec Res",sender_res
	except Exception,e:
		print "Exception on create_connection method",e		
		
def get_totalhost(dataset):
	try:	
		for k,val in dataset.iteritems():
			print "Key",k,"Val",val['total']
			if k == 'as2':
				total=val['total']				
				return int(total)
	except Exception,e:
		print "Exception on get_totalhost method",e

def setup_connection(hostname,master_ip,conn_json_req):
	try:
		print "*****************************setup_connection*****************************"
		conn_json_path=create_conn_json(hostname,conn_json_req)
		conn_json_results=create_connection(master_ip,conn_json_path)
		print "Connnection Results",conn_json_results
		print "*****************************setup_connection END*****************************"		
	except Exception,e:
		print "Exception on setup_connection method",e		
		
def setup_sender_cert(certname,sender_ip,tp_ip,master_ip):
	try:
		print "*****************************setup_sender_cert*****************************"
		cert_json_path=create_cert_json(certname)
		sender_cert_res=create_cert(master_ip,cert_json_path)
		sender_json_req['sender_ip']=sender_ip
		sender_json_req['partner_ip']=tp_ip
		sender_json_req['cert_id']=sender_cert_res['id']
		sender_json_req['certificate']=sender_cert_res['certificate']
		partner_import_res=import_cert(tp_ip,sender_json_req['certificate'])
		receiver_json_req['import_cert_id']=partner_import_res['id']
		print "*****************************setup_sender_cert END*****************************"		
	except Exception,e:
		print "Exception on setup_sender_cert method",e
		
def setup_receiver_cert(certname,sender_ip,tp_ip,master_ip):
	try:
		print "*****************************setup_receiver_cert*****************************"
		cert_json_path=create_cert_json(certname)
		sender_cert_res=create_cert(sender_ip,cert_json_path)
		receiver_json_req['sender_ip']=sender_ip
		receiver_json_req['partner_ip']=tp_ip
		receiver_json_req['cert_id']=sender_cert_res['id']
		receiver_json_req['certificate']=sender_cert_res['certificate']
		partner_import_res=import_cert(master_ip,receiver_json_req['certificate'])
		sender_json_req['import_cert_id']=partner_import_res['id']
		print "*****************************setup_receiver_cert END*****************************"		
	except Exception,e:
		print "Exception on setup_receiver_cert method",e

def setup_as2_with_proxy(host_name,server_hosts,tphosts,dataset,proxy_hosts):
	try:
		tp_cnt=len(tphosts)
		proxy_cnt=len(proxy_hosts)
		proxy_indx=0
		master_ip=server_hosts[0]
		total_host=get_totalhost(dataset)
		host_pernode=(total_host/tp_cnt)
		start=1
		end=int(host_pernode)
		for tp_ip in tphosts:
			print "TP Host Index",tphosts.index(tp_ip)
			print "Server Hosts",server_hosts[tphosts.index(tp_ip)]
			print "TP Hosts",tphosts[tphosts.index(tp_ip)]
			if((tphosts.index(tp_ip) > proxy_indx)):
				proxy_indx=(tphosts.index(tp_ip)-proxy_cnt)
			for i in range(start,end+1):
				setup_sender_cert((host_name+str(i)),proxy_hosts[proxy_indx],tp_ip,master_ip)
				setup_receiver_cert((host_name+str(i)),tp_ip,proxy_hosts[proxy_indx],master_ip)
				setup_connection((host_name+str(i)),master_ip,sender_json_req)
				setup_connection((host_name+str(i)),tp_ip,receiver_json_req)
			start=start+int(host_pernode)
			end=end+int(host_pernode)
			proxy_indx=proxy_indx+1
		return True,"success"
	except Exception,e:
		print "Exception on setup_as2",e
		return False,e
		
def setup_as2_without_proxy(host_name,server_hosts,tphosts,dataset):
	try:
		tp_cnt=len(tphosts)
		master_ip=server_hosts[0]
		total_host=get_totalhost(dataset)
		host_pernode=(total_host/tp_cnt)
		start=1
		end=int(host_pernode)
		for tp_ip in tphosts:
			print "TP Host Index",tphosts.index(tp_ip)
			print "Server Hosts",server_hosts[tphosts.index(tp_ip)]
			print "TP Hosts",tphosts[tphosts.index(tp_ip)]		
			for i in range(start,end+1):
				setup_sender_cert((host_name+str(i)),server_hosts[tphosts.index(tp_ip)],tp_ip,master_ip)
				setup_receiver_cert((host_name+str(i)),tp_ip,server_hosts[tphosts.index(tp_ip)],master_ip)
				setup_connection((host_name+str(i)),master_ip,sender_json_req)
				setup_connection((host_name+str(i)),tp_ip,receiver_json_req)
			start=start+int(host_pernode)
			end=end+int(host_pernode)
		return True,"success"
	except Exception,e:
		print "Exception on setup_as2",e
		return False,e
		
def main():
	try:
		fields = {
			"host_name": {"required": True, "type": "str"},
			"server_hosts": {"required": True, "type": "list"},
			"proxy_hosts": {"required": False, "type": "list" },
			"tp_hosts": {"required": True, "type": "list" },
			"dataset": {"required": True, "type": "dict" },
		}
		module = AnsibleModule(argument_spec=fields)
		if not os.path.exists("./files/as2jsons/"):
			os.makedirs("./files/as2jsons/")
		if (len(module.params['proxy_hosts']) >= 1):
			stat,result=setup_as2_with_proxy(module.params['host_name'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'],module.params['proxy_hosts'])
		else:
			stat,result=setup_as2_without_proxy(module.params['host_name'],module.params['server_hosts'],module.params['tp_hosts'],module.params['dataset'])
			
		if stat:
			module.exit_json(meta=result)
		else:
			module.fail_json(meta=result)
	except Exception,e:
		print "Exception on Main",e
		
if __name__ == "__main__":
    main()
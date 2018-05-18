#!/usr/bin/python
DOCUMENTATION = '''
module: file_monitor
'''

import os
import sys
import time
import datetime
from ansible.module_utils.basic import *
import smtplib
from os.path import basename
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.MIMEBase import MIMEBase
from email.encoders import encode_base64


res_json={'results':[],'main':[]}
mbx_count={'mbx_results':[]}
def comp_secs_check(file_path):
	try:
		secs_json={}
		file_time_millis=int(os.stat(file_path).st_mtime*1000.0)
		print "File Properties millis",file_time_millis
		print "Current TimeStamp millis",int(time.time())
		file_time=datetime.datetime.fromtimestamp(file_time_millis/1000.0)
		curr_time=datetime.datetime.fromtimestamp(time.time()*1000.0/1000.0)
		print "File time",file_time,"Current Time in Millis",curr_time		
		time_diff_millis=curr_time-file_time		
		print "Time Difference in Seconds",time_diff_millis.seconds
		print "Time Difference in Minutes",(time_diff_millis.seconds/60)
		secs_json={'curr_time':curr_time,'file_time':file_time,'secs':time_diff_millis.seconds,'mins':(time_diff_millis.seconds/60)}
		return secs_json
	except Exception,e:
		print "Exception on comp_secs_check ",e
		
def getTopMbxResults(mbx_count):
	try:
		print " Get Top Mailbox Results",mbx_count['mbx_results']
		sorted_elems=sorted(mbx_count['mbx_results'],key=lambda i: (i['mailbox'],i['file_count']))
		return sorted_elems
	except Exception,e:
		print "Exception on getTopMbxResults  ",e
		
def send_mail(log_name,smtp_host,to_addr):
	try:
		print "Sendign Mail 1"
		fread=open(log_name,"rb") 		
		text_file = MIMEBase('application', "octet-stream")
		text_file.set_payload(fread.read())
		fread.close()
		encode_base64(text_file)
		text_file.add_header('Content-Disposition', 'attachment', filename=os.path.basename(log_name))
		smsg = MIMEMultipart()		
		smsg['Subject']="Watch File Logs"
		smsg['From']="systemtest@cleo.com"
		smsg['To']=to_addr
		smsg.attach(text_file)
		sm=smtplib.SMTP(smtp_host)
		sm.sendmail("systemtest@cleo.com",to_addr,smsg.as_string())
	except Exception,e:
		print "Exception on send_mail ",e
		
def write_file(mbx_count,res_json,secs_json,log_name):
	try:
		sorted_elems=getTopMbxResults(mbx_count)
		with open(log_name+".logs","a") as s:
			s.write("\n\n**************************************************\n\n")
			s.write("Start Time:-"+str(datetime.datetime.now())+"\n\n")
			s.write("\nTotal Backlog-->"+str(res_json['main'][0]['total_backlog'])+"\n")
			if('mins' in secs_json):
				s.write("\nMax File Time-->"+str(secs_json['mins'])+"minutes and "+str(secs_json['secs'])+" seconds\n")
			s.write("\nMailboxes with count\n")
			for elem in sorted_elems[:5]:
				s.write("\n"+str(elem)+"\n")				
			print "Total Backlog write_file:-",res_json['main'][0]['exit_flag']
			s.write("\n\n**************************************************\n\n")
			return True,"SUCCESS"
	except Exception,e:
		print "Exception on write_file  ",e	
		
def monitor_outboxes(outboxes,log_name,smtp_host,to_addr,seconds,t_backlog):
	max_retry=10000
	secs_json={}
	cnt=0
	chk_secs='true'
	total_backlog=0
	while (cnt <=max_retry):
		try:
			for each_outbox in outboxes:
				print "Each Outbox in folder path ",each_outbox
				for root,subfolders,files in os.walk(each_outbox):
					print "Root File System",root
					print "SubFolders System",subfolders
					print "Sub Files ",files
					fcount_less=0
					fcount_more=0
					total_fcount=0
					file_paths=[]
					for	file in files:
						file_path=os.path.join(root,file)
						total_backlog += 1							
						print "File Path of System",file_path
						secs_json=comp_secs_check(file_path)
						total_fcount=total_fcount+1
						if(secs_json['secs'] >= seconds and fcount_more <=5):
							fcount_more=fcount_more+1
							file_paths.append(os.path.basename(file_path))							
							chk_secs='false'
						elif(secs_json['secs'] >= seconds and fcount_more >5):
							fcount_more=fcount_more+1							
							chk_secs='false'
						if(secs_json['secs'] < seconds and fcount_less <=5):
							file_paths.append(os.path.basename(file_path))
							fcount_less=fcount_less+1
						elif(secs_json['secs'] < seconds and fcount_less >5):
							fcount_less=fcount_less+1
					mbx_count['mbx_results'].append({'mailbox':os.path.basename(root),'file_count':total_fcount,'file_paths':file_paths,'>5mins':fcount_more,'<5mins':fcount_less})
					res_json['results'].append({os.path.basename(root):total_fcount,'file_paths':file_paths,'>5mins':fcount_more,'<5mins':fcount_less})
			res_json['main'].append({'total_backlog':total_backlog,'exit_flag':chk_secs})	
			print "Results JSON to Return",res_json
			stat,result=write_file(mbx_count,res_json,secs_json,log_name)
			log_file=log_name+".logs"
			if(chk_secs=='false' or total_backlog <= 0 or total_backlog >t_backlog ):
				print "Files toook more than 300 secs"					
				send_mail(log_file,smtp_host,to_addr)
				return True,res_json['main']
			else:
				send_mail(log_file,smtp_host,to_addr)
				raise ValueError('Files took < 500 Seconds and Total Backlog is not Zero')
		except Exception,e:
			print "Exception on monitor_outboxes ",e
			time.sleep(10)
			cnt+=1
			if(cnt>max_retry):
				print "Count and Retries REACHED***",cnt
				raise e

def main():
 	try:
 		fields = {
 			"outboxes": {"required": True, "type": "list"},
			"log_name": {"required": True, "type": "str"},
			"smtp_host": {"required": True, "type": "str"},
			"to_mail_address": {"required": True, "type": "str"},
			"seconds": {"required": True, "type": "int"},
			"total_backlog": {"required": True, "type": "int"}
			
 		}
 		module = AnsibleModule(argument_spec=fields)
		stat,res=monitor_outboxes(module.params['outboxes'],module.params['log_name'],module.params['smtp_host'],module.params['to_mail_address'],module.params['seconds'],module.params['total_backlog'])
 		if stat:
 			module.exit_json(meta=res)
 		else:
 			module.fail_json(**res)
 	except Exception,e:
		print "Exception on Main for File_Monitor",e		
			
if __name__ == "__main__":
	main()
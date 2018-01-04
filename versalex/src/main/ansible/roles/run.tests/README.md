Run Tests
===================

  * Sets up jmeter in Servers and TPNodes  and Runs the Jmeter tests as per user configs

Requirements:-
--------------------
	
	1. Install Product and Configure Product & Setup Testprofiles roles should ran successfully


Role Variables:-
--------------

```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	tpnodes.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	defaults.yml:- <checkout dir>st/versalex/src/main/ansible/files/defaults.yml

	ex: 
	Run Only AS2 tests
	
	as2_filespermin=40 as2_totalmins=10 as2_totalhosts=2 ftp_filespermin=0 ftp_totalmins=0 ftp_totalhosts=0
        
	Run Only FTP tests
	
	as2_filespermin=0 as2_totalmins=0 as2_totalhosts=0 ftp_filespermin=40 ftp_totalmins=10 ftp_totalhosts=2
	
	Run Both AS2 and FTP tests
	
	as2_filespermin=40 as2_totalmins=10 as2_totalhosts=2 ftp_filespermin=40 ftp_totalmins=10 ftp_totalhosts=2
	
```

Dependencies:-
------------

* Setup Variables Playbook:-

	Sets up Host and Group variables required for further module processing
	
	Based on below yamls
	
		1. Servers.yml
		2. TPNodes.yml
		3. Defaults.ym;
		
       ansible-playbook setup_vars.yml -i inventories/servers/ -e machine_type=servers

       ansible-playbook setup_vars.yml -i inventories/tpnodes/ -e machine_type=tpnodes

	
Sub Roles:-
-------------

* Setup . JMX(role: configure.product/jmx,tags: ['setup-jmx'])

```
	1. Sets up JMX ocnfiguration for Versalex and OSGI
	
```

* String command (role: setup.testprofiles/str_command/,cmdline_str: 'scheduler,start' ,tags: ['rest'] )

```

	1. Runs any String instruction for versalex products
	
	ex: Harmonyc -s scheduler,start
	

```


* Sets up Jmeter and Run tests based on user configs passed

```	

	- hosts: "shares"
	  roles:
	      - { role: run.tests/,as2_filespermin: "{{as2_filespermin|default(0)}}",as2_totalmins: "{{as2_totalmins|default(0)}}",as2_totalhosts: "{{as2_totalhosts|default(0)}}",ftp_filespermin: "{{ftp_filespermin|default(0)}}",ftp_totalmins: "{{ftp_totalmins|default(0)}}",ftp_totalhosts: "{{ ftp_totalhosts|default(0)  }}",tags: 'run_tests' }

		
```


Example Playbook
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/run.tests/
	
	
    Run with specifying tags:- 
    
    		Run Jmeter tests for passing AS2 and FTP args
    		
		    ansible-playbook -i inventories/servers/ -i inventories/tpnodes/ -e as2_filespermin=40 -e as2_totalmins=10 -e as2_totalhosts=2  -e ftp_filespermin=40 -e ftp_totalmins=10 -e ftp_totalhosts=2 run_tests.yml
		    
		Only Setup JMX and Restart Versalex
		
		    ansible-playbook -i inventories/servers/ -i inventories/tpnodes/ -e as2_filespermin=40 -e as2_totalmins=10 -e as2_totalhosts=2  -e ftp_filespermin=40 -e ftp_totalmins=10 -e ftp_totalhosts=2 run_tests.yml --tags ['setup-jmx','versalex-restart']

		
		
	[root@localhost ansible]# cat run_tests.yml
	---
	- name: Configures jmx for versalex instances
	  hosts: "versalex"
	  roles:
		- { role: configure.product/jmx,tags: ['setup-jmx'] }

	- name: Restart versalex instances
	  hosts: "versalex"
	  # #strategy: free
	  roles:
		- { role: common/versalex/restart/,tags: ['versalex-restart'] }

	- name: Validates versalex instances for jmx configs
	  hosts: "versalex"
	  roles:
		- { role: setup.testprofiles/tests, tags: ['rest','java'] }

	- name: Start and stop scheduler using command line interface
	  hosts: "versalex"
	  roles:
		- { role: setup.testprofiles/str_command/,cmdline_str: 'scheduler,stop' ,tags: ['rest'] }
		- { role: setup.testprofiles/str_command/,cmdline_str: 'scheduler,start' ,tags: ['rest'] }

	# - hosts: "tpnodes-versalex"
	  # roles:
		# - { role: setup.testprofiles/str_command/,cmdline_str: 'scheduler,start' ,tags: ['rest'] }
	- name: Run tests for server and tpnodes
	  hosts: "shares"
	  roles:
		  - { role: run.tests/,as2_filespermin: "{{as2_filespermin|default(0)}}",as2_totalmins: "{{as2_totalmins|default(0)}}",as2_totalhosts: "{{as2_totalhosts|default(0)}}",ftp_filespermin: "{{ftp_filespermin|default(0)}}",ftp_totalmins: "{{ftp_totalmins|default(0)}}",ftp_totalhosts: "{{ ftp_totalhosts|default(0)  }}",tags: 'run_tests' }

	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).
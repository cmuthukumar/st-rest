Monitor Tests
===================

  * Monitors number of transactions sent versus available in db.Compare these transactions with respect to protocol and fail if not equal

Requirements:-
--------------------
	
	1. Install Product and Configure Product & Setup Testprofiles , Run tests roles should ran successfully


Role Variables:-
--------------

```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	tpnodes.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	defaults.yml:- <checkout dir>st/versalex/src/main/ansible/files/defaults.yml

	ex: 
	Monitor Tests
	
		ansible-playbook -i inventories/<'aws' or 'digitalocean'/servers/ -i inventories/<'aws' or 'digitalocean'/tpnodes -e as2_filespermin=200 -e as2_totalmins=120 -e ftp_filespermin=200 -e ftp_totalmins=120 -e sshftp_filespermin=200 -e sshftp_totalmins=120 monitor_tests.yml --tags all

	
```

Dependencies:-
------------

* Setup Variables Playbook:-

	Sets up Host and Group variables required for further module processing
	
	Based on below yamls
	
		1. Servers.yml
		2. TPNodes.yml
		3. Defaults.yml
		
       ansible-playbook setup_vars.yml -i inventories/<'aws' or 'digitalocean'>/servers/ -e machine_type=servers

       ansible-playbook setup_vars.yml -i inventories/<'aws' or 'digitalocean'>/tpnodes/ -e machine_type=tpnodes

	
Sub Roles:-
-------------

* Monitor transactions on the D

```
	 roles:
    - { role: monitor.tests/db,tags: ['monitor-db'] 
    
	
```

		
```


Example Playbook
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/monitor.tests/
	
	
    Run with specifying tags:- 
    
    		Monitor Tests
    		
		ansible-playbook -i inventories/<'aws' or 'digitalocean'/servers/ -i inventories/<'aws' or 'digitalocean'/tpnodes -e as2_filespermin=200 -e as2_totalmins=120 -e ftp_filespermin=200 -e ftp_totalmins=120 -e sshftp_filespermin=200 -e sshftp_totalmins=120 monitor_tests.yml --tags all

		
	[root@localhost ansible]# cat monitor_tests.yml
	---
	
	- name: Validate DB for transactions processed
	  hosts: "integrations"
	  # connection: local
	  roles:
	    - { role: monitor.tests/db,tags: ['monitor-db'] }

	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).
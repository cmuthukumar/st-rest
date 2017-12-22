Install Product
===================

  * Installs thirdparty applications(ex: db..etc..) on Digital Ocean instances created based on servers and tpnodes yaml configurations passed by user

Requirements:-
--------------------


Role Variables:-
--------------

```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	tpnodes.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	defaults.yml:- <checkout dir>st/versalex/src/main/ansible/files/defaults.yml

	[root@localhost ansible]# cat files/servers.yml
	
	hardware:
	   integrations:           
	      - name: mysql 
		ram_size: 1gb
		region: nyc1
		image_name: "centos-6-x64"
		qty: 1
	software:
	   integrations:
	      - name: mysql
		version: "mysql:latest"
        
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
* install integrations	
```

	1. Install integrations like db , other applications used for versalex instances using docker image 
		

```

Run Playbook with tags
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/install.product/

    Run with defaults:- Run all sub roles in the playbook
        ansible-playbook install.product.yml -e machine_type=servers -e machine_type=servers
    
    Run with specifying tags:- 
    	
    	Servers and TPNodes should run separately, change machine_type=servers to machine_type=tpnodes for TP node setup
    
    		Install integrations like db,other applications   for versalex instances
    		
		    ansible-playbook install_integrations.yml -e machine_type=servers   		    
		    
		[root@localhost ansible]# cat install_integrations.yml
		---
		- name: 'Installs db/thirdparty integrations using docker'
		  hosts: "integrations"
		  roles:
		    - { role: install.integrations }[root@localhost ansible]#
	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).
Install Product
===================

  * Installs versalex products(ex: harmony, vltrader) on Digital Ocean instances created based on servers and tpnodes yaml configurations passed by user

Requirements:-
--------------------
	Hosts file should be present with details like below for connecting and installing products
	
### Hosts File

	Hosts Inventory file should be present

```	

	[root@localhost ansible]# cat <inventories/'aws' or 'digitalocean'>servers/hosts
	
	[versalex]
	servers-versalex-1 ansible_ssh_host=67.205.136.55  appl=harmony subtype=versalex
	servers-versalex-2 ansible_ssh_host=198.199.75.196  appl=harmony subtype=versalex
	
	[all]
	servers-shares-1 ansible_ssh_host=67.207.94.186  appl=share subtype=shares
	servers-proxy-1 ansible_ssh_host=162.243.163.13  appl=proxy subtype=proxy
	servers-proxy-2 ansible_ssh_host=67.205.181.129  appl=proxy subtype=proxy
	servers-versalex-1 ansible_ssh_host=67.205.136.55  appl=harmony subtype=versalex
	servers-versalex-2 ansible_ssh_host=198.199.75.196  appl=harmony subtype=versalex
	servers-integrations-1 ansible_ssh_host=208.68.39.251  appl=mysql subtype=integrations
	
	[servers-shares]
	servers-shares-1 ansible_ssh_host=67.207.94.186  appl=share subtype=shares
	
	[servers-versalex]
	servers-versalex-1 ansible_ssh_host=67.205.136.55  appl=harmony subtype=versalex
	servers-versalex-2 ansible_ssh_host=198.199.75.196  appl=harmony subtype=versalex
	
	[integrations]
	servers-integrations-1 ansible_ssh_host=208.68.39.251  appl=mysql subtype=integrations
	
	[shares]
	servers-shares-1 ansible_ssh_host=67.207.94.186  appl=share subtype=shares
	
	[servers]
	servers-shares-1 ansible_ssh_host=67.207.94.186  appl=share subtype=shares
	servers-proxy-1 ansible_ssh_host=162.243.163.13  appl=proxy subtype=proxy
	servers-proxy-2 ansible_ssh_host=67.205.181.129  appl=proxy subtype=proxy
	servers-versalex-1 ansible_ssh_host=67.205.136.55  appl=harmony subtype=versalex
	servers-versalex-2 ansible_ssh_host=198.199.75.196  appl=harmony subtype=versalex
	servers-integrations-1 ansible_ssh_host=208.68.39.251  appl=mysql subtype=integrations
		


```

Role Variables:-
--------------

```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	tpnodes.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	defaults.yml:- <checkout dir>st/versalex/src/main/ansible/files/defaults.yml

	[root@localhost ansible]# cat files/servers.yml
	---
	hardware:
	   versalex:
	      - name: harmony
		ram_size: 4gb
		region: nyc1
		image_name: "centos-6-x64"
		qty: 2
	software:  
	   versalex: -- Product configs to be installed
	      - name: harmony
		version: "5.4.2-SNAPSHOT"
		install_location: "/root/Harmony/"

```       
 	
Dependencies:-
------------

* Setup Variables Playbook:-

	Sets up Host and Group variables required for further module processing
	
	Based on below yamls
	
		1. Servers.yml
		2. TPNodes.yml
		3. Defaults.ym;
		
       ansible-playbook setup_vars.yml -i <inventories/'aws' or 'digitalocean'>servers/ -e machine_type=servers

       ansible-playbook setup_vars.yml -i <inventories/'aws' or 'digitalocean'>tpnodes/ -e machine_type=tpnodes

	
Sub Roles:-
-------------
* license
```

	1. Generates License properties files using license generator
	2. Generate License key for each node
	
```

* restify
```

	1. Enable Rest for Versalex products

```

* listener
```	

	Setups local listener properties to make sure versalex service is up and running
	
	1. Generates Local Listener Certificate
	
	2. Setting Local Listener Properties  
		1. External Address required 
		2. Signing Certificate and password
		

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
    
    		Install only Versalex    
    		
		    ansible-playbook install.product.yml -e machine_type=servers   --tags: ['package-deps','setup-license','install-versalex','versalex-start_stop','setup-listener']
		    
		Install only VLProxy
		
		    ansible-playbook install.product.yml -e machine_type=servers   --tags: ['package-deps','setup-license','install-versalex','install-proxy','versalex-start_stop','setup-listener']
		    
	[root@localhost ansible]# cat install_product.yml
	---
	- name: Installs Versalex Dependencies
	  hosts: "versalex:proxy:integrations:shares"
	  roles:
	    - { role: common/versalex/deps,tags: 'package-deps' }

	- name: Generates and setups license keys for versalex instance
	  hosts: "versalex"
	  roles:
	    - { role: install.product/license,tags: 'setup-license' }

	- name: Downloads and Installs versalex product
	  hosts: "versalex"
	  roles:
	    - { role: install.product, prod_dict: "{{ versalex }}",tags: 'install-versalex' }

	- name: Downloads and Installs VLProxy
	  hosts: "proxy"
	  roles:
	    - { role: install.product, prod_dict: "{{ proxy }}",tags: 'install-proxy' }

	- name: Enabled VLNavigator Users and Restifies versalex products
	  hosts: "versalex"
	  roles:
	    - { role: install.product/restify,tags: ['enable-rest'] }

	- name: Start and stop versalex products
	  hosts: "versalex"
	  roles:
	    - { role: common/versalex/start_stop/,status: start,tags: ['versalex-start_stop'] }

	- name: Generates Signing Certificates using Python module
	  hosts: "versalex"
	  connection: local
	  roles:
	    - { role: install.product/listener/,certname: "LOC_LIST",tags: ['setup-listener'] }

	- name: Apply Listener Properties with Signing Certificates
	  hosts: "versalex"
	  roles:
	    - { role: install.product/listener/update_props,certname: "LOC_LIST",tags: ['setup-listener'] }
	    - { role: common/versalex/restart/,tags: ['versalex-restart'],tags: ['setup-listener'] }

	- name: Validates versalex instances by rest calls
	  hosts: "versalex"
	  strategy: free
	  roles:
	    - { role: install.product/tests,tags: 'install-tests' } 

	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

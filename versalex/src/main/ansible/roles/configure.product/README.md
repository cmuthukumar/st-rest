Configure Product
===================

  * Configures versalex products(ex: Harmony, VLTrader) with Shares,Mem,VLProxy,DB

Requirements:-
--------------------
	
	1. Install Product role should ran successfully	with rest enabled


Role Variables:-
--------------

```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	tpnodes.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	defaults.yml:- <checkout dir>st/versalex/src/main/ansible/files/defaults.yml


```

Dependencies:-
------------

* Setup Variables Playbook:-

	Sets up Host and Group variables required for further module processing
	
	Based on below yamls
	
		1. Servers.yml
		2. TPNodes.yml
		3. Defaults.ym;
		
       ansible-playbook setup_vars.yml -i inventories/<'aws' or 'digitalocean'>servers/ -e machine_type=servers

       ansible-playbook setup_vars.yml -i inventories/<'aws' or 'digitalocean'>tpnodes/ -e machine_type=tpnodes


* Install NFS Utils for Client and Server Shares on instances
	
Sub Roles:-
-------------

* server_share(configure.product/server_share)

```

	1. Create NFS folder share for server instances
		
	
```

* client_share(configure.product/client_share)

```

	1. Create NFS folder share for client instances
	2. Mount to share folders fr each versalex instance

```

* Memory(configure.product/mem)

```	

	1. Configures Memory for versalex instances based on servers and tpnodes file
		

```

* VLProxy(configure.product/vlproxy)

```	

	1. Generates Properties files for VLProxy 
	2. Configures VLProxy with Serial Numbers of versalex instances
		

```

* DB(configure.product/db)

```	

	1. Configures DB for versalex instances and set transfer logging to DB instead of XML
		

```

* Proxy(configure.product/proxy)

```	

	1. Configures Proxy properties (ip,port) for versalex instances 	
		

```

Run Playbook with tags
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/configure.product/

    Run with defaults:- Run all sub roles in the playbook
    
        ansible-playbook install.product.yml -e machine_type=servers -e machine_type=servers
    
    Run with specifying tags:- 
    
    		Configures Server Share , Client Share , Memory, and Restart
    		
		    ansible-playbook configure_product.yml -i inventories/<'aws' or 'digitalocean'>/servers/ -e machine_type=servers  --tags: ['server_share','client_share','mem',''versalex-restart']
		    
    		Configures Server Share , Client Share , Memory,Versalex Proxy, VLProxy,and Restart
    		
		    ansible-playbook configure_product.yml -i inventories/<'aws' or 'digitalocean'>/tpnodes/ -e machine_type=tpnodes  --tags: ['server_share','client_share','mem','vex_proxy','setup_vlproxy','versalex-restart']

	[root@localhost ansible]# cat configure_product.yml
	---
	- name: Installs NFS shares and dependencies
	  hosts: "versalex:proxy:shares"
	  roles:
		- { role: configure.product/common}

	- name: Setups system testing jar for configuring versalex instances
	  hosts: "versalex"
	  roles:
		- { role: 'configure.product/common/use_systestjar',tags: ['use_systestjar'] }

	- name: Configures server side shares
	  hosts: shares
	  tasks:
		- name: Configure Server Side Shares
		  include_role:
			  name: configure.product/server_share
		  with_dict: "{{ shares | default({}) }}"
		  loop_control:
			  loop_var: srvr_share
		  tags:
			- 'server_share'

	- name: Configures client versalex instances side shares
	  hosts: versalex
	  tasks:
		- name: Configure Client Side Shares
		  include_role:
			name: configure.product/client_share
		  with_dict: "{{ versalex | default({}) }}"
		  loop_control:
			  loop_var: clishare
		  tags:
			- 'client_share'


	- name: Configures memory for versalex instances
	  hosts: "versalex"
	  roles:
		- { role: configure.product/mem,tags: ['mem'] }

	- name: Configures VLProxy with serial nos and properties file
	  hosts: "proxy"
	  # connection: local
	  roles:
		- { role: configure.product/vlproxy,external_address: "{{ansible_ssh_host}}" ,email_on_fail_addr: 'muthukumarc@cleo.com',tags: ['setup_vlproxy'] }

	- name: Configures db for versalex instances
	  hosts: "versalex:integrations"
	  roles:
		- { role: configure.product/db,when: integrations is defined,tags: ['db'] }

	- name: Configures proxy for versalex instances
	  hosts: "versalex:proxy"
	  roles:
		- { role: configure.product/proxy,when: proxy is defined,tags: ['vex_proxy'] }

	- name: Configures ldap for versalex instances
	  hosts: "versalex:integrations"
	  roles:
		- { role: configure.product/ldap,when: ldap is defined,tags: ['ldap'] }

	- name: Restart versalex instances
	  hosts: versalex
	  roles:
		- { role: common/versalex/restart/,tags: ['versalex-restart'] }

	- name: Tests versalex instances for shares
	  hosts: "versalex:proxy"
	  roles:
		- { role: configure.product/tests }

	
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

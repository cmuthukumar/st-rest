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
		
       ansible-playbook setup_vars.yml -i inventories/servers/ -e machine_type=servers

       ansible-playbook setup_vars.yml -i inventories/tpnodes/ -e machine_type=tpnodes


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
    		
		    ansible-playbook configure_product.yml -i inventories/servers/ -e machine_type=servers  --tags: ['server_share','client_share','mem',''versalex-restart']
		    
    		Configures Server Share , Client Share , Memory,Versalex Proxy, VLProxy,and Restart
    		
		    ansible-playbook configure_product.yml -i inventories/tpnodes/ -e machine_type=tpnodes  --tags: ['server_share','client_share','mem','vex_proxy','setup_vlproxy','versalex-restart']

	[root@localhost ansible]# cat configure_product.yml
	---
	- hosts: "versalex:proxy:shares"
	  roles:
	    - { role: configure.product/common}

	- hosts: "versalex"
	  roles:
	    - { role: 'configure.product/common/use_systestjar' }

	- hosts: shares
	  tasks:
	    - name: Configure Server Side Shares
	      include_role:
		  name: configure.product/server_share
	      with_dict: "{{ shares | default({}) }}"
	      loop_control:
		  loop_var: srvr_share

	- hosts: versalex
	  tasks:
	    - name: Configure Client Side Shares
	      include_role:
		name: configure.product/client_share
	      with_dict: "{{ versalex | default({}) }}"
	      loop_control:
		  loop_var: clishare

	- hosts: "versalex"
	  roles:
	    - { role: configure.product/mem }

	- hosts: "proxy"
	  # connection: local
	  roles:
	    - { role: configure.product/vlproxy,external_address: "{{ansible_ssh_host}}" ,email_on_fail_addr: 'muthukumarc@cleo.com' }

	- hosts: "versalex:integrations"
	  roles:
	    - { role: configure.product/db,when: integrations is defined }

	- hosts: "versalex:proxy"
	  roles:
	    - { role: configure.product/proxy,when: proxy is defined }

	- hosts: "versalex:integrations"
	  roles:
	    - { role: configure.product/ldap,when: ldap is defined }

	- hosts: "versalex"
	  # strategy: free
	  roles:
	    - { role: common/versalex/restart/,tags: ['versalex-restart'] }

	- hosts: "versalex:proxy"
	  roles:
	    - { role: configure.product/tests }

	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).
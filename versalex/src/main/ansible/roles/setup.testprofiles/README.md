Setup TestProfiles
===================

  * Sets up tests profiles for AS2, FTP Protocols
  
  	- AS2
  	- FTP

Requirements:-
--------------------
	
	1. Install Product and Configure Product roles should ran successfully


Role Variables:-
--------------

```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	tpnodes.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	defaults.yml:- <checkout dir>st/versalex/src/main/ansible/files/defaults.yml

	dataset:
	   versalex:
	      - name: as2
	        total: 40 -- Total hosts for AS2
	        schedule_actions: true         
	      - name: ftp
	        total: 40 -- Total hosts for FTP
        schedule_actions: true 
        
        
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

* setup.sync({role: setup.sync,tags: ['setup-sync','rest','java']})

```
	1. Sets up sync with server client nodes
	
```

* AS2 Profiles(- { role: setup.testprofiles/rest/as2/, hostname: "AS2H",when: "'servers-versalex-1' in inventory_hostname",tags: ['rest'] })

```

	1. Setups Sender and Receiver Certs and Connections for AS2 using REST with user passed dataset
	2. User can pass HostName

```

* FTP Server & Client(- { role: setup.testprofiles/rest/ftp/server/, when: "'servers-versalex' in inventory_hostname",tags: ['java','rest'] })

```	
	1. Configures FTP Servers using java api with user passed dataset - Servers
	2. Configures FTP Cliens for Versalex Instances using java with user passed dataset
	

```
   
* Schedule Actions

```	

- hosts: "servers-versalex[0]"
  roles:
    - { role: setup.testprofiles/rest/schedule_actions/, schedule_option: "on file polling continuously",action_type: "send", tags: ['schedule-actions-server','rest'] }

- hosts: "tpnodes-versalex"
  roles:
    - { role: setup.testprofiles/rest/schedule_actions/,schedule_option: "on file polling continuously",action_type: "send", tags: ['schedule-actions-tp','rest'] }


	1. Schedules all actions created with 'send' alias with on file polling continuously
		

```

* Sets up Server and TP Nodes testprofiles using java api

```	

	- hosts: "versalex:proxy"
	  roles:
	    - { role: setup.testprofiles/setup_server, when: "'servers-versalex' in inventory_hostname",tags: ['java'] }
	
	- hosts: "versalex:proxy"
	  roles:
	     - { role: setup.testprofiles/tp_dataset, when: "'servers-versalex' in inventory_hostname",tags: ['java']  }
	     - { role: setup.testprofiles/setup_tp,tags: ['java']  }

		
```


Example Playbook
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/setup.testprofile/

    Run with defaults:- Run all sub roles in the playbook
        ansible-playbook install.product.yml -e machine_type=servers -e machine_type=servers
    
    Run with specifying tags:- 
    
    		Setups Test Profiles for AS2 , FTP using Java
		    ansible-playbook -i inventories/servers/ -i inventories/tpnodes/ setup_testprofiles.yml  --tags: ['java',]
		    
    		Setups Test Profiles for AS2 , FTP using Rest
		    ansible-playbook -i inventories/servers/ -i inventories/tpnodes/  setup_testprofiles.yml  --tags: ['rest']

	[root@localhost ansible]# cat setup_testprofiles.yml
	
	---
	
	- hosts: "servers-versalex"
	  roles:
	    - {role: setup.sync,tags: ['setup-sync','rest','java']}

	- hosts: "versalex:proxy"
	  roles:
	    - { role: setup.testprofiles/common, when: "'servers-versalex' in inventory_hostname",tags: ['rest','java'] }
	    - { role: setup.testprofiles/rest/as2/, hostname: "AS2H",when: "'servers-versalex-1' in inventory_hostname",tags: ['rest'] }

	- hosts: "versalex:proxy"
	  roles:
	    - { role: setup.testprofiles/rest/ftp/server/, when: "'servers-versalex' in inventory_hostname",tags: ['rest'] }
	    - { role: setup.testprofiles/rest/ftp/client/, hostname: "FTPHost",when: "'servers-versalex-1' in inventory_hostname",tags: ['rest'] }

	- hosts: "servers-versalex[0]"
	  roles:
	    - { role: setup.testprofiles/rest/schedule_actions/, schedule_option: "on file polling continuously",action_type: "send", tags: ['schedule-actions-server','rest'] }

	- hosts: "tpnodes-versalex"
	  roles:
	    - { role: setup.testprofiles/rest/schedule_actions/,schedule_option: "on file polling continuously",action_type: "send", tags: ['schedule-actions-tp','rest'] }

	- hosts: "versalex:proxy"
	  roles:
	    - { role: setup.testprofiles/setup_server, when: "'servers-versalex' in inventory_hostname",tags: ['java'] }

	- name: Setups Dataset on TP Node for Testprofiles
	  hosts: "versalex:proxy"
	  roles:
	     - { role: setup.testprofiles/tp_dataset, when: "'servers-versalex' in inventory_hostname",tags: ['java']  }
	     - { role: setup.testprofiles/setup_tp,tags: ['java']  }


	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).
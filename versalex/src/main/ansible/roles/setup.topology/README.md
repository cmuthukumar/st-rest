Setup Topology
===================

  * Creates Droplets on Digital Ocean based on servers and tpnodes yaml configurations passed by user

Requirements:-
--------------------

### Digital Ocean API TOKEN and SSH Keys

	* Get DigitalOcean API Token for your account		
	   See Details in <https://www.digitalocean.com/community/tutorials/how-to-use-the-digitalocean-api-v2>	
	
	* Generate SSH Key Pair locally (using sshkeygen like tools). 
	
	* Add SSH Public key to your Digital Ocean Account.Note down SSH Key name.

Role Variables:-
--------------
```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/servers.yml
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/tpnodes.yml
	hardware:
	   versalex:           
	      - name: harmony -- (Versalex product names.. ex: harmony, lexicom, vltrader)
		ram_size: 1gb -- (Machine ramsize... ex: 1gb,gb,4gb,8gb,16gb)
		region: nyc1  -- (Region Names ...ex: nyc1,nyc2,nyc3,)
		image_name: "centos-6-x64" -- (CentOS, Ubuntu...
		qty: 2 -- No of Nodes need to be created
        
```       
 	
Dependencies:-
------------
       None
  
Checkout:-
-------------
  * Checks out code from github repo https://github.com/CleoDev/st/ branch(ex: master) with required resource files to user machine(ex: jenkins slave machine)/local
	<check out dir>
	
Sub Roles:-
-------------
* upload_sshkey
```

	1. Verify SSH Key exists in DigitalOcean
	2. Creates DigitalOcean Droplets based on SSH Key
	
```

* tasks
```

	1. Creates DigitalOCean Droplets based on SSH Key and API Token passed by user	

```

* create_hosts
```

	1. Creates hosts inventory file for further processing	like below	

		[root@localhost ansible]# cat inventories/servers/hosts
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
		[ungrouped]
		[proxy]
		servers-proxy-1 ansible_ssh_host=162.243.163.13  appl=proxy subtype=proxy
		servers-proxy-2 ansible_ssh_host=67.205.181.129  appl=proxy subtype=proxy
		[servers-integrations]
		servers-integrations-1 ansible_ssh_host=208.68.39.251  appl=mysql subtype=integrations
		[servers-proxy]
		servers-proxy-1 ansible_ssh_host=162.243.163.13  appl=proxy subtype=proxy
		servers-proxy-2 ansible_ssh_host=67.205.181.129  appl=proxy subtype=proxy
		

```

Run Playbook with tags
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/setup.topology/

    Run with defaults:- Run all sub roles in the playbook
    
        	ansible-playbook setup_topology.yml -e machine_type=servers -e do_api_token="<API token from digitalocean>" -e username="<any string represents your name>" -e sshkey_name="<ssh key name  from digitalocean>"
    
    Run with specifying tags:- 
    
		    ansible-playbook setup_topology.yml -e machine_type=servers -e do_api_token="<API token from digitalocean>" -e username="<any string represents your name>" -e sshkey_name="<ssh key name  from digitalocean>" --tags ['ssh-key','create-droplet','hosts-file']

	- hosts: localhost
	  connection: local
	  become: true
	  vars_files:
	    - "{{playbook_dir}}/files/{{machine_type}}.yml"
	  roles:
	    - {role: setup.topology/digitalocean/upload_sshkey,tags: 'ssh-key' }
	    - {role: setup.topology/digitalocean/, machine_type: "{{machine_type}}", username: "{{username}}",tags: 'create-droplet'}
	    - {role: setup.topology/digitalocean/create_hosts,tags: 'hosts-file' }

	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).
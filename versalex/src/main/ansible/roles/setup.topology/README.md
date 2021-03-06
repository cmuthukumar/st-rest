Setup Topology
===================

  * Creates Droplets/EC2 Instacnces on Digital Ocean or AWS based on servers and tpnodes yaml configurations passed by user

Requirements:-
--------------------

### Digital Ocean API TOKEN and SSH Keys

	* Get DigitalOcean API Token for your account		
	   See Details in <https://www.digitalocean.com/community/tutorials/how-to-use-the-digitalocean-api-v2>	
	
	* Generate SSH Key Pair locally (using sshkeygen like tools). 
	
	* Add SSH Public key to your Digital Ocean Account.Note down SSH Key name.

### AWS:-

    * Place AWS credentials in below location on the machine

             cat /root/.aws/credentials
                 [default]
                 aws_access_key_id=<AWS Access Key Id from your account>
                 aws_secret_access_key=<AWS Secret Key from your account>

    * Place SSH Key to below location on the machine for connecting to EC2 instances

              cat /root/.ssh/config
                IdentityFile ~/.ssh/id_rsa_aws


Role Variables:-
--------------
```
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/'aws or digitalocean'/ servers.yml
	servers.yml:- <checkout dir>st/versalex/src/main/ansible/files/'aws' or digitalocean'/tpnodes.yml
	hardware:
	   versalex:           
	      - name: harmony -- (Versalex product names.. ex: harmony, lexicom, vltrader)
		ram_size: 1gb -- (Machine ramsize... ex: 1gb,gb,4gb,8gb,16gb)
		region: nyc1  -- (Region Names ...ex: nyc1,nyc2,nyc3,)
		image_name: "centos-6-x64" -- (CentOS, Ubuntu...
		qty: 2 -- No of Nodes need to be created
        
	AWS:-
	
	hardware:
          versalex:           
              - name: harmony
                security_group: (Security group name from aws account)
                key_pair: (Keypair from aws account)
                instance_type: (instacne types from aws..like t2.medium, t2.large,t2.xlarge..etc...)
                image: (EC2 instance image)
                region: (Region name to create ec2 instacnes)
                vpc_subnet_id: (Subnet id of VPC instances belongs to)
                volume_size: (Disk size of ec2 instacne)
                qty: (No of instances of to create)
	
	
	
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
           
           DigitalOcean:-
           
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
		
	 AWS:-
	 	[servers]
		servers-shares-1 ansible_ssh_host=34.217.120.137 aws_id=i-0117f184053212f70 aws_region=us-west-2 aws_public_dns_name=ec2-34-217-120-137.us-west-2.compute.amazonaws.com ansible_user=ec2-user ansible_become=true appl=share subtype=shares
		servers-proxy-1 ansible_ssh_host=34.217.46.177 aws_id=i-08eacc71490e32cae aws_region=us-west-2 aws_public_dns_name=ec2-34-217-46-177.us-west-2.compute.amazonaws.com ansible_user=ec2-user ansible_become=true appl=vlproxy subtype=proxy
		servers-proxy-2 ansible_ssh_host=52.33.112.14 aws_id=i-0bc8ac2c6456ef951 aws_region=us-west-2 aws_public_dns_name=ec2-52-33-112-14.us-west-2.compute.amazonaws.com ansible_user=ec2-user ansible_become=true appl=vlproxy subtype=proxy
		servers-versalex-1 ansible_ssh_host=54.245.46.66 aws_id=i-07b6626f9ddcc94f9 aws_region=us-west-2 aws_public_dns_name=ec2-54-245-46-66.us-west-2.compute.amazonaws.com ansible_user=ec2-user ansible_become=true appl=harmony subtype=versalex
		servers-versalex-2 ansible_ssh_host=34.216.42.28 aws_id=i-0ebf1c379008730dc aws_region=us-west-2 aws_public_dns_name=ec2-34-216-42-28.us-west-2.compute.amazonaws.com ansible_user=ec2-user ansible_become=true appl=harmony subtype=versalex
		servers-integrations-1 ansible_ssh_host=52.24.191.209 aws_id=i-0405d975d37afd352 aws_region=us-west-2 aws_public_dns_name=ec2-52-24-191-209.us-west-2.compute.amazonaws.com ansible_user=ec2-user ansible_become=true appl=mysql subtype=integrations
		
		

```

Run Playbook with tags
-----------------------
	Checks out code from branch 
	
```
	<check out dir>/st/versalex/src/main/ansible

	cd to <check out dir>/st/versalex/src/main/ansible/roles/setup.topology/

    Run with defaults:- Run all sub roles in the playbook
    
        	ansible-playbook setup_topology.yml -e machine_type=servers -e 'cloud_provider=<'aws' or 'digitalocean'> -e do_api_token="<API token from digitalocean>" -e username="<any string represents your name>" -e sshkey_name="<ssh key name  from digitalocean>"
        	
        	ansible-playbook setup_topology.yml -e machine_type=servers -e 'cloud_provider=<'aws' or 'digitalocean'> -e username="<any string represents your name>" --tags 'aws' or 'digitalocean' "

    
    Run with specifying tags:- 
    
		    ansible-playbook setup_topology.yml -e machine_type=servers -e do_api_token="<API token from digitalocean>" -e username="<any string represents your name>" -e sshkey_name="<ssh key name  from digitalocean>" --tags ['ssh-key','create-droplet','hosts-file']
		    
		    ansible-playbook setup_topology.yml -e machine_type=servers -e 'cloud_provider=<'aws' or 'digitalocean'> -e username="<any string represents your name>" --tags 'aws' or 'digitalocean' "


- hosts: localhost
  connection: local
  gather_facts: true
  vars_files:
    - "{{playbook_dir}}/files/{{cloud_provider}}/{{machine_type}}.yml"
  tasks:
    - name: Upload SSH Key for Digital Ocean
      include_role:
            name: "setup.topology/{{cloud_provider}}/upload_sshkey"
      vars:
        do_token: "{{do_api_token}}"
      tags: ['ssh-key','digitalocean']
    
    - name: Create Instances based on Cloud Provider    
      include_role:
            name: "setup.topology/{{cloud_provider}}/"
      vars:
        machine_type: "{{machine_type}}"
        username: "{{username}}"
      tags: ['aws','digitalocean']

    - name: Create Hosts File based on Groups of Servers and TP Nodes   
      include_role:
            name: "setup.topology/{{cloud_provider}}/create_hosts"
      tags: ['aws','digitalocean']

	        
```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

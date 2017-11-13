Create Machines
=========

  * Create Machines on DigitalOcean based on yaml config file

Requirements
------------
1. Digital Ocean API TOKEN - Login to DigitalOcean to get your API token

Role Variables
--------------

---
hardware:
   versalex:           
      - name: harmony -- (Versalex product names.. ex: harmony, lexicom, vltrader)
        ram_size: 1gb -- (Machine ramsize... ex: 1gb,gb,4gb,8gb,16gb)
        region: nyc1  -- (Region Names ...ex: nyc1,nyc2,nyc3,)
        image_name: "centos-6-x64" -- (CentOS, Ubuntu...
        qty: 2 -- 
        
        

Dependencies
------------

  
###Checkout:
-------------
  * Checks out java and ansible code from github branch with required resource files (pom..etc..) to user machine(ex: jenkins slave machine)


####Maven Commands:
	  *  checkout([$class: 'GitSCM', branches: [[name: "${gitSysTestBranch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', url: 'https://github.com/CleoDev/EFSS.git']]])    

#####Results:
	Checks out code from branch 
```
	<branch checkout dir>

Below will be checked out from EFSS/non-shipped/test/automation/systemtesting/

	controller - Ansible code used to control different stages of System Testing
	
	    * src/main/ansible - ansible roles and playbooks
	    
		configure.product
		create.machines - role
		install.apps
		setup.testprofiles
		
		configure_product.yml - playbook calls configure.product role
		create_machines.yml - playbook calls create.machines role
		install_apps.yml - playbook calls install.apps role
		setup_testprofiles.yml - playbook calls setup.testprofiles role
		
	    * pom.xml -	profiles defined here for each stage mapped to above stages
			
	setup_versalex - Java  used for Setting up Versalex Configuration
			
	    * src/main/java/com/cleo/systest - java classes used in generating system testing jars
	    		
		SetupOptions.java
		SetupSchedule.java
		SetupVersalex.java		
		UtilTestdata.java
		
	    * pom.xml - profiles used to generate jars
	    
      * pom.xml	- Parent pom for System testing
	
```


Example Playbook
----------------

###Create Machines
-------------------
  * Create Machines on DigitalOcean based on yaml config file

```
Sample yaml config file
- machine_type: digitalocean(plan to extend for physical machines and aws cloud machines in near future)
- do_token: <digitalocean account api token..can be generated from digital ocean website for user account>
systestnodes:
   - type: servers (Groups hosts based on this type.This can not be changed by users as they wish)
     configs:
       - name: srv1 (Hostname for the box created)
         ram_size: 8gb
         region: blr1
         image_name: "centos-6-5-x64"
   - type: tpnodes 
     configs:
       - name: tpn1
         ram_size: 8gb
         region: blr1
         image_name: "centos-6-5-x64"
   - type: proxies
     configs:
       - name: proxy1
         ram_size: 4gb
         region: blr1
         image_name: "centos-6-5-x64"
   - type: shares
     configs:
       - name: share1
         ram_size: 4gb
         region: blr1
         image_name: "centos-6-5-x64"
   - type: dbservers
     configs:
       - name: dbsrv1
         ram_size: 4gb
         region: blr1
         image_name: "centos-6-5-x64"
     
 ```
 
License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

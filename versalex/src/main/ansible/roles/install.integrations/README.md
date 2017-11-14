Role Name
=========

A brief description of the role goes here.

Requirements
------------

Any pre-requisites that may not be covered by Ansible itself or the role should be mentioned here. For instance, if the role uses the EC2 module, it may be a good idea to mention in this section that the boto package is required.

Role Variables
--------------

A description of the settable variables for this role should go here, including any variables that are in defaults/main.yml, vars/main.yml, and any variables that can/should be set via parameters to the role. Any variables that are read from other roles and/or the global scope (ie. hostvars, group vars, etc.) should be mentioned here as well.

Dependencies
------------
N/A

Example Playbook
----------------
###Install Integrations (Ansible Role)
----------------------------------------
  * Installs versalex products(generates license and configure them) and external applications required for setting up versalex products.
  
  	* Dockerized install
  		* DockerFile
			* Installs docker on remote hosts and run docker based application using docker file
  		* Docker Image
			* Installs docker on remote hosts and run docker based application using docker image. Pulls docker image from Docker Hub and run it.-For external applications like mysql,ldap servers..etc..
		

		
* Dockerized Install:
            
```
Sample yaml for dockerized install using Docker image-Mysql
<Ansible Playbook DirPath>/install.apps/vars/dbsrv1.yml
---
install_apps:  
  mysql:
    docker: "true"
    image: "mysql:latest"
    use_dockerfile: "false"
    expose_port: 3306
    image_args: "MYSQL_ROOT_PASSWORD=testdocker"
	
```

* Dockerized Install:
		
```
Sample yaml for dockerized install using Docker File
<Ansible Playbook DirPath>/install.apps/vars/dbsrv1.yml
---
install_apps:  
  mysql:
    docker: "true"
    use_dockerfile: "true"
    src_dir: "/home/SystemTest/DockerFiles/"
    dest_dir: "/root/DockerFile/"
    image_name: testdockerfile
    image_tag: latest
    image_args: "MYSQL_ROOT_PASSWORD=testdocker"
	
```
		
```
Location:
	<<Ansible Playbook DirPath>/results/install.apps(ansible role name)>
```	


License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

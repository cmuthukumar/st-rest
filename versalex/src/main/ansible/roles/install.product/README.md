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

A list of other roles hosted on Galaxy should go here, plus any details in regards to parameters that may need to be set for other roles, or variables that are used from other roles.

Example Playbook
----------------

###Install Product (Ansible Role)
----------------------------------------
  * Installs versalex products(generates license and configure them) and external applications required for setting up versalex products.
  
  	* Dockerized install
  		* DockerFile
			* Installs docker on remote hosts and run docker based application using docker file
  		* Docker Image
			* Installs docker on remote hosts and run docker based application using docker image. Pulls docker image from Docker Hub and run it.-For external applications like mysql,ldap servers..etc..
			
  	* Non-Dockerized install
		* Installs versalex products(Harmony, VLTrader, Lexicom, VLProxy) on ips inputted
		
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

* Non-Dockerized Install:

```
Sample yaml config file for non-dockerized install for versalex product harmony
<Ansible Playbook DirPath>/install.apps/vars/srv1.yml
---
install_apps:
  versalex:
    docker: "false" (controls dockerization for the this host)
    find_version_url:  http://contd.cleo.com/nexus/service/local/artifact/maven/resolve
    download_version_url: http://contd.cleo.com/nexus/service/local/artifact/maven/content
    nexus_repoid: InstallerSnapshots
    nexus_groupid: com.cleo.installers
    nexus_artifactid: Harmony
    nexus_classifier: linux64-jre18
    nexus_packaging: bin
    nexus_version: 5.3.0-SNAPSHOT
    port: 5080
    download_location: "/home/SystemTestNew/vexdownload/"
    install_location: "/root/Harmony/"                 
    license:
        nexus_repourl: http://contd.cleo.com/nexus/service/local/artifact/maven/content
        nexus_repoid: cleo
        nexus_groupid: com.cleo.util
        nexus_artifactid: LexiComLicenser_2
        nexus_version: LATEST
        CompanyName: Cleo
        SerialNumber: MY0002
        KeyExpiration: 30
        Product: Harmony
        Limit_HSP: 0
        Limit_Anyother: 0
        Trust: 1
        Unify: 1
 ```

License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

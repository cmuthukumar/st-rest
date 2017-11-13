#System Testing:-

Click Wiki for Details (https://github.com/CleoDev/st/wiki) 

##Purpose:-
  * Automating performance aspects of Versalex Products with respect to various load patterns and scenarios.



####Maven Commands:
	sh "${mvnHome}/bin/mvn install -Pcreate-nodes -f '${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/pom.xml'  -Dplaybook.path='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/create_machines.yml' -Dhost.filepath='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/hosts' "

	
#####Results:
	Generates hosts inventory file and place it in Location: <Ansible Playbook DirPath>/hosts
	
```
	[servers]
	 srv1 ansible_ssh_host=139.59.17.170
	[tpnodes]
	 tpn1 ansible_ssh_host=139.59.21.60
	[proxies]
	 proxy1 ansible_ssh_host=139.59.29.140
	[shares]
	 share1 ansible_ssh_host=139.59.32.196
	[dbservers]
	 dbsrv1 ansible_ssh_host=139.59.47.160
	 
Note:- 
	For each host there is corresponding config file with name <hostname>.yml for each stage below.For above hosts below are the config files placed under each stage(ansible role)
	<Ansible Playbook DirPath>/<rolename>/vars/srv1.yml
	<Ansible Playbook DirPath>/<rolename>/vars/tpn1.yml
	<Ansible Playbook DirPath>/<rolename>/vars/proxy1.yml
	<Ansible Playbook DirPath>/<rolename>/vars/share1.yml
	<Ansible Playbook DirPath>/<rolename>/vars/dbsrv1.yml	
```		

###Install Applications (Ansible Role)
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

```
Sample yaml config file for non-dockerized install for versalex product Harmony TP Node
<Ansible Playbook DirPath>/install.apps/vars/tpn1.yml
---
install_apps:
  versalex:
    docker: "false"
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
        SerialNumber: ZY0002
        KeyExpiration: 30
        Product: Harmony
        Limit_HSP: 0
        Limit_Anyother: 0
        Trust: 1
        Unify: 1
	
```	

```

Sample yaml config file for non-dockerized install for versalex product VLProxy
<Ansible Playbook DirPath>/install.apps/vars/proxy1.yml
---
install_apps:
  versalex:
    docker: "false"
    find_version_url:  http://contd.cleo.com/nexus/service/local/artifact/maven/resolve
    download_version_url: http://contd.cleo.com/nexus/service/local/artifact/maven/content
    nexus_repoid: InstallerSnapshots
    nexus_groupid: com.cleo.installers
    nexus_artifactid: VLProxy
    nexus_classifier: linux64-jre18
    nexus_packaging: bin
    nexus_version: 5.3.0-SNAPSHOT
    port: 5080
    download_location: "/home/SystemTestNew/vexdownload/"
    install_location: "/root/VLProxy/"
```   
    
####Maven Commands:
    	sh "${mvnHome}/bin/mvn install -Pinstall-apps -f '${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/pom.xml'  -Dplaybook.path='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/install_apps.yml' -Dhost.filepath='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/hosts' "

	
#####Results:
	Generates license artifacts like license_keys , license properties file ...etc.. and kept in below location
		
```
Location:
	<<Ansible Playbook DirPath>/results/install.apps(ansible role name)>
```	


###Configurre Versalex (Ansible Role)
----------------------------------------
  * Compile java classes and generates below jars required for setting/configuring up versalex products
  	* SetupOptions jar file
  		- Sets LDAP, DB, Proxies, Transfer Logging, Shares(Server and Client) in versalex products  		
  	* SetupVersalex jar file
  		- Setup Test Data/Profiles in versalex products
  	* SetupSchedule
  		- Schedules actions for Test Data/Profiles created in prior step
		
  * Configures versalex products for DB, LDAP Servers, Proxies, NFS client and server Shares. VLProxy configuration for different harmony servers
  	
  	* Versalex Configs
  		* Configures DB(ex: mysql) with jars downloaded and sets transfer logging to DB
  		* Configures LDAP servers  and Proxies
  		* Configures  NFS Server and Client Shares and mounts the same on remote hosts
  	* VLProxy Configs
  		* Captures serial numbers from servers and tpnodes versalex products and configure the same in VLProxy
  		* Configures VLProxy with custom vlproxy file with vlproxy settings in it	

* Versalex Configs:
		
```
Sample yaml for Versalex Configs for server side-Harmony
<Ansible Playbook DirPath>/configure.product/vars/srv1.yml
---
configure_apps:        
    vex_product: Harmony
    checkout_dir: "/root/workspace/LoadTesting/"
    install_location: "/root/Harmony/"
    dbservers:
       mysql:
        db_type: mysql
        node_ip: dbsrv1
       # jdbc_url: "jdbc:mysql://2.2.2.2:3306/test1"
        port: 3306
        dbname: test1
        driver_str: "com.mysql.jdbc.Driver"
        username: root
        password: mysql        
       mysql2:
        db_type: mysql
        node_ip: dbsrv1
        port: 3306
        dbname: test2
       # jdbc_url: "jdbc:mysql://1.1.1.1:3306/test2"
        driver_str: "com.mysql.jdbc.Driver"
        username: root
        password: mysql
    ldapservers:
       ldap1:
        type: "Active Directory"
        host: "10.80.80.888"
        port: 2222
        secuirtymode: SSL    
        basedn: "Overnight,DC=Engineering,DC=localzz"
        username: testldap
        fullnameattrib: displayNamezz
        homedirattrib: homeDirectoryzz
        emailattrib: xyz@gmal.com
        searchfilter: "department=QAzz"
        usernameattrib: "MuthuTest"
    vlproxies:
        proxy1:
          node_ip: proxy1 
          type: "http"      
          port: "2000"
          revfwdproxy: "true"
        proxy2:
          node_ip: proxy1 
          type: "http"      
          port: "3000"
          revfwdproxy: "true"
    nfs_server_shares:
        mount1:          
          src_path: /root/share1/          
          customvars_macro: "%wmdatas%=/root/share1/"
          
```

```
Sample yaml for Versalex Configs for Client Side-Harmony
<Ansible Playbook DirPath>/configure.product/vars/tpn1.yml
---
configure_apps:
    vex_product: Harmony
    checkout_dir: "/root/workspace/LoadTesting/"
    install_location: "/root/Harmony/"
  #  testprofile_csvpath: FTP_AS2_Rest_Sender_Linux_10.csv
  #  as2certs_exportlocation: /root/certs/
  #  node_type: Sender
    dbservers:
       mysql:
        maven_artifactid: mysql-connector-java
        maven_groupid: mysql
        maven_version: 5.1.26
        maven_destfilename: "mysqltest26.jar"
        db_type: mysql
        node_ip: dbsrv1
        # jdbc_url: "jdbc:mysql://2.2.2.2:3306/test1"
        port: 3306
        dbname: test1
        driver_str: "com.mysql.jdbc.Driver"
        username: root
        password: mysql        
       mysql2:
        maven_artifactid: mysql-connector-java
        maven_groupid: mysql
        maven_version: 5.1.28
        maven_destfilename: "mysqltest28.jar"
        db_type: mysql
        node_ip: dbsrv1
        port: 3306
        dbname: test2
        # jdbc_url: "jdbc:mysql://1.1.1.1:3306/test2"
        driver_str: "com.mysql.jdbc.Driver"
        username: root
        password: mysql        
    ldapservers:
       ldap1:
        type: "Active Directory"
        host: "10.80.80.888"
        port: 2222
        secuirtymode: SSL    
        basedn: "Overnight,DC=Engineering,DC=localzz"
        username: testldap
        fullnameattrib: displayNamezz
        homedirattrib: homeDirectoryzz
        emailattrib: xyz@gmal.com
        searchfilter: "department=QAzz"
        usernameattrib: "MuthuTest"
    vlproxies:
        proxy1:
          node_ip: proxy1
          type: "http"      
          port: "2000"
          revfwdproxy: "true"
        proxy2:
          node_ip: proxy1
          type: "http"      
          port: "3000"
          revfwdproxy: "true"
    nfs_server_shares:
        mount1:          
          src_path: /root/tpshare1/          
          customvars_macro: "%tpshare1%=/root/tpshare1/"

```

```
Sample yaml for Versalex Configs for client side
<Ansible Playbook DirPath>/configure.product/vars/share1.yml
---
configure_apps:        
    checkout_dir: "/root/workspace/LoadTesting/"
    install_location: "/root/Harmony/"   
    nfs_client_shares:
        mount1:
          node_ip: srv1
          srv_path: /root/share1/
          local_path: /home/share1/
        tpshare1:          
          node_ip: tpn1
          srv_path: /root/tpshare1/
          local_path: /home/tpshare2/
          
```

* VLProxy Configs:
		
```
Sample yaml for Versalex Configs for proxy side
<Ansible Playbook DirPath>/configure.product/vars/share1.yml
---
configure_apps:
    vex_product: VLProxy
    vlproxyfile: vlproxy
    checkout_dir: "/root/workspace/LoadTesting/"
    install_location: "/root/VLProxy/"
    vlproxies:
        RevProxyHttpPorts:
            filepath: revproxy.txt
        MaxLogFileSize:
            filepath: logfilesize.txt
          
```

####Maven Commands(Configuring Versalex- Generating Jars required):

	* sh "${mvnHome}/bin/mvn clean install -PSetupOptions -f '<branch checkout dir>/non-shipped/test/automation/systemtesting/pom.xml'"
	* sh "${mvnHome}/bin/mvn install -PSetupVersalex -f '<branch checkout dir>/non-shipped/test/automation/systemtesting/pom.xml'"
	* sh "${mvnHome}/bin/mvn install -PSetupSchedule -f '<branch checkout dir>/non-shipped/test/automation/systemtesting/pom.xml'"
	
#####Results:
	Generates jars files in below location


####Maven Commands(Configuring versalex):

    	sh "${mvnHome}/bin/mvn install -Pconfigure-versalex -f '${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/pom.xml'  -Dplaybook.path='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/configure_product.yml' -Dhost.filepath='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/hosts' "

#####Results:
	Generates Serial Number file, VLProxy config files
		


```

Location:
	<<Ansible Playbook DirPath>/results/configure.product(ansible role name)/<hostname>/
```


###Setup TestProfiles (Ansible Role)
----------------------------------------
  * Setups Test data/profiles on versalex products with certificates for server and tp nodes
  * Schedules actions for the test data/profiles created in prior step
  
```
Sample yaml for Setup Testprofiles in Server side
<Ansible Playbook DirPath>/configure.product/vars/srv1.yml

---
setup_profiles:        
    vex_product: Harmony
    checkout_dir: "/root/workspace/LoadTesting"
    install_location: "/root/Harmony/"
    testprofile_csvpath: "FTP_AS2_Rest_Sender_Linux_10.csv" (CSV file contains test data/profiles with certs info on below format)
    as2certs_exportlocation: /root/certs/ (Certs export location path)
    node_type: Sender
    vex_port: 5080
    
Sample TestProfile: FTP_AS2_Rest_Sender_Linux_10.csv
	Protocol,Host_Advanced,Host_DefaultAlias,Host_alias,Host_Address,Host_Port,Host_Inbox,Host_Outbox,Host_Sentbox,Host_Receivedbox,Mailbox_alias,Mailbox_HomeDirectory,Mailbox_Pwdhash,Mailbox_Usesamecerts,Mailbox_Usesigncert,Mailbox_Useencrcert,Mailbox_Overridelistenercerts,Mailbox_localsigncertpassword,Mailbox_localsigncertalias,Mailbox_localencrcertalias,Mailbox_localencrcertpassword,MailboxH_AS2-From,MailboxH_AS2-To,MailboxH_Subject,MailboxH_[Content-Type],Action_alias,Action_Commands,Mailbox_Encryptedrequest,Mailbox_Receiptdesired,Mailbox_Signedrequest,Mailbox_Encryptionmethod,Cert_UserAlias,Cert_CommonName,Cert_Email,Cert_OrgUnit,Cert_Org,Cert_City,Cert_State,Cert_Country,Cert_DigitalSign,Cert_ClientAuth,Cert_PrivateKeyPwd,Cert_ExportLocation,LocalListener_localsigncertpassword
	
AS2,RESTEnabled=false,Generic AS2,AS2H001,proxy1,6080,inbox/,outbox/,sentbox/,receivebox/,AS2H001,,,TRUE,TRUE,TRUE,TRUE,cleo,TEST1,TEST1,cleo,AS2H001,AS2TP01,TETPMessage,Binary,send,PUT * ,TRUE,TRUE,TRUE,4,TEST1,gg,e@gmail.com,TT,CLOE,L,KN,IN,TRUE,TRUE,cleo,/root/certs/,cleo

```

```
Sample yaml for Setup Testprofiles in TPNode side
<Ansible Playbook DirPath>/configure.product/vars/tpn1.yml
---
setup_profiles:
    vex_product: Harmony
    checkout_dir: "/root/workspace/LoadTesting"
    install_location: "/root/Harmony/"
    testprofile_csvpath: "FTP_AS2_Rest_Receiver_Linux_10.csv"
    as2certs_importlocation: "/root/certs/" (Certs import location)
    node_type: Receiver
    vex_port: 6080

Sample TestProfile: FTP_AS2_Rest_Receiver_Linux_10.csv

Protocol,Host_Advanced,Host_DefaultAlias,Host_alias,Host_Address,Host_Port,Host_Inbox,Host_Outbox,Host_Sentbox,Host_Receivedbox,Mailbox_alias,Mailbox_Username,Mailbox_Password,Mailbox_Usesamecerts,Mailbox_Usesigncert,Mailbox_Useencrcert,Mailbox_Overridelistenercerts,Mailbox_localsigncertpassword,Mailbox_localsigncertalias,Mailbox_localencrcertalias,Mailbox_localencrcertpassword,MailboxH_AS2-From,MailboxH_AS2-To,MailboxH_Subject,MailboxH_[Content-Type],Action_alias,Action_Commands,MailboxH_Content-Type,Mailbox_Encryptedrequest,Mailbox_Receiptdesired,Mailbox_Signedrequest,Mailbox_Encryptionmethod,Cert_ImportUserAlias,Cert_ImportPrivateKeyPwd,Cert_ImportLocation,LocalListener_localsigncertpassword,LocalListener_Httpport,LocalListener_localsigncertpassword,Service_alias,Service_Hostname

AS2,RESTEnabled=false,Generic AS2,AS2TP01,srv1,6080,inbox/,outbox/,sentbox/,receivebox/,AS2TP01,,,TRUE,TRUE,TRUE,TRUE,cleo,TEST1,TEST1,cleo,AS2TP01,AS2H001,TETPMessage,Binary,send,PUT * ,Binary,TRUE,TRUE,TRUE,4,TEST1,cleo,/root/certs/,cleo,6080,cleo,,

```

####Maven Commands:
    	sh "${mvnHome}/bin/mvn install -Psetup-testprofiles -f '${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/pom.xml'  -Dplaybook.path='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/setup_testprofiles.yml' -Dhost.filepath='${env.WORKSPACE}/non-shipped/test/automation/systemtesting/controller/src/main/ansible/hosts' "


#####Results:
	Configures Versalex with test profiles defined in the csv files of server and tp nodes
		* 	Ansible generates machines.txt (as like below) to map nodes ips to setup profiles
		
```
Location:
	<<Ansible Playbook DirPath>/results/setup.testprofiles(ansible role name)/machines.txt
	
	[root@localhost setup.testprofiles]# cat machines.txt
	srv1=139.59.2.210
	tpn1=139.59.29.253
	share1=139.59.47.130
	dbsrv1=139.59.47.155
	proxy1=139.59.32.91

```

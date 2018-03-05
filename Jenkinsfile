#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node


//Define Global Vars

params = ['servers','tpnodes']
workdir = "versalex/src/main/ansible/"
String[] protocols=[]

node('SysTest') {

env.WORKSPACE = pwd()
def mvnHome = tool 'Mvn3.3.9'
sh "cd ${env.WORKSPACE}"
env.JAVA_HOME="${tool 'JDK1.8'}"
env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
sh 'java -version'

//withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', passwordVariable: 'GITPWD', usernameVariable: 'GITUSR']]) {
    stage('Clean')
        {
        deleteDir()
        sh 'ls -lah'
            }
            
    stage('CheckOut')
        {
// Checkout Github Branch to Specific Directory        
       //checkout scm
		checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: "${gitBranch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '79815ffd-ea78-431b-ae29-277ccd0807f6', url: 'https://github.com/CleoDev/st.git']]]
        sh 'printenv'
            }
//def systestvexImage=docker.build('st-versalex:1.0','.')
    def st_ansibleImage =  docker.image('cleo/ansible:st_5.0');
    withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
    st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    {    
    try{
    stage('Create Nodes')
            {
		parallel(
		(params[0]): {
			createNodes(params[0])
				},
		(params[1]): {
				createNodes(params[1])
			}	  
          )

            }
    stage('Install Product')
            {
			 
	parallel(
		(params[0]): {
			installProduct(params[0])
				},
			(params[1]): {
			installProduct(params[1])
				}	  
             )   
				//installProduct(params)
			}

    stage('Install Integrations')
            {			
		parallel(
		(params[0]): {
			installIntegrations(params[0])
				},
			(params[1]): {
			installIntegrations(params[1])
				}	  
             )  
		
            } 
            
            
    stage('Configure Product')
            {
		parallel(
		(params[0]): {
			configProduct(params[0])
				},
			(params[1]): {
			configProduct(params[1])
				}	  
             ) 
		
            } 
			
        }
        finally{
        
            echo "End of System Testing"
        }
    }
    
    }
  
  
 withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
          try{				
				stage('Setup TestProfiles')
					{
    					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'schedule-auto-startup,common,setup-sync' "
						protocols.add('as2')
						protocols.add('ftp')
						protocols.add('sshftp')	
					def stepsDatasets = [:]					
					for (int i = 0; i < protocols.size(); i++) {
			    		def stepProtocol = protocols[i]
			   		 stepsDatasets[stepProtocol] = { ->           
			      			  echo "Running ${stepProtocol}"
							  
						st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    						{       							
    					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags ${stepProtocol} "
				
    						}
							
							
			   				 }
					}
					
					parallel stepsDatasets
					
					}	              
				
		    stage('Run Tests')
		            {
				st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
		    		{       							
					//sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e as2_totalhosts=${doProps[2]['AS2'][0]['HoststoRun']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']} -e ftp_totalhosts=${doProps[2]['FTP'][0]['HoststoRun']} -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']} -e sshftp_totalhosts=${doProps[2]['SSHFTP'][0]['HoststoRun']} run_tests.yml "
					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_totalcreatehosts=1500 -e as2_filespermin=700 -e as2_totalmins=30 -e as2_totalhosts=1500 -e ftp_totalcreatehosts=500 -e ftp_filespermin=700 -e ftp_totalmins=30 -e ftp_totalhosts=500 -e sshftp_totalcreatehosts=500 -e sshftp_filespermin=700 -e sshftp_totalmins=30 -e sshftp_totalhosts=500 run_tests.yml "
						
    				}		               
		            }
		    stage('Monitor Tests')
		            {
		        st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
			       	{       							
					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=700 -e as2_totalmins=30 -e ftp_filespermin=700 -e ftp_totalmins=30  -e sshftp_filespermin=700 -e sshftp_totalmins=30  monitor_tests.yml "
			       						
    				}	
		            }
					
		    stage('Destroy Droplets')
		            {
		                destroyDroplets(params)
            			} 
            			
          }
          finally{
        
            echo "End of Setup , Run,Monitor Tests"
        }
          
      
  
}

}


	
    def createNodes(param)
    {
    withCredentials([[$class: 'StringBinding', credentialsId: 'doCredentials', variable: 'do_ap_token']]) {

        println "Creating Nodes for ${param}"
           sh "cd ${workdir} && ansible-playbook setup_topology.yml -c local -e machine_type=${param} -e do_api_token=${env.do_ap_token} -e username=${doProps[3]['General'][0]['Username']} -e sshkey_name='st-versalex' "
		   
           sh "cd ${workdir} && ansible-playbook setup_vars.yml -c local -i inventories/${param}/ -e machine_type=${param} "

        }
    }
    
    def installProduct(param)
    {
    println "Install Product for ${param} "

			sh "cd ${workdir} && ansible-playbook -i inventories/${param}/ -e machine_type=${param} install_product.yml "
			

    }

    def installIntegrations(param)
    {
    println "Install Integrations"

        println "Installing Product for ${param}"
           sh "cd ${workdir} && ansible-playbook -i inventories/${param}/ -e machine_type=${param} install_integrations.yml "

    }
    
    def configProduct(param)
    {
    println "Configuring Product with DB, LDAP, PROXY Configs"
  
        println "Installing Product for ${param}"
           sh "cd ${workdir} && ansible-playbook -i inventories/${param}/ -e machine_type=${param} configure_product.yml "
    
    }  

	
    def destroyDroplets(params)
	{
	
		println "Destroy Droplets"
		if(("${destroyDroplets}" == "true"))
		{
			for(int i=0; i<params.size(); i++ )
				{
			sh "cd ${workdir} && ansible-playbook destroy_topology.yml -e machine_type=${params[i]}  -e do_api_token=${env.do_ap_token} -e username='jenkins' "
				}
		}
		else
		{
		
			println "Not Running the Stage as destroy droplets flag Set to false"
		}
		

	
	}
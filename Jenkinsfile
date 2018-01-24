#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node


//Define Global Vars

params = ['servers','tpnodes']
workdir = "versalex/src/main/ansible/"
doProps = readJSON text: "${DigitalOcean}"

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
		checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: "${doProps[3]['General'][0]['GitHubBranch']}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '79815ffd-ea78-431b-ae29-277ccd0807f6', url: 'https://github.com/CleoDev/st.git']]]
        sh 'printenv'
            }
//def systestvexImage=docker.build('st-versalex:1.0','.')
    def st_ansibleImage =  docker.image('cleo/ansible:st_4.0');
    withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
    st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    {    
    try{
	    stage('Build Vars')
            {

              buildVars()
            }
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
                installIntegrations()
            } 
            
            
    stage('Configure Product')
            {
                configProduct()
            } 

		

			
        }
        finally{
        
            echo "End of System Testing"
        }
    }
    
    }
  
  
 withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
          try{
			if(("${doProps[2]['AS2'][0]['FilesPerMin']}" > 0) || ("${doProps[2]['FTP'][0]['FilesPerMin']}" > 0) || ("${doProps[2]['SSHFTP'][0]['FilesPerMin']}" > 0) )			
				{
				stage('Setup TestProfiles')
					{
    					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'common,setup-sync' "

			parallel(
				'AS2': {
					if(("${doProps[2]['AS2'][0]['FilesPerMin']}" > 0))
					{
						st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    						{       							
    					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'rest-as2' "
				
    						}
					}
				},
	                
				'FTP': {
					if(("${doProps[2]['FTP'][0]['FilesPerMin']}" > 0))
					{
						st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    						{       							
    					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'rest-ftp' "
				
    						}
					}
				},		                
				'SSH FTP': {
					if(("${doProps[2]['SSHFTP'][0]['FilesPerMin']}" > 0))
					{
						st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    						{       							
    					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'rest-sshftp' "
				
    						}
					}
				}
				)			
						//sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'schedule-actions-server,schedule-actions-tp' "

		            }
		}
		    stage('Run Tests')
		            {
				st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
		    		{       							
					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e as2_totalhosts=${doProps[2]['AS2'][0]['HoststoRun']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']} -e ftp_totalhosts=${doProps[2]['FTP'][0]['HoststoRun']} -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']} -e sshftp_totalhosts=${doProps[2]['SSHFTP'][0]['HoststoRun']} run_tests.yml "
						
    				}		               
		            }
		    stage('Monitor Tests')
		            {
		        st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
			       	{       							
					sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']}  -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']}  monitor_tests.yml "
			       						
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

	def buildVars()
	{
	if(("${doProps[0]['integrations'][0]['Total Droplets']}" <= 0) || ("${doProps[0]['shares'][0]['Total Droplets']}" <= 0) || ("${doProps[0]['versalex'][0]['Total Droplets']}" <= 0))
		{
			echo "Servers Variables Passed by User ${doProps[0]}"
			error("Failing the Build as Servers Integrations/Shares/Versalex Total Droplets Can not be Zero")
		}
	if(("${doProps[1]['integrations'][0]['Total Droplets']}" <= 0) || ("${doProps[1]['shares'][0]['Total Droplets']}" <= 0) || ("${doProps[1]['versalex'][0]['Total Droplets']}" <= 0))
		{
			echo "TPNodes Variables Passed by User ${doProps[1]}"
			error("Failing the Build as TPNodes Integrations/Shares/Versalex Total Droplets Can not be Zero")
		}
		sh "cd ${workdir} && ansible-playbook setup_uservars.yml -e 'do_vars=${DigitalOcean}'   "
	
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

    def installIntegrations()
    {
    println "Install Integrations"
       for(int i=0; i<params.size(); i++ )
        {
        println "Installing Product for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook -i inventories/${params[i]}/ -e machine_type=${params[i]} install_integrations.yml "
        }
    }
    
    def configProduct()
    {
    println "Configuring Product with DB, LDAP, PROXY Configs"
       for(int i=0; i<params.size(); i++ )
        {
        println "Installing Product for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook -i inventories/${params[i]}/ -e machine_type=${params[i]} configure_product.yml "
        }
    }  

		
    def runTests()
    {
    println "Running Tests"

		sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e as2_totalhosts=${doProps[2]['AS2'][0]['HoststoRun']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']} -e ftp_totalhosts=${doProps[2]['FTP'][0]['HoststoRun']} -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']} -e sshftp_totalhosts=${doProps[2]['SSHFTP'][0]['HoststoRun']} run_tests.yml "

     
    } 
    
    def monitorTests()
    {
		println "Monitor Tests in DB"		
		sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']}  -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']}  monitor_tests.yml "
     
    } 
	
    def destroyDroplets(params)
	{
	
		println "Destroy Droplets"
		if(("${doProps[3]['General'][0]['Delete Droplets Afer Tests']}" == "true"))
		{
			for(int i=0; i<params.size(); i++ )
				{
			sh "cd ${workdir} && ansible-playbook destroy_topology.yml -e machine_type=${params[i]}  -e do_api_token=${env.do_ap_token} -e username=${doProps[3]['General'][0]['Username']} "
				}
		}
		else
		{
		
			println "Not Running the Stage as destroy droplets flag Set to false"
		}
		

	
	}
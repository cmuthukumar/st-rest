#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node


//Define Global Vars

params = ['servers','tpnodes']
workdir = "versalex/src/main/ansible/"
String[] protocols=[]
node('pbvt_harmony')
{

color='good'
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
		sh 'sync; echo 3 > /proc/sys/vm/drop_caches '
            }
            
    stage('CheckOut')
        {
// Checkout Github Branch to Specific Directory        
       //checkout scm
		checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: "${gitBranch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '79815ffd-ea78-431b-ae29-277ccd0807f6', url: 'https://github.com/CleoDev/st.git']]]
        sh 'printenv'
            }
//def systestvexImage=docker.build('st-versalex:1.0','.')
    def st_ansibleImage =  docker.image('cleo/ansible:st_6.0');
try
{
    withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
    st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/ -v /root/.aws/:/root/.aws/')
    {
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
			
      		
		stage('Setup TestProfiles')
					{
    					sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}/ setup_testprofiles.yml --tags 'schedule-auto-startup,common,setup-sync' "
						protocols.add('as2')
						protocols.add('ftp')
						protocols.add('sshftp')	
					def stepsDatasets = [:]					
					for (int i = 0; i < protocols.size(); i++) {
			    		def stepProtocol = protocols[i]
			   		 stepsDatasets[stepProtocol] = { ->           
			      			  echo "Running ${stepProtocol}"
							  
					//	st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    					//	{       							
    					sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}/ setup_testprofiles.yml --tags ${stepProtocol} "
				
    					//	}
							
							
			   				 }
					}
					
					parallel stepsDatasets
					
					}	              
				
		    stage('Run Tests')
		            {
				//st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/ -v /root/.aws/:/root/.aws/')
		    		//{       							
					//sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e as2_totalhosts=${doProps[2]['AS2'][0]['HoststoRun']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']} -e ftp_totalhosts=${doProps[2]['FTP'][0]['HoststoRun']} -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']} -e sshftp_totalhosts=${doProps[2]['SSHFTP'][0]['HoststoRun']} run_tests.yml "
					sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}/ -e as2_totalcreatehosts=600 -e as2_filespermin=200 -e as2_totalmins=120 -e as2_totalhosts=600 -e ftp_totalcreatehosts=300 -e ftp_filespermin=200 -e ftp_totalmins=120 -e ftp_totalhosts=300 -e sshftp_totalcreatehosts=300 -e sshftp_filespermin=200 -e sshftp_totalmins=120 -e sshftp_totalhosts=300 run_tests.yml "
						
    				//}		               
		            }
		    stage('Monitor Tests')
		            {
		        //st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/ -v /root/.aws/:/root/.aws/')
			       //	{       							
					sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}/ -e as2_filespermin=200 -e as2_totalmins=120 -e ftp_filespermin=200 -e ftp_totalmins=120  -e sshftp_filespermin=200 -e sshftp_totalmins=120  monitor_tests.yml "
			       						
    				//}	
		            }
					
		    stage('Destroy Droplets')
		            {
		                destroyDroplets(params)
            		} 
		
		
	  }
		
    }
		color='good'
 }
 catch(Exception e)
 {
	
		currentBuild.result = 'FAILURE'
		color='danger'
 
 }
 finally
 {
        echo "End of SysTest..Sending Slack Message"
		url = env.RUN_DISPLAY_URL ?: env.BUILD_URL
		msg="SysTest Job Results branch: `${gitBranch}` status: ${color} "
		message = "<${url}|${env.JOB_NAME}-${CloudProviders}:${env.BUILD_NUMBER}>\n".plus("${msg}")
		slackSend channel: 'systest-jenkins', color: "${color}", message: "${message}"
 }

}


	
    def createNodes(param)
    {
		if(("${CloudProviders}" == 'aws'))
		{
				println "In AWS ${param}"
		//withCredentials([[$class: 'StringBinding', credentialsId: 'systest_aws_access_key_id', variable: 'systest_aws_access_key'], [$class: 'StringBinding', credentialsId: '49b954ef-6c35-4d5e-a3a8-49791bdab948', variable: 'systest_secret_access_key']]) {
  			   sh "cd ${workdir} && ansible-playbook setup_uservars.yml -e cloud_provider=${CloudProviders} -e version=${Version} --tags 'default_vars' "
				
			   sh "cd ${workdir} && ansible-playbook setup_topology.yml -c local -e machine_type=${param} -e cloud_provider=${CloudProviders}  -e username='jenkins' -e sshkey_name='st-versalex' --tags ${CloudProviders} "

			   sh "cd ${workdir} && ansible-playbook setup_vars.yml -c local -i inventories/${CloudProviders}/${param}/ -e cloud_provider=${CloudProviders} -e machine_type=${param} "

				//}	
		}
		else
		{
			println "In DO ${param}"
		withCredentials([[$class: 'StringBinding', credentialsId: 'doCredentials', variable: 'do_ap_token']]) {

			println "Creating Nodes for ${param}"
 			   sh "cd ${workdir} && ansible-playbook setup_uservars.yml -e cloud_provider=${CloudProviders} -e version=${Version} --tags 'default_vars' "

			   sh "cd ${workdir} && ansible-playbook setup_topology.yml -c local -e machine_type=${param} -e cloud_provider=${CloudProviders} -e do_api_token=${env.do_ap_token} -e username='jenkins' -e sshkey_name='st-versalex' --tags ${CloudProviders} "
			   
			   sh "cd ${workdir} && ansible-playbook setup_vars.yml -c local -i inventories/${CloudProviders}/${param}/ -e cloud_provider=${CloudProviders} -e machine_type=${param} "

			}
			
		}
    }
    
    def installProduct(param)
    {
    println "Install Product for ${param} "

			sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${param}/ -e cloud_provider='${CloudProviders}' -e machine_type=${param} install_product.yml "
			

    }

    def installIntegrations(param)
    {
    println "Install Integrations"

        println "Installing Product for ${param}"
           sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${param}/ -e machine_type=${param} install_integrations.yml "

    }
    
    def configProduct(param)
    {
    println "Configuring Product with DB, LDAP, PROXY Configs"
  
        println "Installing Product for ${param}"
           sh "cd ${workdir} && ansible-playbook -i inventories/${CloudProviders}/${param}/ -e machine_type=${param} configure_product.yml "
    
    }  

	
    def destroyDroplets(params)
	{
	
		println "Destroy Droplets ${deleteDroplets}"
		if(("${deleteDroplets}" == true))
		{
			for(int i=0; i<params.size(); i++ )
				{
				
				if(("${CloudProviders}" == 'aws'))
					{				
						sh "cd ${workdir} && ansible-playbook destroy_topology.yml -i inventories/${CloudProviders}/${params[i]}/ -e machine_type=${params[i]} -e username='jenkins' --tags 'aws' "
					}
					else
					{
					
					sh "cd ${workdir} && ansible-playbook destroy_topology.yml -e machine_type=${params[i]}  -e do_api_token=${env.do_ap_token} -e username='jenkins' --tags 'digitalocean' "
					
					}
				}
		}
		else
		{
		
			println "Not Running the Stage as destroy droplets flag Set to false"
		}	

	
	}
#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node


//Define Global Vars

params = ['servers','tpnodes']
workdir = "versalex/src/main/ansible/"

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
       checkout scm
       //checkout([$class: 'GitSCM', branches: [[name: ${gitStBranch}]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', url: 'https://github.com/CleoDev/st.git']]])
        sh 'printenv'
            }
//def systestvexImage=docker.build('st-versalex:1.0','.')
    def st_ansibleImage =  docker.image('cleo/ansible:st_2.0');
    withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
    st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/')
    {    
    try{
    stage('Create Nodes')
            {
                createNodes(params)
            }
    stage('Install Product')
            {
			 steps {
		parallel(
			a: {
				installProduct(params[0])
				},
			b: {
				installProduct(params[1])
				}	  
             )   
            }
			
			}

    stage('Install Integrations')
            {
                installIntegrations()
            } 
            
            
    stage('Configure Product')
            {
                configProduct()
            } 

		
    stage('Setup TestProfiles')
            {
                setupTestProfiles()
            } 

	//stage('Setup Sync')
		//{
		//	setupSync()
		//}  
			
    stage('Run Tests')
            {
                runTests()
            } 
        }
        finally{
        
            echo "End of Create Node"
        }
    }
    
    }
  
}

    def createNodes(params)
    {
    withCredentials([[$class: 'StringBinding', credentialsId: 'doCredentials', variable: 'do_ap_token']]) {
        println "Inside Create Node"
        for(int i=0; i<params.size(); i++ )
        {
        println "Creating Nodes for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook setup_topology.yml -c local -e machine_type=${params[i]} -e do_api_token=${env.do_ap_token} -e username=${username} "
		   
           sh "cd ${workdir} && ansible-playbook setup_vars.yml -c local -i inventories/${params[i]}/ -e machine_type=${params[i]} "
		   }        
        }
    }
    
    def installProduct(param)
    {
    println "Install Product on both "

			sh "cd ${workdir} && ansible-playbook -i inventories/${param}/ -e machine_type=${param} install_product.yml "
					
      // for(int i=0; i<params.size(); i++ )
        //{
        //println "Installing Product for ${params[i]}"
          // sh "cd ${workdir} && ansible-playbook -i inventories/${params[i]}/ -e machine_type=${params[i]} install_product.yml "
        //}
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
	
    def setupTestProfiles()
    {
    println "Setup Test Profiles for Server and TP Side"
   
           sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ setup_testprofiles.yml --tags 'rest' "
     
    } 
	
    def setupSync()
    {
    println "Setup Sync for Server Side"

           sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ setup_sync.yml  "
     
    } 
		
    def runTests()
    {
    println "Running Tests"
		
          // sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e files_per_min=${filesPerMin} -e total_mins=${TotalMins} -e destCounter=2 run_tests.yml "
		  sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=40 -e as2_totalmins=10 -e as2_totalhosts=2  -e ftp_filespermin=40 -e ftp_totalmins=10 -e ftp_totalhosts=2 run_tests.yml "

     
    } 
    
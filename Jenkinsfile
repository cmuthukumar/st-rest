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
    def st_ansibleImage =  docker.image('cleo/ansible:st_6.0');
try
{
    withDockerRegistry([credentialsId: 'DockerCleoSysTest', url: 'https://hub.docker.com/r/cleo/ansible/']) {
    st_ansibleImage.inside('-v /root/.ssh/:/root/.ssh/ -v /root/.aws/:/root/.aws/')
    {
	
	stage('Build Vars')	
	{
	try {	
		runMethodsParallely('buildUserVars',[params[0]])
		}
	catch(e)
		{
		println(" Stage Build Vars  failed. ${e.toString()}")	
		}
	
	}	
    stage('Create Nodes')
           {
			try {	
				runMethodsParallely('createNodes',params)
				}
			catch(e)
				{
				println(" Stage Create Nodes  failed. ${e.toString()}")	
				}

           }
		   
    stage('Install Product')
            {			
			try {	
				runMethodsParallely('installProduct',params)
				}
			catch(e)
				{
				println(" Stage installProduct  failed. ${e.toString()}")	
				}
			}

    stage('Install Integrations')
            {
			try {	
				runMethodsParallely('installIntegrations',params)
				}
			catch(e)
				{
				println(" Stage installIntegrations  failed. ${e.toString()}")	
				}		

		
            } 
            
            
    stage('Configure Product')
            {
			
			try {	
				runMethodsParallely('configProduct',params)
				}
			catch(e)
				{
				println(" Stage configProduct  failed. ${e.toString()}")	
				}	
		
            } 

	stage('Setup TestProfiles')
		{
		try {	
				runMethodsParallely('setupSync',[params[0]])
			}
			catch(e)
			{
				println(" Stage setupSync  failed. ${e.toString()}")	
			}
		}			
      		
	stage('Setup TestProfiles')
		{
				protocols.add('as2')
				protocols.add('ftp')
				protocols.add('sshftp')							
			try {	
				runMethodsParallely('setupTestProfiles',protocols)
				}
			catch(e)
				{
				println(" Stage Setup Test Profiles  failed. ${e.toString()}")	
				}					
				
				
		}	              
				
	stage('Run Tests')
			{
			
			try {	
				runMethodsParallely('runTests',[params[0]])
				}
			catch(e)
				{
				println(" Stage Run Tests  failed. ${e.toString()}")	
				}

			}

	stage('Monitor Tests')
			{
			try {	
				runMethodsParallely('monitorTests',[params[0]])
				}
			catch(e)
				{
				println(" Stage Monitor Tests  failed. ${e.toString()}")	
				}					  							
								

			}
			
	stage('Destroy Instances')
			{
			try {	
				runMethodsParallely('destroyDroplets',params)
				}
			catch(e)
				{
				println(" Stage Destroy Instances  failed. ${e.toString()}")	
				}	
		  
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



def runMethodsParallely(methodName,params)
{  
    try{	
				println "Method name ${methodName}"
				println "Param name ${params}"
				def stepsDatasets = [:]								
				for (int i = 0; i < params.size(); i++)
				{
					def stepProtocol = params[i]
				 stepsDatasets[stepProtocol] = { ->
				 "$methodName"(stepProtocol)		
						}
				}				
					parallel stepsDatasets
				    println "END OF runMethodsParallely"
		
            
	}
	catch(e)
	{	
		println("runMethodsParallely Method failed. ${e.toString()}")	
	}


}

def runPlaybook(playbook,inventory,extraVars,tags) {
retry(1)
    {
    try{
        
        echo "Running Playbook ${playbook}"		
		sh "cd ${workdir} && ansible-playbook ${inventory} ${extraVars} ${playbook} --tags ${tags} "
		echo "Running Playbook ${playbook} Done"
        return
    }catch(Exception e)
    {        
		println "Run Playbook Exception-${e.toString()}"
		currentBuild.result = 'FAILURE'
		color='danger'
		throw e
    }

    }    
}


	def buildUserVars(param)
	{
			runPlaybook("setup_uservars.yml","","-e 'cloud_provider=${CloudProviders}' -e 'version=${Version}'","default_vars")
	
	}
	
    def createNodes(param)
    {

		if(("${CloudProviders}" == 'aws'))
		{
				println "In AWS ${param}"
		//withCredentials([[$class: 'StringBinding', credentialsId: 'systest_aws_access_key_id', variable: 'systest_aws_access_key'], [$class: 'StringBinding', credentialsId: '49b954ef-6c35-4d5e-a3a8-49791bdab948', variable: 'systest_secret_access_key']]) {
			runPlaybook("setup_topology.yml","","-c local -e 'cloud_provider=${CloudProviders}' -e 'machine_type=${param}' -e 'version=${Version}' -e 'username=jenkins' ","${CloudProviders}")
				//}	
		}
		else
		{
			println "In DO ${param}"
		withCredentials([[$class: 'StringBinding', credentialsId: 'doCredentials', variable: 'do_ap_token']]) 
			{
			println "Creating Nodes for ${param}"
			runPlaybook("setup_topology.yml","","-c local -e 'cloud_provider=${CloudProviders}' -e 'machine_type=${param}' -e 'version=${Version}' -e 'do_api_token=${env.do_ap_token}' -e 'sshkey_name=st-versalex' -e 'username=jenkins' ","${CloudProviders}")
			}
		}
		println "Running Setup Vars Playbook"
			runPlaybook("setup_vars.yml","-i inventories/${CloudProviders}/${param}/","-c local -e 'cloud_provider=${CloudProviders}' -e 'machine_type=${param}' ","all")

		
    }
    
    def installProduct(param)
    {
		println "Install Product for ${param} "			
		runPlaybook("install_product.yml","-i inventories/${CloudProviders}/${param}/","-e 'cloud_provider=${CloudProviders}' -e 'machine_type=${param}' ","all")


    }

    def installIntegrations(param)
    {
    println "Install Integrations"

        println "Installing Product for ${param}"
		runPlaybook("install_integrations.yml","-i inventories/${CloudProviders}/${param}/","-e 'machine_type=${param}' ","all")

    }
    
    def configProduct(param)
    {
    println "Configuring Product with DB, LDAP, PROXY Configs"
  
        println "Installing Product for ${param}"
    	runPlaybook("configure_product.yml","-i inventories/${CloudProviders}/${param}/","-e 'machine_type=${param}' ","all")

    }  

    def setupSync(param)
    {
        println "setupSync Method"
    	runPlaybook("setup_testprofiles.yml","-i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}","","schedule-auto-startup,common,setup-sync")

    }  

    def setupTestProfiles(param)
    {
        println "setupTestProfiles for ${param}"
    	runPlaybook("setup_testprofiles.yml","-i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}","","${param}")

    }  	
	
    def runTests(param)
    {
        println "Run Tests for ${param}"
    	runPlaybook("run_tests.yml","-i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}","-e as2_totalcreatehosts=600 -e as2_filespermin=180 -e as2_totalmins=120 -e as2_totalhosts=600 -e ftp_totalcreatehosts=300 -e ftp_filespermin=180 -e ftp_totalmins=120 -e ftp_totalhosts=300 -e sshftp_totalcreatehosts=300 -e sshftp_filespermin=180 -e sshftp_totalmins=120 -e sshftp_totalhosts=300","all")

    }  	

    def monitorTests(param)
    {
        println "Monitor Tests for ${param}"
    	runPlaybook("monitor_tests.yml","-i inventories/${CloudProviders}/${params[0]}/ -i inventories/${CloudProviders}/${params[1]}","-e as2_filespermin=180 -e as2_totalmins=120 -e ftp_filespermin=180 -e ftp_totalmins=120  -e sshftp_filespermin=180 -e sshftp_totalmins=120","all")

    }  	
	
    def destroyDroplets(param)
	{
	
		println "Destroy Droplets ${deleteDroplets}"
		if(("${deleteDroplets}" == true))
		{
			if(("${CloudProviders}" == 'aws'))
				{				
					runPlaybook("destroy_topology.yml","-i inventories/${CloudProviders}/${param}/","-e 'machine_type=${param}' -e 'username=jenkins' ","aws")
				}
				else
				{
					runPlaybook("destroy_topology.yml","-i inventories/${CloudProviders}/${param}/","-e 'machine_type=${param}' -e do_api_token=${env.do_ap_token} -e 'username=jenkins' ","digitalocean")	
				}
				
		}
		else
		{
		
			println "Not Running the destroyDroplets as flag Set to false"
		}	

	
	}
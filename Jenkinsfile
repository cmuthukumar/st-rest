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
       // checkout([$class: 'GitSCM', branches: [[name: ${gitStBranch}]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', url: 'https://github.com/CleoDev/st.git']]])
        sh 'printenv'
            }
//def systestvexImage=docker.build('st-versalex:1.0','.')
    def st_ansibleImage =  docker.image('muthukumarc/st-ansible:1.0');
    
    st_ansibleImage.inside('-u root')
    {    
    try{
    stage('Create Nodes')
            {
                createNodes(params)
            }
    stage('Install Product')
            {
                installProduct()
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

    stage('Setup Sync')
            {
                setupSync()
            }             
            
        }
        finally{
        
            echo "End of Create Node"
        }
    }
  
}

  
    def createNodes(params)
    {
    withCredentials([[$class: 'StringBinding', credentialsId: 'doCredentials', variable: 'do_api_token']]) {
        println "Inside Create Node"
        for(int i=0; i<params.size(); i++ )
        {
        println "Creating Nodes for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook setup_topology.yml -c local -e machine_type=${params[i]} -e do_api_token=$do_api_token"
           sh "cd ${workdir} && ansible-playbook setup_vars.yml -c local -i inventory/ -e machine_type=${params[i]}"
        }
        
        }
    }
    
    def installProduct()
    {
    println "Install Product on both "
       for(int i=0; i<params.size(); i++ )
        {
        println "Installing Product for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook install_product.yml -i inventory/ -e machine_type=${params[i]}"
        }
    }

    def installIntegrations()
    {
    println "Install Integrations"
       for(int i=0; i<params.size(); i++ )
        {
        println "Installing Product for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook install_integrations.yml -i inventory/ -e machine_type=${params[i]}"
        }
    }
    
    def configProduct()
    {
    println "Configuring Product with DB, LDAP, PROXY Configs"
       for(int i=0; i<params.size(); i++ )
        {
        println "Installing Product for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook configure_product.yml -i inventory/ -e machine_type=${params[i]}"
        }
    }  

    def setupTestProfiles()
    {
    println "Setup Test Profiles for Server and TP Side"
   
           sh "cd ${workdir} && ansible-playbook setup_testprofiles.yml -i inventory/ -e server_conf=${params[0]} -e tp_conf=${params[1]}"
     
    } 

    def setupSync()
    {
    println "Setup Sync for Server Side"

           sh "cd ${workdir} && ansible-playbook setup_sync.yml -i inventory/${params[0]} "
     
    } 
    
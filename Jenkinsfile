#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node

def params = ['servers','tpnodes']
//Define env variables
def workdir = "${env.WORKSPACE}/versalex/src/main/ansible/"

node('SysTest') {

def mvnHome = tool 'Mvn3.3.9'
env.WORKSPACE = pwd()
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
       // checkout([$class: 'GitSCM', branches: [[name: 'S-11540-merge-versalex-ansible-code']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', url: 'https://github.com/CleoDev/st.git']]])
        sh 'printenv'
            }
def systestvexImage=docker.build('st-versalex:1.0','.')
    systestvexImage.inside('-u root')
    {    
    try{
    stage('Create Nodes')
            {
                createNodes(params)
            }
    stage('Install Product')
            {
                installProd()
            }            
        }
        finally{
        
            echo "End of Create Node"
        }
    }
  
}

  
    def createNodes(params)
    {
    println "Inside Create Node"
        for(int i=0; i<params.size(); i++ )
        {
        println "Creating Nodes for ${params[i]}"
           sh "cd ${workdir} && ansible-playbook setup_topology.yml -e machine_type=params[i]"
           sh "cd ${workdir} && ansible-playbook setup_vars.yml -e machine_type=params[i]"
        }
    }
    
    def installProd()
    {
    println "Inside Install Product"
       
    }    
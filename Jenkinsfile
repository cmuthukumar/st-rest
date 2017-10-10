#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node

node('SysTest'){

//Define env variables
workdir = '${env.WORKSPACE}/versalex/src/main/ansible/'

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
        //checkout([$class: 'GitSCM', branches: [[name: 'S-11540-merge-versalex-ansible-code']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', url: 'https://github.com/CleoDev/st.git']]])
        sh 'printenv'
            }
def systestvexImage=docker.build('st-versalex:1.0','.')            
    systestvexImage.inside('-u root')
    {    
    try{
    stage('Create Nodes')
        {
        sh "mvn install -Pcreate-nodes -f '${env.WORKSPACE}/versalex/pom.xml'  -Dplaybook.path='${env.WORKSPACE}/versalex/src/main/ansible/setup_topology.yml' -Dmachine.type='servers' "     
        sh "mvn install -Psetup-vars -f '${env.WORKSPACE}/versalex/pom.xml'  -Dplaybook.path='${env.WORKSPACE}/versalex/src/main/ansible/setup_vars.yml' -Dmachine.type='servers' "
            }
        }
        finally{
        
            echo "End of Create Node"
        }
    }
    

}
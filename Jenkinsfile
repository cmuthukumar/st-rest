#!groovy
import org.yaml.snakeyaml.Yaml
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import hudson.model.Node

node('SysTest'){
workdir = '/root/workspace/st-versalex/versalex/src/main/ansible/'
// Clone Github and Checkout Branch to Specific Directory
def mvnHome = tool 'Mvn3.3.9'
env.WORKSPACE = pwd()
sh "cd ${env.WORKSPACE}"
env.JAVA_HOME="${tool 'JDK1.8'}"
env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
sh 'java -version'
def systestvexImage=docker.build('st-versalex:1.0','.')
//withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', passwordVariable: 'GITPWD', usernameVariable: 'GITUSR']]) {
stage('Clean') {
    deleteDir()
    sh 'ls -lah'
}   
stage('CheckOut')
{
checkout scm
//checkout([$class: 'GitSCM', branches: [[name: 'S-11540-merge-versalex-ansible-code']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '683540f0-61a9-48c1-acef-dc5520fb6466', url: 'https://github.com/CleoDev/st.git']]])

sh 'printenv'

    systestvexImage.inside('-u root')
    {

        sh 'ansible --version'
        sh 'cd versalex'
        sh 'ls -lart'
          //sh "${mvnHome}/bin/mvn -Pdeploy-nexus clean deploy -f '${env.WORKSPACE}/versalex/pom.xml' "
        sh "cd ${workdir} && ansible-playbook setup_vars.yml -e machine_type=servers"
      

    }
}

}
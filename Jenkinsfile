#!groovy

node('SysTest'){
// Clone Github and Checkout Branch to Specific Directory
def mvnHome = tool 'Mvn3.3.9'
env.WORKSPACE = pwd()
sh "cd ${env.WORKSPACE}/versalex/"
env.JAVA_HOME="${tool 'JDK1.8'}"
env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
sh 'java -version'
stage('CheckOut')
{
checkout scm

globals("${env.BRANCH_NAME}")
sh 'printenv'

def stvexImage=docker.build(st-versalex:1.0,.)

    stvexImage.inside(-u root)
    {

        sh 'ansible --version'
          //sh "${mvnHome}/bin/mvn -Pdeploy-nexus clean deploy -f '${env.WORKSPACE}/versalex/pom.xml' "


    }
}


}
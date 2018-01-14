import org.boon.Boon;
 
def jsonEditorOptions = Boon.fromJson(/{
        disable_edit_json: true,
        disable_properties: true,
        no_additional_properties: true,
        disable_collapse: true,
        disable_array_add: true,
        disable_array_delete: true,
        disable_array_reorder: true,
        theme: "bootstrap2",
        iconlib:"fontawesome4",
       "schema":{
  "title": "SystemTest",
  "type": "array",
  "format":"tabs",
  "items": {
     "title": "{{self.name}}",
     "headerTemplate": "{{self.name}}",
     "type": "object",
     "properties": {
		    "name" : {
		    "type": "string", 
		     "options": {
		      "hidden": true
				  }
				     },
 "versalex": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {

                                  "Product" : {
                                      "type": "string", 
                                        "enum": [
                                              "Harmony",
                                               "VLTrader"                                               
                                            ],
                                            "default": "Harmony",
                                            "propertyOrder": 1
                                  },
                                  
                                  "Version" : {
                                      "type": "string", 
                                        "enum": [
                                              "5.4.1-SNAPSHOT",
                                               "5.4.2-SNAPSHOT"
                                            ],
                                            "propertyOrder": 2
                                  },                                  

                                  "Total Droplets" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1,
                                                2,                                                
                                                4,                                                
                                                6
                                            ],
                                            "default": 0,
                                            "propertyOrder": 5
                                  },

                                  "RAM" : {
                                      "type": "string", 
                                        "enum": [
                                               "2gb",
                                               "4gb",
                                               "8gb"
                                            ],
                                            "propertyOrder": 4
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
                                              "centos-6-x64",
                                               "centos-7-x64"
                                            ],
                                            "default": "centos-7-x64",
                                             "propertyOrder": 3
                                  }

                              }
                         }
                     },                    
                     
 "integrations": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {

                                  "Product" : {
                                      "type": "string", 
                                        "enum": [
                                              "mysql"                                              
                                            ],
                                            "default": "mysql",
                                            "propertyOrder": 1
                                  },

                                  "Total Droplets" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1                                                
                                            ],
                                            "default": 0,
                                            "propertyOrder": 4
                                            
                                  },

                                  "RAM" : {
                                      "type": "string", 
                                        "enum": [
                                              "2gb",
                                               "4gb",
                                               "8gb"
                                            ],
                                            "propertyOrder": 3
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
                                              "centos-6-x64",
                                               "centos-7-x64"
                                            ],
                                            "default": "centos-7-x64",
                                            "propertyOrder": 2
                                  }

                              }
                         }
                     },
                                          
                                          
 "proxies": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {

                                  "Product" : {
                                      "type": "string", 
                                        "enum": [
                                              "VLProxy"
                                            ],
                                            "default": "VLProxy",
                                            "propertyOrder": 1
                                  },

                                  "Version" : {
                                      "type": "string", 
                                        "enum": [
                                              "5.4.1-SNAPSHOT",
                                               "5.4.2-SNAPSHOT"
                                            ],
                                            "propertyOrder": 2
                                  },  

                                  "Total Droplets" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1,
                                                2,                                                
                                                4,                                                
                                                6
                                            ],
                           	"default": 0,
                           	"propertyOrder": 5
                                  },

                                  "RAM" : {
                                      "type": "string", 
                                        "enum": [
                                              "2gb",
                                               "4gb",
                                               "8gb"
                                            ],
                                            "propertyOrder": 4
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
                                              "centos-6-x64",
                                               "centos-7-x64"
                                            ],
                                            "default": "centos-7-x64",
                                            "propertyOrder": 3
                                  }

                              }
                         }
                     },
                     

 "shares": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {
                                  "Total Droplets" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1                                                
                                            ],
                                            "default": 0,
                                            "propertyOrder": 3
                                  },

                                  "RAM" : {
                                      "type": "string", 
                                        "enum": [
                                              "2gb",
                                               "4gb",
                                               "8gb"
                                            ],
                                            "propertyOrder": 2
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
                                              "centos-6-x64",
                                               "centos-7-x64"
                                            ],
                                            "default": "centos-7-x64",
                                            "propertyOrder": 1
                                  }

                              }
                         }
                     },                    


 "AS2": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {
                                  "Total Mins" : {
                                      "type": "integer", 
                                       "default": 0,
                                       "propertyOrder": 3
                                  },

                                  "FilesPerMin" : {
                                      "type": "integer",
                                       "default": 0,
                                       "propertyOrder": 2

                                  },

                                  "Total Hosts" : {
                                      "type": "integer", 
                                      "default": 0,
                                      "propertyOrder": 1
                                       
                                  },
                                  
                                  "HoststoRun" : {
                                      "type": "integer", 
                                      "default": 0,
                                      "propertyOrder": 4
                                       
                                  }
                                  

                              }
                         }
                     } ,                    
                     
 "FTP": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {
                                  "Total Mins" : {
                                      "type": "integer", 
                                       "default": 0,
                                       "propertyOrder": 3
                                  },

                                  "FilesPerMin" : {
                                      "type": "integer",
                                       "default": 0,
                                       "propertyOrder": 2
                                  },

                                  "Total Hosts" : {
                                      "type": "integer", 
                                      "default": 0,
                                       "propertyOrder": 1                                      
                                       
                                  },
                                  
                                  "HoststoRun" : {
                                      "type": "integer", 
                                      "default": 0,
                                      "propertyOrder": 4                                                                            
                                       
                                  }
                                  

                              }
                         }
                     },
                     
 "SSHFTP": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {
                                  "Total Mins" : {
                                      "type": "integer", 
                                       "default": 0,
                                       "propertyOrder": 3
                                  },

                                  "FilesPerMin" : {
                                      "type": "integer",
                                       "default": 0,
                                       "propertyOrder": 2
                                  },

                                  "Total Hosts" : {
                                      "type": "integer", 
                                      "default": 0,
                                       "propertyOrder": 1                                      
                                       
                                  },
                                  
                                  "HoststoRun" : {
                                      "type": "integer", 
                                      "default": 0,
                                      "propertyOrder": 4                                                                            
                                       
                                  }
                                  

                              }
                         }
                     },
                     
 "General": {
                         "type": "array",
                         "format": "table",
                         "items": {
                             "type": "object",
                              "properties": {
                                  "GitHubBranch" : {
                                      "type": "string", 
                                       "default": "S-12506-SFTP-protocol-setup",
                                       "propertyOrder": 1
                                  },

                                  "Username" : {
                                      "type": "string",
                                       "default": 0,
                                       "propertyOrder": 2
                                  },

                                  "Delete Droplets Afer Tests" : {
                                      "type": "boolean", 
                                      "default": false,
                                       "propertyOrder": 3                                      
                                       
                                  },
   
                                  

                              }
                         }
                     },                     
                     
                                                               
                     
  }
  }
},
   startval: [
{
"name": "servers",
"versalex": [{
                    "Product":,
              }
             ],
"integrations": [{
                    "Product":,
              }
             ],
"proxies": [{
                    "Product":,
              }
             ],
             
"shares": [{
                    "RAM":,
              }
             ]
},
             {
"name": "tpnodes",
"versalex": [{
                    "Product":,
              }
             ],
"integrations": [{
                    "Product":,
              }
             ],             
"shares": [{
                    "RAM":,
              }
             ]
             
                 },
                                  
             {
"name": "dataset",
	"AS2": [{
		"Total Hosts": 200,
		"Total Mins": 10,
		"FilesPerMin": 100,		
		"HoststoRun": 10
		}],
	"FTP": [{
		"Total Mins": 10,
		"FilesPerMin": 100,
		"Total Hosts": 200,
		"HoststoRun": 10
		}],		
	"SSHFTP": [{
		"Total Mins": 10,
		"FilesPerMin": 100,
		"Total Hosts": 200,
		"HoststoRun": 10
		}]		
                
                },
 		{               
"name": "general",
	"General": [{
		"GitHubBranch": "S-12506-SFTP-protocol-setup",
		"Username": "jenkins",
		"Delete Droplets Afer Tests": false,
		}],
		
                
                }                
                
                
             ]

}
/);
 
return jsonEditorOptions;


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
    def st_ansibleImage =  docker.image('cleo/ansible:st_3.0');
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
              //  createNodes(params)
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
    stage('Destroy Droplets')
            {
                destroyDroplets()
            } 			
			
        }
        finally{
        
            echo "End of System Testing"
        }
    }
    
    }
  
}


	def buildVars()
	{
	if(("${doProps[0]['integrations'][0]['Total Droplets']}" <= 0) || ("${doProps[0]['shares'][0]['Total Droplets']}" <= 0) || ("${doProps[0]['versalex'][0]['Total Droplets']}" <= 0))
		{
			echo "Servers Variables Passed by User ${doProps[0]}"
			error("Failing the Build as Servers Integrations /Shares/Versalex Total Droplets Can not be Zero")
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
      //  println "Inside Create Node"
       // for(int i=0; i<params.size(); i++ )
       // {
        println "Creating Nodes for ${param}"
           sh "cd ${workdir} && ansible-playbook setup_topology.yml -c local -e machine_type=${param} -e do_api_token=${env.do_ap_token} -e username=${doProps[3]['General'][0]['Username']} -e sshkey_name='st-versalex' "
		   
           sh "cd ${workdir} && ansible-playbook setup_vars.yml -c local -i inventories/${param}/ -e machine_type=${param} "
		  // }        
        }
    }
    
    def installProduct(param)
    {
    println "Install Product for ${param} "

			sh "cd ${workdir} && ansible-playbook -i inventories/${param}/ -e machine_type=${param} install_product.yml "
					
     //  for(int i=0; i<params.size(); i++ )
     //  {
			//println "Installing Product for ${params[i]}"
          //sh "cd ${workdir} && ansible-playbook -i inventories/${params[i]}/ -e machine_type=${params[i]} install_product.yml "
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
		//  sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${AS2filesPerMin} -e as2_totalmins=${AS2TotalMins} -e as2_totalhosts=${AS2TotalHosts}  -e ftp_filespermin=${FTPfilesPerMin} -e ftp_totalmins=${FTPTotalMins} -e ftp_totalhosts=${FTPTotalHosts} -e sshftp_filespermin=${FTPfilesPerMin} -e sshftp_totalmins=${FTPTotalMins} -e sshftp_totalhosts=${FTPTotalHosts} run_tests.yml "
		sh "cd ${workdir} && ansible-playbook -i inventories/${params[0]}/ -i inventories/${params[1]}/ -e as2_filespermin=${doProps[2]['AS2'][0]['FilesPerMin']} -e as2_totalmins=${doProps[2]['AS2'][0]['Total Mins']} -e as2_totalhosts=${doProps[2]['AS2'][0]['HoststoRun']} -e ftp_filespermin=${doProps[2]['FTP'][0]['FilesPerMin']} -e ftp_totalmins=${doProps[2]['FTP'][0]['Total Mins']} -e ftp_totalhosts=${doProps[2]['FTP'][0]['HoststoRun']} -e sshftp_filespermin=${doProps[2]['SSHFTP'][0]['FilesPerMin']} -e sshftp_totalmins=${doProps[2]['SSHFTP'][0]['Total Mins']} -e sshftp_totalhosts=${doProps[2]['SSHFTP'][0]['HoststoRun']} run_tests.yml "

     
    } 
    
    def destroyDroplets()
	{
	
		println "Destroy Droplets"
		if(("${doProps[3]['General'][0]['Delete Droplets Afer Tests']}"))
		{
			sh "cd ${workdir} && ansible-playbook destroy_topology.yml -e machine_type=tpnodes  -e do_api_token=${env.do_ap_token} -e username=${doProps[3]['General'][0]['Username']} "
		}
		else
		{
		
			println "Not Running the Stage as destroy droplets flag Set to false"
		}
		

	
	}
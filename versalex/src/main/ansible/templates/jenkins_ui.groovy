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
     "title":  "{{self.name}}",
     "headerTemplate":  "{{self.name}}",
     "type": "object",
     "properties": {
		    "name" : {
		    "type": "string", 
		    "readOnly": "true",
		     "options": {
		      "hidden": true
				  }
				     },
				     
 "digitalocean": {
             "title": "digitalocean",
             "type": "array",
             "format":"tabs",
             "items": {
                 "title": "{{self.name}}",
                 "headerTemplate":  "{{self.name}}",
                 "type": "object",
                 "properties": {
                    "name" : {
                         "title": "systest",
                         "type": "string",
                         "options": {
			 "hidden": true
				  }
 
                     },	     
				     
				     
 "versalex": {
                         "type": "array",
                         "format": "table",
                         "propertyOrder": 1,
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
                                        "enum": ["5.6-SNAPSHOT", "5.5-Portal-SNAPSHOT", "5.5.0-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "nickp20180318-SNAPSHOT", "Jenkins-test-SNAPSHOT", "develop-SNAPSHOT", "5.5.0.0", "5.5-RC-4.0", "5.5-RC-3.0", "5.5-RC-2.0", "5.5-RC-1.0", "5.5-RC-0.6", "5.5-RC-0.5", "5.5-RC-0.4", "5.5-RC-0.3", "5.5-RC-0.2", "5.5-RC-0.1", "5.4.1.19", "5.4.1.18", "5.4.1.17", "5.4.1.16", "5.4.1.15", "5.4.1.14", "5.4.1.13", "5.4.1.12", "5.4.1.11", "5.4.1.10", "5.4.1.9", "5.4.1.8", "5.4.1.7", "5.4.1.6", "5.4.1.4", "5.4.1.3", "5.4.1.2", "5.4.1.1", "5.4.1.0", "5.4.1.0-RC-5.0", "5.4.1.0-RC-4.0", "5.4.1.0-RC-3.0", "5.4.1.0-RC-2.0", "5.4.1.0-RC-1.3", "5.4.1.0-RC-1.2", "5.4.1.0-RC-1.1", "jenkins-version-test-12", "jenkins-version-test-11", "jenkins-version-test-9", "jenkins-version-test-8", "jenkins-version-test-7", "jenkins-version-test-6", "jenkins-version-test-3", "jenkins-version-test-2", "jenkins-version-test-1"],
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
                                               "8gb",
											   "16gb",
											   "32gb",
											   "48gb"											   
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
                         "propertyOrder": 3,
                         "items": {
                             "type": "object",
                              "properties": {

                                  "Product" : {
                                      "type": "string", 
                                        "enum": [
                                              "mysql",
                                              "oracle_11g"
                                            ],
                                            "default": "mysql",
                                            "propertyOrder": 1
                                  },
                                  
                                  "Version" : {
                                      "type": "string", 
                                        "enum": [
                                              "mysql:5.7"                                               
                                            ],
                                            "options": {
					        "enum_titles": [
					          "mysql:5.7",
					          "oracle_11.2.0.4.0"
    						]
    					},
                                            "default": "mysql:5.7",
                                            "propertyOrder": 2
                                  },                                   

                                  "Total Droplets" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1                                                
                                            ],
                                            "default": 0,
                                            "propertyOrder": 5
                                            
                                  },
								  
                                  "RAM" : {
                                      "type": "string", 
                                        "enum": [
                                              "2gb",
                                               "4gb",
                                               "8gb",
											   "16gb",
											   "32gb",
											   "48gb"											   
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
                                          
                                          
 "proxies": {
                         "type": "array",
                         "format": "table",
                         "propertyOrder": 2,
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
                                        "enum": ["5.6-SNAPSHOT", "5.5-Portal-SNAPSHOT", "5.5.0-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "nickp20180318-SNAPSHOT", "Jenkins-test-SNAPSHOT", "develop-SNAPSHOT", "5.5.0.0", "5.5-RC-4.0", "5.5-RC-3.0", "5.5-RC-2.0", "5.5-RC-1.0", "5.5-RC-0.6", "5.5-RC-0.5", "5.5-RC-0.4", "5.5-RC-0.3", "5.5-RC-0.2", "5.5-RC-0.1", "5.4.1.19", "5.4.1.18", "5.4.1.17", "5.4.1.16", "5.4.1.15", "5.4.1.14", "5.4.1.13", "5.4.1.12", "5.4.1.11", "5.4.1.10", "5.4.1.9", "5.4.1.8", "5.4.1.7", "5.4.1.6", "5.4.1.4", "5.4.1.3", "5.4.1.2", "5.4.1.1", "5.4.1.0", "5.4.1.0-RC-5.0", "5.4.1.0-RC-4.0", "5.4.1.0-RC-3.0", "5.4.1.0-RC-2.0", "5.4.1.0-RC-1.3", "5.4.1.0-RC-1.2", "5.4.1.0-RC-1.1", "jenkins-version-test-12", "jenkins-version-test-11", "jenkins-version-test-9", "jenkins-version-test-8", "jenkins-version-test-7", "jenkins-version-test-6", "jenkins-version-test-3", "jenkins-version-test-2", "jenkins-version-test-1"],
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
                                               "8gb",
											   "16gb",
											   "32gb",
											   "48gb"											   
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
                         "propertyOrder": 4,
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
                                               "8gb",
											   "16gb",
											   "32gb",
											   "48gb"											   
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
	 "aws": {
             "title": "aws",
             "type": "array",
             "format":"tabs",
             "items": {
                 "title": "{{self.name}}",
                 "headerTemplate":  "{{self.name}}",
                 "type": "object",
                 "properties": {
                    "name" : {
                         "title": "systest",
                         "type": "string",
                         "options": {
			 "hidden": true
				  }
 
                     },	     
				     
				     
 "versalex": {
                         "type": "array",
                         "format": "table",
                         "propertyOrder": 1,
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
                                        "enum": ["5.6-SNAPSHOT", "5.5-Portal-SNAPSHOT", "5.5.0-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "nickp20180318-SNAPSHOT", "Jenkins-test-SNAPSHOT", "develop-SNAPSHOT", "5.5.0.0", "5.5-RC-4.0", "5.5-RC-3.0", "5.5-RC-2.0", "5.5-RC-1.0", "5.5-RC-0.6", "5.5-RC-0.5", "5.5-RC-0.4", "5.5-RC-0.3", "5.5-RC-0.2", "5.5-RC-0.1", "5.4.1.19", "5.4.1.18", "5.4.1.17", "5.4.1.16", "5.4.1.15", "5.4.1.14", "5.4.1.13", "5.4.1.12", "5.4.1.11", "5.4.1.10", "5.4.1.9", "5.4.1.8", "5.4.1.7", "5.4.1.6", "5.4.1.4", "5.4.1.3", "5.4.1.2", "5.4.1.1", "5.4.1.0", "5.4.1.0-RC-5.0", "5.4.1.0-RC-4.0", "5.4.1.0-RC-3.0", "5.4.1.0-RC-2.0", "5.4.1.0-RC-1.3", "5.4.1.0-RC-1.2", "5.4.1.0-RC-1.1", "jenkins-version-test-12", "jenkins-version-test-11", "jenkins-version-test-9", "jenkins-version-test-8", "jenkins-version-test-7", "jenkins-version-test-6", "jenkins-version-test-3", "jenkins-version-test-2", "jenkins-version-test-1"],
                                            "propertyOrder": 2
                                  },                                  

                                  "Total Instances" : {
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

                                  "Instance Type" : {
                                      "type": "string", 
                                        "enum": [
                                               "t2.medium",
                                               "t2.large",
                                               "t2.xlarge",
											   "t2.2xlarge"											   
                                            ],
                                            "propertyOrder": 4
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [										
										  "ami-d874e0a0",
										  "ami-223f945a"										  										  
                                            ],
														"options": {
										"enum_titles": [
										"Amazon Linux 1",
										"RHEL 7.4"								
										]
									},
                                            "default": "Amazon Linux 1",
                                             "propertyOrder": 3
                                  }

                              }
                         }
                     },                    
                     
 "integrations": {
                         "type": "array",
                         "format": "table",
                         "propertyOrder": 3,
                         "items": {
                             "type": "object",
                              "properties": {

                                  "Product" : {
                                      "type": "string", 
                                        "enum": [
                                              "mysql",
                                              "oracle_11g"
                                            ],
                                            "default": "mysql",
                                            "propertyOrder": 1
                                  },
                                  
                                  "Version" : {
                                      "type": "string", 
                                        "enum": [
                                              "mysql:5.7",
                                               "sath89\/oracle-xe-11g:latest"
                                            ],
                                            "options": {
					        "enum_titles": [
					          "mysql:5.7",
					          "oracle_11.2.0.4.0"
    						]
    					},
                                            "default": "mysql:5.7",
                                            "propertyOrder": 2
                                  },                                   

                                  "Total Instances" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1                                                
                                            ],
                                            "default": 0,
                                            "propertyOrder": 5
                                            
                                  },

                                  "Instance Type" : {
                                      "type": "string", 
                                        "enum": [
                                               "t2.medium",
                                               "t2.large",
                                               "t2.xlarge",
											   "t2.2xlarge"
                                            ],
                                            "propertyOrder": 4
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
										  "ami-d874e0a0",
										  "ami-223f945a"
                                            ],
														"options": {
										"enum_titles": [
										"Amazon Linux 1",
										"RHEL 7.4"
										]
									},
                                            "default": "Amazon Linux 1",
                                             "propertyOrder": 3
                                  }

                              }
                         }
                     },
                                          
                                          
 "proxies": {
                         "type": "array",
                         "format": "table",
                         "propertyOrder": 2,
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
                                        "enum": ["5.6-SNAPSHOT", "5.5-Portal-SNAPSHOT", "5.5.0-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "nickp20180318-SNAPSHOT", "Jenkins-test-SNAPSHOT", "develop-SNAPSHOT", "5.5.0.0", "5.5-RC-4.0", "5.5-RC-3.0", "5.5-RC-2.0", "5.5-RC-1.0", "5.5-RC-0.6", "5.5-RC-0.5", "5.5-RC-0.4", "5.5-RC-0.3", "5.5-RC-0.2", "5.5-RC-0.1", "5.4.1.19", "5.4.1.18", "5.4.1.17", "5.4.1.16", "5.4.1.15", "5.4.1.14", "5.4.1.13", "5.4.1.12", "5.4.1.11", "5.4.1.10", "5.4.1.9", "5.4.1.8", "5.4.1.7", "5.4.1.6", "5.4.1.4", "5.4.1.3", "5.4.1.2", "5.4.1.1", "5.4.1.0", "5.4.1.0-RC-5.0", "5.4.1.0-RC-4.0", "5.4.1.0-RC-3.0", "5.4.1.0-RC-2.0", "5.4.1.0-RC-1.3", "5.4.1.0-RC-1.2", "5.4.1.0-RC-1.1", "jenkins-version-test-12", "jenkins-version-test-11", "jenkins-version-test-9", "jenkins-version-test-8", "jenkins-version-test-7", "jenkins-version-test-6", "jenkins-version-test-3", "jenkins-version-test-2", "jenkins-version-test-1"],
                                            "propertyOrder": 2
                                  },  

                                  "Total Instances" : {
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
								  
                                  "Instance Type" : {
                                      "type": "string", 
                                        "enum": [
                                               "t2.medium",
                                               "t2.large",
                                               "t2.xlarge",
											   "t2.2xlarge"
                                            ],
                                            "propertyOrder": 4
                                  },

                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
										  "ami-d874e0a0",
										  "ami-223f945a"
                                            ],
														"options": {
										"enum_titles": [
										"Amazon Linux 1",
										"RHEL 7.4"
										]
									},
                                            "default": "Amazon Linux 1",
                                             "propertyOrder": 3
                                  }

                              }
                         }
                     },
                     

 "shares": {
                         "type": "array",
                         "format": "table",
                         "propertyOrder": 4,
                         "items": {
                             "type": "object",
                              "properties": {
                                  "Total Instances" : {
                                      "type": "integer", 
                                        "enum": [
                                        	0,
                                                1                                                
                                            ],
                                            "default": 0,
                                            "propertyOrder": 3
                                  },
		
                                  "Instance Type" : {
                                      "type": "string", 
                                        "enum": [
                                               "t2.medium",
                                               "t2.large",
                                               "t2.xlarge",
											   "t2.2xlarge"
                                            ],
                                            "propertyOrder": 2
                                  },
								  
                                  "OS" : {
                                      "type": "string", 
                                        "enum": [
										  "ami-d874e0a0",
										  "ami-223f945a"
                                            ],
														"options": {
										"enum_titles": [
										"Amazon Linux 1",
										"RHEL 7.4"
										]
									},
                                            "default": "Amazon Linux 1",
                                             "propertyOrder": 3
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
	}
	
	
	
  }
}
  
},
   startval: [
{
"name": "digitalocean",
"digitalocean": [
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
		"Total Hosts": 600,
		"Total Mins": 120,
		"FilesPerMin": 250,		
		"HoststoRun": 600
		}],
	"FTP": [{
		"Total Mins": 120,
		"FilesPerMin": 250,
		"Total Hosts": 300,
		"HoststoRun": 300
		}],		
	"SSHFTP": [{
		"Total Mins": 120,
		"FilesPerMin": 250,
		"Total Hosts": 300,
		"HoststoRun": 300
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
             
             },
             {
	     "name": "aws",	     
	     "aws": [
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
	                         "Instance Type":,
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
	                         "Instance Type":,
	                   }
	                  ]
	                  
	                      },
	                                       
	                  {
	     "name": "dataset",
	     	"AS2": [{
	     		"Total Hosts": 600,
	     		"Total Mins": 120,
	     		"FilesPerMin": 250,		
	     		"HoststoRun": 600
	     		}],
	     	"FTP": [{
	     		"Total Mins": 120,
	     		"FilesPerMin": 250,
	     		"Total Hosts": 300,
	     		"HoststoRun": 300
	     		}],		
	     	"SSHFTP": [{
	     		"Total Mins": 120,
	     		"FilesPerMin": 250,
	     		"Total Hosts": 300,
	     		"HoststoRun": 300
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
	     
          
             ]

}
/);
 
return jsonEditorOptions;
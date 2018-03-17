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
				     
 "do": {
             "title": "do",
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
                                        "enum": ["5.5-Portal-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "5.3.1-SNAPSHOT", "5.3.0.x-SNAPSHOT", "5.3.0-SNAPSHOT", "5.3-SNAPSHOT", "5.3.0.0-RC-SNAPSHOT", "5.2.2-SNAPSHOT", "5.2.1-SNAPSHOT", "5.2.1-RC-SNAPSHOT", "1.0.0.0-SNAPSHOT", "Jenkins-test-SNAPSHOT"],
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
                                              "mysql:latest",
                                               "sath89\/oracle-xe-11g:latest"
                                            ],
                                            "options": {
					        "enum_titles": [
					          "mysql:latest",
					          "oracle_11.2.0.4.0"
    						]
    					},
                                            "default": "mysql:latest",
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
                                        "enum": ["5.5-Portal-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "5.3.1-SNAPSHOT", "5.3.0.x-SNAPSHOT", "5.3.0-SNAPSHOT", "5.3-SNAPSHOT", "5.3.0.0-RC-SNAPSHOT", "5.2.2-SNAPSHOT", "5.2.1-SNAPSHOT", "5.2.1-RC-SNAPSHOT", "1.0.0.0-SNAPSHOT", "Jenkins-test-SNAPSHOT"],
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
                                        "enum": ["5.5-Portal-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "5.3.1-SNAPSHOT", "5.3.0.x-SNAPSHOT", "5.3.0-SNAPSHOT", "5.3-SNAPSHOT", "5.3.0.0-RC-SNAPSHOT", "5.2.2-SNAPSHOT", "5.2.1-SNAPSHOT", "5.2.1-RC-SNAPSHOT", "1.0.0.0-SNAPSHOT", "Jenkins-test-SNAPSHOT"],
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
										  "ami-223f945a",
										  "ami-79873901"
                                            ],
														"options": {
										"enum_titles": [
										"RHEL 7.4",
										"Ubuntu 16.04"
										]
									},
                                            "default": "RHEL 7.4",
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
                                              "mysql:latest",
                                               "sath89\/oracle-xe-11g:latest"
                                            ],
                                            "options": {
					        "enum_titles": [
					          "mysql:latest",
					          "oracle_11.2.0.4.0"
    						]
    					},
                                            "default": "mysql:latest",
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
										  "ami-223f945a",
										  "ami-79873901"
                                            ],
														"options": {
										"enum_titles": [
										"RHEL 7.4",
										"Ubuntu 16.04"
										]
									},
                                            "default": "RHEL 7.4",
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
                                        "enum": ["5.5-Portal-SNAPSHOT", "5.5-SNAPSHOT", "5.4.2-SNAPSHOT", "5.4.1-SNAPSHOT", "5.4-SNAPSHOT", "5.3.1-SNAPSHOT", "5.3.0.x-SNAPSHOT", "5.3.0-SNAPSHOT", "5.3-SNAPSHOT", "5.3.0.0-RC-SNAPSHOT", "5.2.2-SNAPSHOT", "5.2.1-SNAPSHOT", "5.2.1-RC-SNAPSHOT", "1.0.0.0-SNAPSHOT", "Jenkins-test-SNAPSHOT"],
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
										  "ami-223f945a",
										  "ami-79873901"
                                            ],
														"options": {
										"enum_titles": [
										"RHEL 7.4",
										"Ubuntu 16.04"
										]
									},
                                            "default": "RHEL 7.4",
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
										  "ami-223f945a",
										  "ami-79873901"
                                            ],
														"options": {
										"enum_titles": [
										"RHEL 7.4",
										"Ubuntu 16.04"
										]
									},
                                            "default": "RHEL 7.4",
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
"do": [
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
		"Total Hosts": 1500,
		"Total Mins": 30,
		"FilesPerMin": 1000,		
		"HoststoRun": 259
		}],
	"FTP": [{
		"Total Mins": 30,
		"FilesPerMin": 1000,
		"Total Hosts": 500,
		"HoststoRun": 359
		}],		
	"SSHFTP": [{
		"Total Mins": 30,
		"FilesPerMin": 1000,
		"Total Hosts": 500,
		"HoststoRun": 459
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
	     
          
             ]

}
/);
 
return jsonEditorOptions;
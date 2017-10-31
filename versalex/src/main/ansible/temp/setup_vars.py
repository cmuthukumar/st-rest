import requests
from requests.auth import HTTPBasicAuth
import json

url ='http://67.207.88.177:5080/api/certs'
# session = requests.Session()
# session.auth = ('administrator','Admin')

data = '''{
  "requestType":"generateCert",
  "alias":"AS2HTest",
  "dn":"cn=as2htest,c=us,st=in",
  "validity":"24 months",
  "alg":"sha1",
  "keyAlg":"rsa",
  "keySize":1024,
  "extendedKeyUsage":["clientAuth"],
  "keyUsage":["digitalSignature","keyEncipherment"],
  "password": "cleo"
}'''
response = requests.post(url, data=data,auth=('administrator','Admin'))
print "response-out", response
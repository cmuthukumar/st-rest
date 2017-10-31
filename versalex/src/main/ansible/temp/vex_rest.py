import requests
from requests.auth import HTTPBasicAuth
import json
from base64 import b64encode

url ='http://67.207.88.177:5080/api/certs'
# usrPwd = b64encode("administrator:Admin")
# print "Base64 Encode",usrPwd
head ={ 'Content-type':'application/json','Accept':'application/json'}
data = {
  "requestType":"generateCert",
  "alias":"AS2H222",
  "dn":"cn=as2htest,c=us,st=in",
  "validity":"24 months",
  "alg":"sha1",
  "keyAlg":"rsa",
  "keySize":1024,
  "extendedKeyUsage":["clientAuth"],
  "keyUsage":["digitalSignature","keyEncipherment"],
  "password": "cleo"
}
res = requests.post(url,headers=head,auth=HTTPBasicAuth('administrator', 'Admin'),data=json.dumps(data))
print "response-Authorization", res.headers
print "response-body", res.text
jp=json.loads(res.text)
print "response-body-cert", jp['certificate']
#!/usr/bin/python

DOCUMENTATION = '''
module: test_hello
short_description: "Checks if a process is running on a target machine"
author:
  - your name
requirements:
  - only standard library needed
options:
  name:
    description:
      - the name of the process you want to check
      required: true
      default: null
example:  is_running: name=etcd
'''

from ansible.module_utils.basic import *


def main():

    module = AnsibleModule(argument_spec={})
    response = {"hello": "world"}
    module.exit_json(changed=False,meta=response)

if __name__ == '__main__':
   main()
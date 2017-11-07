Create Machines
=========

  * Create Machines on DigitalOcean based on yaml config file

Requirements
------------
1. Digital Ocean API TOKEN - Login to DigitalOcean to get your API token

Role Variables
--------------

---
hardware:
   versalex:           
      - name: harmony -- (Versalex product names.. ex: harmony, lexicom, vltrader)
        ram_size: 1gb -- (Machine ramsize... ex: 1gb,gb,4gb,8gb,16gb)
        region: nyc1  -- (Region Names ...ex: nyc1,nyc2,nyc3,)
        image_name: "centos-6-x64" -- (CentOS, Ubuntu...
        qty: 2 -- 
        
        

Dependencies
------------

A list of other roles hosted on Galaxy should go here, plus any details in regards to parameters that may need to be set for other roles, or variables that are used from other roles.

Example Playbook
----------------

Including an example of how to use your role (for instance, with variables passed in as parameters) is always nice for users too:

    - hosts: servers
      roles:
         - { role: username.rolename, x: 42 }

License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

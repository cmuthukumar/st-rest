Install Product
=========

A brief description of the role goes here.

Requirements
------------

  * Installs versalex products(generates license and configure them) and external applications required for setting up versalex products.


Role Variables
--------------

A description of the settable variables for this role should go here, including any variables that are in defaults/main.yml, vars/main.yml, and any variables that can/should be set via parameters to the role. Any variables that are read from other roles and/or the global scope (ie. hostvars, group vars, etc.) should be mentioned here as well.

Dependencies
------------

A list of other roles hosted on Galaxy should go here, plus any details in regards to parameters that may need to be set for other roles, or variables that are used from other roles.

Example Playbook
----------------

###Install Product (Ansible Role)
----------------------------------------
  * Installs versalex products(generates license and configure them) required for setting up versalex products.
	* Installs versalex products(Harmony, VLTrader, Lexicom, VLProxy) on ips inputted

* Versalex Install:

```
Sample yaml config file for non-dockerized install for versalex product harmony
<Ansible Playbook DirPath>/install_product.yml
---
---
hardware:
   versalex:           
      - name: harmony
        ram_size: 4gb
        region: nyc1
        image_name: "centos-7-x64"
        qty: 2
software:
   versalex:
      - name: harmony
        version: "5.4.1-SNAPSHOT"
        install_location: "/root/Harmony/"   
      
 ```

License
-------

BSD

Author Information
------------------

An optional section for the role authors to include contact information, or a website (HTML is not allowed).

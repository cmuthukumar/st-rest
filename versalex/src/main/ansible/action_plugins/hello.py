from collections import namedtuple
from ansible.parsing.dataloader import DataLoader
from ansible.vars import VariableManager
from ansible.inventory import Inventory
from ansible.playbook.play import Play
from ansible.executor.task_queue_manager import TaskQueueManager

Options = namedtuple('Options',
                ['connection', 'module_path', 'forks', 'become',
                 'become_method', 'become_user', 'check']
            )
            
            
#initialize needed objects

variable_manager = VariableManager()
loader = DataLoader()

options = Options(
    connection='local', module_path='', forks=100, become=True,
    become_method='sudo', become_user='root', check=False)
passwords = dict(vault_pass='secret')

#create inventory and pass to variable manager

inventory = Inventory(loader=loader, variable_manager=variable_manager,
                      host_list='localhost')
variable_manager.set_inventory(inventory)

# create play with tasks
play_source =  dict(
        name = "Ansible Play",
        hosts = 'localhost',
        gather_facts = 'no'
         # result = dict(failed=False, changed=False, msg="Hello World")        
         # tasks = [
             # dict(module='hello',failed=False, changed=False, msg="Hello World")
             # # dict(action=dict(module='hello', msg="Hello World"))
          # ]
    )
play = Play().load(play_source, variable_manager=variable_manager, loader=loader)

# actually run it
tqm = None
try:
    tqm = TaskQueueManager(
              inventory=inventory,
              variable_manager=variable_manager,
              loader=loader,
              options=options,
              passwords=passwords,
              stdout_callback=dict(failed=False, changed=False, msg="Hello World")
          )
   # res = dict(failed=False, changed=False, msg="Hello World") 
     result = tqm.run(play)
finally:
    if tqm is not None:
        tqm.cleanup()

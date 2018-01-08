package com.cleo.systest;

//import com.couchbase.client.deps.io.netty.util.internal.StringUtil;

public class SetupOmniUsers {

    /**
     * Used to Pass User Passed Configs to another Util Class
     *
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {
        try {
          // System.out.println("Args Passed by User is " + "VersalEx CSV Path::"+args);

            for (String arg : args) {
                System. out.println(arg);
            }

            SetupUsers users = new SetupUsers();
            UtilTestProfile scheduleutil;
            scheduleutil = new UtilTestProfile();

            if(args.length <2)
            {
                System.out.println("Proper Usage is:  java SetupOmniUsers 1. Product Name 2. Product Location 3.Protocol Type 4.Host Range(ex: 1-1000) 5. Mailbox Range(ex: 1-100) 7. Action Range (1-400) 8. TP Host IPAddress 9. TP Host Port 10. Server Share ") ;
                System.exit(0);
            }
             users.setupUserprops(args[0],args[1],args[2],args[3],args[4],args[5],args[6],Integer.parseInt(args[7]),args[8],args[9],args[10]);


        } catch (Exception e) {

            System.out.println("Setting up Omni Users Setup failed with exception " + e.getMessage());

        } finally {

            System.exit(0);
        }

    }


}

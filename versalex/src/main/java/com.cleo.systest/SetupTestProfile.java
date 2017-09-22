package com.cleo.systest;

import com.couchbase.client.deps.io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;

public class SetupTestProfile {

    /**
     * Used to Pass User Passed Configs to another Util Class
     *
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Args Passed by User is " + "VersalEx CSV Path::" + args[0] + "Versalex Product" + args[1] );
            AS2Setup as2setup = new AS2Setup();
            FTPSetup ftpsetup = new FTPSetup();
            UtilTestProfile scheduleutil;
            scheduleutil = new UtilTestProfile();

            if(args.length <2)
            {
                System.out.println("Proper Usage is:  java SetupTestProfile 1. Product Name 2. Product Location 3.Protocol Type 4.Host Range(ex: 1-1000) 5. Mailbox Range(ex: 1-100) 7. Action Range (1-400) 8. TP Host IPAddress 9. TP Host Port 10. Server Share ") ;
                System.exit(0);
            }
            if(args[2].toLowerCase().startsWith("as2"))
            {
                as2setup.setupAS2props(args[0],args[1],args[3],args[4],args[5],args[6],args[7],args[8],args[9],args[10],args[11],args[12]);

            }
            if(args[2].toLowerCase().startsWith("ftp"))
            {
                ftpsetup.setupFTPprops(args[0],args[1],args[3],args[4],args[5],args[6],args[7],args[8],args[9],args[10],args[11],args[12]);
            }
            if(Boolean.parseBoolean(args[2]) ) {
                scheduleutil.setProduct(args[0]);
                scheduleutil.setVexPath(args[1]);
                scheduleutil.setupSchedule();
            }

        } catch (Exception e) {

            System.out.println("Setting up As2 failed with exception" + e.getMessage());

        } finally {

            System.exit(0);
        }

    }


}

package com.cleo.systest;

import com.cleo.security.hashing.VLPBKDF2Authenticator;

import com.opencsv.CSVReader;

public class SetupVersalex_WMart {

    /**
     * Used to Pass User Passed Configs to another Util Class
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Args Passed by User is " + "VersalEx CSV Path::" + args[0] + "Versalex Product" + args[1] + "Versalex Path" + args[2] + "Host IP File" + args[3]);

            UtilTestdata_WMart setupTestdata = new UtilTestdata_WMart();

            setupTestdata.setCsvPath(args[0]);

            setupTestdata.loadHostIPPropsFile(args[3]);

            setupTestdata.loadTestdata();

            if (args[1].toLowerCase().startsWith("harmony")) {
                setupTestdata.setProduct(3);
            }

            if (args[1].toLowerCase().startsWith("vltrader")) {
                setupTestdata.setProduct(2);
            }

            if (args[1].toLowerCase().startsWith("lexicom")) {
                setupTestdata.setProduct(1);
            }
            setupTestdata.setVexPath(args[2]);

            setupTestdata.createVersalex();

         //   setupTestdata.cleanUpVersalExHosts();
            for (int n = 0; n < setupTestdata.vextestdata.size(); n++) {

                System.out.println("Record No" + n + "size of it" + setupTestdata.vextestdata.size());
                setupTestdata.setHostProps(n);
                setupTestdata.setupEachHost();
                setupTestdata.setupSchedule();
            }
            setupTestdata.closeVersalex();
        } catch (Exception e) {

            System.out.println("Setting up As2 failed with exception" + e.getMessage());

        } finally {

            System.exit(0);
        }

    }


}

package com.cleo.systest;

public class SetupVersalex_New {

    /**
     * Used to Pass User Passed Configs to another Util Class
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("Args Passed by User is " + "VersalEx CSV Path::" + args[0] + "Versalex Product" + args[1] + "Versalex Path" + args[2] );

            UtilTestdata_New setupTestdata = new UtilTestdata_New();

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

            for (int n = 0; n < setupTestdata.vextestdata.size(); n++) {

                System.out.println("Record No" + n + "size of it" + setupTestdata.vextestdata.size());
                setupTestdata.setHostProps(n);
                setupTestdata.setupEachHost();

            }
            setupTestdata.closeVersalex();
        } catch (Exception e) {

            System.out.println("Setting up As2 failed with exception" + e.getMessage());

        } finally {

            System.exit(0);
        }

    }


}

/*
package com.cleo.systest;

import com.cleo.lexicom.certmgr.external.CertificateInfo;
import com.cleo.lexicom.certmgr.external.ICertManagerRunTime;
import com.cleo.lexicom.external.ILexiCom;
import com.cleo.lexicom.external.ISchedule;
import com.cleo.lexicom.external.LexiComFactory;
import com.cleo.security.hashing.VLPBKDF2Authenticator;
import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class UtilTestdata_WMart {

    public String vexHomePath;
    public String csvpath;
    public List<Map<String, String>> vextestdata = new ArrayList<Map<String, String>>();
    ILexiCom versalex = null;
    public int vexproduct;
    public Map<String, String> vexHostProps;
    private String[] vexHostPath = new String[3];
    public HashMap<String, String> cert_map = new HashMap<String, String>();
    Properties props = new Properties();

    */
/**
     * Used to Set VersalEx Product Location/Path where it is installed for getting versalex object
     *
     * @param Dirpath
     *//*

    public void setVexPath(String Dirpath) {

        vexHomePath = Dirpath;
    }

    */
/**
     * Used to get Versalex  Product Path/Location
     *
     * @param Dirpath
     * @return
     *//*

    public String getVexPath(String Dirpath) {

        return this.vexHomePath;

    }

    */
/**
     * Loading Properties Files which had IP addresses pf created machines.. Used for Replacing IP address at run time while generating test data
     *
     * @param propsfile
     *//*

    public void loadHostIPPropsFile(String propsfile) {
        InputStream ioStream;
        try {
            File propf=new File(propsfile);
            ioStream = new FileInputStream(propf);
            if (ioStream != null) {
                props.load(ioStream);

            }


        } catch (Exception e) {

            System.out.println("Exception while loading Hosts IP Address file" + e.getMessage());
        }

    }

    */
/**
     * Used to Set Path of Test Profile CSV
     *
     * @param filePath
     *//*


    public void setCsvPath(String filePath) {

        csvpath = filePath;


    }

    public String getCsvPath() {

       return csvpath;


    }


    public void cleanUpVersalExHosts()
    {

        try{

            for (String hostslist : versalex.list(ILexiCom.HOST, vexHostPath)) {
                vexHostPath[0]=hostslist;
                if(!hostslist.toLowerCase().contains("listener")) {
                    System.out.println("Removing Existing Hosts in VersalEX" + hostslist);
                    vexHostPath[1] = versalex.remove(ILexiCom.HOST, vexHostPath);
                }

            }


        }catch(Exception e)

        {
            System.out.println("Exception thrown while cleaning up hosts are"+e.getMessage());




        }

    }



    */
/**
     * Used to set Product Name/Number for VersalEx Product .. to get Versal Ex Object
     *
     * @param product
     *//*

    public void setProduct(int product) {

        vexproduct = product;


    }

    */
/**
     * Used to get Specific Row / Details of Test Profile from User Passed CSV(which used for creating Test Profiles)
     *
     * @param n
     *//*


    public void setHostProps(int n) {

        vexHostProps = vextestdata.get(n);

    }

    */
/**
     * Creating VersalEx Product Object with VexProduct, Versalex Path
     *
     * @throws Exception
     *//*


    public void createVersalex() throws Exception {

        try {
            System.out.println("Creating Versalex");

            versalex = LexiComFactory.getVersaLex(this.vexproduct, this.vexHomePath, LexiComFactory.CLIENT_ONLY);

        } catch (Exception e) {

            System.out.println("Exception thrown while creating versalex object" + e.getMessage());
            if (this.versalex != null)
                this.versalex.close();
        } finally {


        }


    }

    */
/**
     * CLosing VersalEx Object
     *
     * @throws Exception
     *//*

    public void closeVersalex() throws Exception {
        try {
            System.out.println("Closing Versalex");
            if (this.versalex != null)
                this.versalex.close();
        } catch (Exception e) {

            System.out.println("Exception thrown while closing Versalex" + e.getMessage());

        }

    }

    */
/**
     * Set REST Advanced Property to User Passed values
     *//*

    public void setRestAdvanced() {

        try {

            versalex.setProperty(ILexiCom.HOST, vexHostPath, "Advanced", vexHostProps.get("host_advanced"));


        } catch (Exception e)

        {

            System.out.println("Exception thrown while setting setRestAdvanced method is" + e.getMessage());

        }


    }

    */
/**
     * Iterate and Check Host Alias Exists in Existing List of Elements
     *
     * @param hostname
     * @return
     * @throws Exception
     *//*


    public boolean checkHostExists(String hostname) throws Exception {


        for (String hostelem : this.versalex.list(ILexiCom.HOST, vexHostPath)) {

            if (hostname.equalsIgnoreCase(hostelem))

            {
               // System.out.println("Returning True");
                vexHostPath[0]=hostname;
                return true;
            }

        }


        return false;


    }

    */
/**
     * Sets Hosts Name based on whether it exists or not
     *
     * @param hostname
     * @throws Exception
     *//*



    private void setHostPath(String hostname) throws Exception

    {

        try {
            long setHostValues = System.currentTimeMillis();
            System.out.println("Current Time setHostPath Host  Start" + new Date(setHostValues));


            System.out.println("Host Element Passed by USer" + hostname);
          //  if (checkHostExists(hostname)) {
               // System.out.println("Before Service Alias"+vexHostPath[0]);
            //    if ((versalex.getHostProtocol(vexHostPath[0]) == 13)) {
                  //  System.out.println("into 13");
               //     vexHostPath[1]="";
                //    vexHostPath[1] = versalex.create(ILexiCom.MAILBOX, vexHostPath, vexHostProps.get("mailbox_alias"), true);

             //   }

            //    if ((vexHostProps.get("service_alias").length() >0)) {
                 //   System.out.println("into Servicse Alias hsotname"+vexHostPath[0]);
                 //   vexHostPath[0]=hostname;
                   // versalex.setProperty(ILexiCom.SERVICE, vexHostPath, "alias",vexHostProps.get("service_alias") );
              //      vexHostPath[1]=vexHostProps.get("service_alias");
              //  }

     //       } else {


                vexHostPath[0] = versalex.activateHost(vexHostProps.get("host_defaultalias"), vexHostProps.get("host_alias"), true);
                vexHostPath[1]=null;
                if ((versalex.getHostProtocol(vexHostPath[0]) == 13) && (vexHostPath[1] == null)) {

                    vexHostPath[1] = versalex.create(ILexiCom.MAILBOX, vexHostPath, vexHostProps.get("mailbox_alias"), true);

                }
                if ((versalex.getHostProtocol(vexHostPath[0]) != 13)) {
                    //setRestAdvanced();
                   for (String mailboxlist : versalex.list(ILexiCom.MAILBOX, vexHostPath)) {
                        vexHostPath[1] = mailboxlist;

                        vexHostPath[1] = versalex.rename(ILexiCom.MAILBOX, vexHostPath, vexHostProps.get("mailbox_alias"));

                   }

                    for (String actionlist : versalex.list(ILexiCom.ACTION, vexHostPath)) {
                        vexHostPath[2] = actionlist;
                        versalex.remove(ILexiCom.ACTION, vexHostPath);

                    }

                }

         //   }
         //   versalex.save(vexHostPath[0]);
            System.out.println("Current Time setHostPath Host  End" + new Date(setHostValues));

        } catch (Exception e) {

            System.out.println("Exception thrown in checkHostExistence is" + e.getMessage());
            if (this.versalex != null)
                this.versalex.close();

        } finally {

        }


    }


    */
/**
     * Import Certificate for VersalEx Products .. This is for Receiver Side and Set Local Listener and Mailbox Level Properties for Certificates created
     *//*

    public void import_cert()

    {
        try {
            String certfile_path;
            String certkey_path;
            String filepath;
            ICertManagerRunTime rt = versalex.getCertManager();
            certfile_path = cert_map.get("ImportLocation") + cert_map.get("ImportUserAlias") + ".cer";
            certkey_path = cert_map.get("ImportLocation") + cert_map.get("ImportUserAlias") + ".pem";
            System.out.println("Certificate File Path is" + certfile_path);
            System.out.println("Certificate Private  Key Path is" + certkey_path);
            rt.importUserCertKey(cert_map.get("ImportUserAlias"), new File(certfile_path), new File(certkey_path), cert_map.get("ImportPrivateKeyPwd"), true);

            filepath = this.vexHomePath + File.separator + "certs" + File.separator + cert_map.get("ImportUserAlias") + ".cer";

            rt.exportUserCert(cert_map.get("ImportUserAlias"), new File(filepath), true, true);

            String cert_path = "certs" + File.separator + cert_map.get("ImportUserAlias").toLowerCase() + ".cer";

            versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "serversigncertfile", cert_path);
            versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "servercertfile", cert_path);
          //  String[] local_listener_path = {"Local Listener"};
          //  versalex.setProperty(ILexiCom.HOST, local_listener_path, "localsigncertalias", cert_map.get("ImportUserAlias"));
        } catch (Exception e)

        {

            System.out.println("Exception thrown while importing Cert" + e.getMessage());

        }

    }


    */
/**
     * Create Certs using Values Passed by User and Set Local Listener and Mailbox Level Properties for Certificates created
     *//*

    public void create_cert()

    {
        try {
            long certCreate = System.currentTimeMillis();
            System.out.println("Current Time Cert Create Start" + new Date(certCreate));

            String filepath;
            String fileexp_path;
            String private_keypath;
            System.out.println("Into Cert Creation");


            ICertManagerRunTime rt = versalex.getCertManager();

            CertificateInfo ci = new CertificateInfo();
            ci.setClientAuth(true);
            ci.setDigitalSignature(true);
            ci.setKeyEncipherment(true);
            System.out.println("Useralias value is::" + cert_map.get("UserAlias"));
            ci.setCommonName(cert_map.get("CommonName"));
            ci.setEmailAddress(cert_map.get("Email"));
            ci.setOrganizationalUnit(cert_map.get("OrgUnit"));
            ci.setCountry(cert_map.get("Country"));
            ci.setStateOrProvince(cert_map.get("State"));

            rt.generateUserCertKey(cert_map.get("UserAlias"), ci, cert_map.get("PrivateKeyPwd"), true);
            filepath = this.vexHomePath + File.separator + "certs" + File.separator + cert_map.get("UserAlias") + ".cer";
            rt.exportUserCert(cert_map.get("UserAlias"), new File(filepath), true, true);
            File file = new File(cert_map.get("ExportLocation"));

            if (!file.exists()) {
                file.mkdir();

            }
            private_keypath = cert_map.get("ExportLocation") + cert_map.get("UserAlias") + ".pem";
            fileexp_path = cert_map.get("ExportLocation") + cert_map.get("UserAlias") + ".cer";
            rt.exportUserCert(cert_map.get("UserAlias"), new File(fileexp_path), true, true);
            rt.exportUserKey(cert_map.get("UserAlias"), new File(private_keypath), "cleo", true);
            String cert_path = "certs" + File.separator + cert_map.get("UserAlias").toUpperCase() + ".cer";
            System.out.println("Encryption Certificate Path" + cert_path);
            versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "serversigncertfile", cert_path);
            versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "servercertfile", cert_path);
            versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "Usesigncert", "TRUE");
          //  String[] local_listener_path = {"Local Listener"};
           // versalex.setProperty(ILexiCom.HOST, local_listener_path, "localsigncertalias", cert_map.get("UserAlias"));


            long certCreateEnd = System.currentTimeMillis();
            System.out.println("Current Time Cert Create End" + new Date(certCreateEnd));


        } catch (Exception e) {

            System.out.println("Excetion  thrown while creating Certificate is" + e.getMessage());
        }


    }

    */
/**
     * Set Host /Mailbox /Action/ Certs /Local Listener Values using USer Passed Values
     *//*


    public void setHostValues() {

        try {

            ArrayList<String> headers = new ArrayList<String>();
            int i = 0;

            cert_map.clear();
            for (String hostelem : vexHostProps.keySet()) {
                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("host_")) && (!hostelem.toLowerCase().equalsIgnoreCase("host_defaultalias"))) {
                    if ((hostelem.toLowerCase().startsWith("host_address"))) {

                        versalex.setProperty(ILexiCom.HOST, vexHostPath, hostelem.split("_")[1], props.getProperty(vexHostProps.get(hostelem.toString())));
                    } else {
                        versalex.setProperty(ILexiCom.HOST, vexHostPath, hostelem.split("_")[1], vexHostProps.get(hostelem.toString()));
                    }
                    continue;

                }

                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("mailboxh_"))) {
                    String test = "PUT " + hostelem.split("_")[1] + "=" + vexHostProps.get(hostelem.toString());
                    headers.add(test);
                    continue;

                }

                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("mailbox_"))) {

                    if (hostelem.toLowerCase().contains("_pwdhash")) {
                        //System.out.println("FTP_Server Mailbox Password PWDHAsh...PATH var::"+Arrays.toString(vexHostPath));
                        VLPBKDF2Authenticator authenticator = new VLPBKDF2Authenticator();
                        String pwdHash = authenticator.createHash("", vexHostProps.get(hostelem));
                        versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "Pwdhash", pwdHash);
                    } else {
                        versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, hostelem.split("_")[1], vexHostProps.get(hostelem.toString()));

                    }
                    //versalex.save(vexHostPath[0]);
                    continue;
                }

                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("action_"))) {

                    versalex.setProperty(ILexiCom.ACTION, vexHostPath, hostelem.split("_")[1], vexHostProps.get(hostelem.toString()));
                    continue;
                }


                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("cert_"))) {

                    //	System.out.println("Setting Each Key for Cert Properties is"+hostelem+"Cert Value is"+vexHostProps.get(hostelem));

                    cert_map.put(hostelem.split("_")[1], vexHostProps.get(hostelem));
                    continue;

                }


                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("locallistener_"))) {


                    String[] local_listener_path = {"Local Listener"};
                    //	if(versalex.getProperty(ILexiCom.HOST, local_listener_path, "localsigncertpassword")!=null)
                    //	{
                    versalex.setProperty(ILexiCom.HOST, local_listener_path, hostelem.split("_")[1], vexHostProps.get(hostelem.toString()));
                    continue;
                    //	}


                }


                if ((vexHostProps.get(hostelem) != null) && (vexHostProps.get(hostelem).length() != 0) && (hostelem.toLowerCase().startsWith("service_"))) {

                   if (hostelem.toLowerCase().contains("_hostname"))
                    {

                       versalex.setProperty(ILexiCom.SERVICE, vexHostPath, hostelem.split("_")[1], props.getProperty(vexHostProps.get(hostelem.toString())));
                        continue;
                    }else
                    {
                        versalex.setProperty(ILexiCom.SERVICE, vexHostPath, hostelem.split("_")[1], vexHostProps.get(hostelem.toString()));

                        continue;
                    }


                }


            }

            if (!headers.isEmpty()) {
                String[] headersarray = headers.toArray(new String[0]);
                System.out.println("Headers to Save is:: "+Arrays.toString(headersarray));
                versalex.setProperty(ILexiCom.MAILBOX, vexHostPath, "Header", headersarray);

            }
            versalex.save(vexHostPath[0]);

        } catch (Exception e) {

            System.out.println("Exception thrown setHostProps is" + e.getMessage());

        }


    }

    */
/**
     * Sets up Host Properties for Each Row (Test Profile) from Test Profile CSV
     *
     * @throws Exception
     *//*


    public void setupEachHost() throws Exception {

        try {
            long setHostPath = System.currentTimeMillis();
            System.out.println("Current Time setHostPath Host  Start" + new Date(setHostPath));
            setHostPath(vexHostProps.get("host_alias"));
            long setHostPathEnd = System.currentTimeMillis();
            System.out.println("Current Time setHostPath Host  End" + new Date(setHostPathEnd));

            setRestAdvanced();
            if (vexHostProps.get("action_alias").length() > 0) {
                vexHostPath[2] = versalex.create(ILexiCom.ACTION, vexHostPath, vexHostProps.get("action_alias"), true);
            }
            long setHostValues = System.currentTimeMillis();
            System.out.println("Current Time SetHostValues Host  Start" + new Date(setHostValues));
            setHostValues();
            long setHostValuesEnd = System.currentTimeMillis();
            System.out.println("Current Time SetHostValues Host  End" + new Date(setHostValuesEnd));
            if ((vexHostProps.get("Cert_ImportLocation") != null) && vexHostProps.get("Cert_ImportLocation").length() > 0) {
                import_cert();
                //System.out.println("Into Imprt Done");
            }
            if ((vexHostProps.get("Cert_UserAlias") != null) && vexHostProps.get("Cert_UserAlias").length() > 0) {
                create_cert();

            }

            versalex.save(vexHostPath[0]);
        } catch (Exception e) {

            System.out.println("Exception thrown while settingup Host is " + e.getMessage());
            System.out.println("Closing VersalEx Object");
            if (this.versalex != null)
                this.versalex.close();
        } finally {
            System.out.println("Finally Closing VersalEx Object");

        }


    }

    */
/**
     * Load CSV File into ArrayList of Hash Maps to access CSV values easily by referring row wise
     *
     * @throws IOException
     *//*


    public void loadTestdata() throws IOException  {

        CSVReader csvReader=new CSVReader(new FileReader(this.csvpath));;

        try {

            int total_rows = 0;
            List<String[]> hosts = csvReader.readAll();
            String[] headers = hosts.get(0);
            System.out.println("Headers are::" + Arrays.toString(headers));
            for (String[] row : hosts) {
                Map<String, String> each_rows = new HashMap<String, String>();

                if (row == hosts.get(0)) continue;
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].contains("H_") || headers[i].startsWith("Cert_")) {
                        each_rows.put(headers[i], row[i]);
                    } else {

                        each_rows.put(headers[i].toLowerCase(), row[i]);
                    }

                }
                System.out.println("total_rows" + total_rows + "rows" + each_rows.toString());
                this.vextestdata.add(total_rows, each_rows);
                total_rows = total_rows + 1;

            }
            System.out.println("Size of testdatas::" + this.vextestdata.size());
            csvReader.close();
        } catch (Exception e)

        {
            System.out.println("Excdeption thrown while Executing Laod Testdata method"+e.getMessage());

        } finally {

            if (csvReader != null)
              csvReader.close();



        }

    }

    */
/**
     * Sets up Schedule for Actions
     *
     * @throws Exception
     *//*


    public void setupSchedule() {

        try{

            ISchedule schedule = versalex.getSchedule();
            ISchedule.Item item;
            ISchedule.Item.Calendar calendar;
            ISchedule.Item.Calendar.Time time;
            String[] path = new String[] {"","",""};

            path[0]= vexHostProps.get("host_alias");
            path[1]= vexHostProps.get("mailbox_alias");
            path[2]= vexHostProps.get("action_alias");
            System.out.println("Setting Scheduler for Path"+path);
            item = schedule.newItem(path);
            //item.setOnlyIfFile(Boolean.parseBoolean(vexHostProps.get("schedule_onlyiffile").toLowerCase()), Boolean.parseBoolean(vexHostProps.get("schedule_continuous").toLowerCase()));
            item.setOnlyIfFile(true,true);
            schedule.updateItem(item, false);
            schedule.save();
            System.out.println("Scheduling done");

        }catch(Exception e)

        {

            System.out.println("Exception thrown while setting up Schedule is "+e.getMessage());
        }

    }



}
*/

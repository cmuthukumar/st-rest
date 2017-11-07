package com.cleo.systest;


import com.cleo.lexicom.certmgr.external.CertificateInfo;
import com.cleo.lexicom.certmgr.external.ICertManagerRunTime;
import com.cleo.lexicom.external.ILexiCom;
import com.cleo.lexicom.external.ISchedule;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.BookBoolRecord;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by muthukumarc on 03-11-2016.
 */
public class AS2Setup {
    public int hostNo;
    public int mbxNo;
    public int actionNo;
    public String hostAlias;
    public String mbxAlias;
    public String actionAlias;
    public String certPath;
    ILexiCom versalex = null;
    String[] vexpath = new String[3];
    UtilTestProfile as2util;

    public void setDefaultHostProps(String map_hostip, String map_port, String share_macro) throws Exception {
        try {

            versalex.setProperty(ILexiCom.HOST, vexpath, "Address", map_hostip);
            versalex.setProperty(ILexiCom.HOST, vexpath, "Port", map_port);
            if (!StringUtils.isEmpty(share_macro)) {
                versalex.setProperty(ILexiCom.HOST, vexpath, "Inbox", share_macro + File.separator + "inboxes" + File.separator + this.hostAlias + File.separator);
                versalex.setProperty(ILexiCom.HOST, vexpath, "Outbox", share_macro + File.separator + "outboxes" + File.separator + this.hostAlias + File.separator);
                versalex.setProperty(ILexiCom.HOST, vexpath, "Sentbox", share_macro + File.separator + "sentboxes" + File.separator + this.hostAlias + File.separator);
                versalex.setProperty(ILexiCom.HOST, vexpath, "Receivedbox", share_macro + File.separator + "receivedboxes" + File.separator + this.hostAlias + File.separator);
            }


        } catch (Exception e) {

            System.out.println("Exception in setDefaultHostProps" + e.getMessage());

        }

    }


    public void setDefaultMailboxProps() throws Exception {
        try {

            ArrayList<String> mbxheaders = new ArrayList<String>();
            for (mbxprops props : mbxprops.values()) {

                if (!props.mbxkey.toLowerCase().startsWith("mbxalias")) {
                    // System.out.println("Setting up mbox props"+props.mbxkey + " => "+props.mbxvalue);
                    versalex.setProperty(ILexiCom.MAILBOX, vexpath, props.mbxkey, props.mbxvalue);
                }

            }
            mbxheaders.add("PUT [Content-Type]=X12");
            mbxheaders.add("PUT Subject=X12MSg");
            mbxheaders.add("PUT AS2-From=" + this.mbxAlias);
            mbxheaders.add("PUT AS2-To=" + this.mbxAlias);
            String[] headersarray = mbxheaders.toArray(new String[0]);
            System.out.println("Headers to Save is:: " + Arrays.toString(headersarray));
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "Header", headersarray);

        } catch (Exception e) {

            System.out.println("Exception in setDefaultMailboxProps" + e.getMessage());

        }

    }

    public void create_cert()

    {
        try {

            String filepath;
            String fileexp_path;
            String private_keypath;
            // System.out.println("Into Cert Creation");
            ICertManagerRunTime rt = versalex.getCertManager();
            CertificateInfo ci = new CertificateInfo();
            ci.setClientAuth(true);
            ci.setDigitalSignature(true);
            ci.setKeyEncipherment(true);
            //     System.out.println("Useralias value is::" + this.hostAlias);
            ci.setCommonName(this.hostAlias);
            ci.setEmailAddress(this.hostAlias + "@cleo.com");
            ci.setOrganizationalUnit(this.hostAlias);
            ci.setCountry(certprops.COUNTRYCODE.certexportvalue);
            ci.setStateOrProvince(this.hostAlias);

            rt.generateUserCertKey(this.hostAlias, ci, certprops.PASSPHRASE.certexportvalue, true);
            filepath = as2util.getVexPath() + File.separator + certprops.CERTS.certexportvalue + File.separator + this.hostAlias + ".cer";
            rt.exportUserCert(this.hostAlias, new File(filepath), true, true);
            String exportLocation = as2util.getVexPath() + File.separator + certprops.EXPORTFOLDER.certexportvalue + File.separator;
            File file = new File(exportLocation);

            if (!file.exists()) {
                file.mkdir();

            }
            private_keypath = exportLocation + this.hostAlias + ".pem";
            fileexp_path = exportLocation + this.hostAlias + ".cer";
            rt.exportUserCert(this.hostAlias, new File(fileexp_path), true, true);
            rt.exportUserKey(this.hostAlias, new File(private_keypath), certprops.PASSPHRASE.certexportvalue, true);
            this.certPath = certprops.CERTS.certexportvalue + File.separator + this.hostAlias.toUpperCase() + ".cer";
            System.out.println("Certificate Path  Generated is" + this.certPath);

        } catch (Exception e) {

            System.out.println("Excetion  thrown while creating Certificate is" + e.getMessage());
        }


    }

    public void setup_MbxCertPath() {
        try {

            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "serversigncertfile", this.certPath);
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "servercertfile", this.certPath);
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "localsigncertalias", this.hostAlias);
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "localsigncertpassword", versalex.encode(certprops.PASSPHRASE.certexportvalue));
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "Usesigncert", "true");
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "localencrcertalias", this.hostAlias);
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "localencrcertpassword", versalex.encode(certprops.PASSPHRASE.certexportvalue));

        } catch (Exception e) {

            System.out.println("Excetion  thrown while Setting up Certificate Path is" + e.getMessage());
        }


    }

    /**
     * Import Certificate for VersalEx Products .. This is for Receiver Side and Set Local Listener and Mailbox Level Properties for Certificates created
     */

    public void import_cert()

    {
        try {
            String certfile_path;
            String certkey_path;
            String filepath;
            ICertManagerRunTime rt = versalex.getCertManager();
            String importLocation = as2util.getVexPath() + File.separator + certprops.EXPORTFOLDER.certexportvalue + File.separator;

            certfile_path = importLocation + this.hostAlias + ".cer";
            certkey_path = importLocation + this.hostAlias + ".pem";
            rt.importUserCertKey(this.hostAlias, new File(certfile_path), new File(certkey_path), certprops.PASSPHRASE.certexportvalue, true);

            filepath = as2util.getVexPath() + File.separator + certprops.CERTS.certexportvalue + File.separator + this.hostAlias + ".cer";

            rt.exportUserCert(this.hostAlias, new File(filepath), true, true);

            this.certPath = certprops.CERTS.certexportvalue + File.separator + this.hostAlias.toUpperCase() + ".cer";
            System.out.println(" Import Cert path is" + this.certPath);
        } catch (Exception e)

        {

            System.out.println("Exception thrown while importing Cert" + e.getMessage());

        }

    }

    public void setupAS2props(String productName, String productLocation, String host_range, String mbx_range, String action_range, String map_hostip, String map_port, String share_macro, String custom_HostProps, String custom_MbxProps, String custom_ActionProps, String create_certs) throws Exception {


        int total_hosts = 0;
        String[] vexHostPath = new String[3];

        try {
            as2util = new UtilTestProfile();
            as2util.setProduct(productName);
            as2util.setVexPath(productLocation);
            versalex = as2util.getVersalex();
            String[] hostVals = host_range.split("-");
            String[] mbxVals = mbx_range.split("-");
            // int total_hossts=as2util.getTotalHosts()+1;
            for (int i = Integer.parseInt(hostVals[0]); i <= Integer.parseInt(hostVals[1]); i++) {
                setHostPath(i);
                setDefaultHostProps(map_hostip, map_port, share_macro);
                //setCustomHostProps(custom_HostProps);
                as2util.setCustomProps("HOST", custom_HostProps, vexpath);
                if (Boolean.parseBoolean(create_certs)) {
                    // System.out.println("Into Cert Creation");
                    create_cert();
                } else {
                    // System.out.println("Into Cert Import");
                    import_cert();
                }
                for (int j = Integer.parseInt(mbxVals[0]); j <= Integer.parseInt(mbxVals[1]); j++) {
                    setMailboxPath(j);
                    setDefaultMailboxProps();
                    // setCustomMbxProps(custom_MbxProps);
                    as2util.setCustomProps("MAILBOX", custom_MbxProps, vexpath);
                    setup_MbxCertPath();
                    createActions(action_range);
                    //  setCustomActionProps(custom_ActionProps);
                    as2util.setCustomProps("ACTION", custom_ActionProps, vexpath);
                }
                versalex.save(vexpath[0]);
            }

            String[] local_listener_path = {"Local Listener", "AS2"};
            System.out.println("Current System Address" + InetAddress.getLocalHost().getHostAddress());
            versalex.setProperty(ILexiCom.SERVICE, local_listener_path, "Hostname", InetAddress.getLocalHost().getHostAddress());
            versalex.save(local_listener_path[0]);

            String[] LocalListPath = {"Local Listener"};
            versalex.setProperty(ILexiCom.HOST, LocalListPath, "localsigncertalias", this.hostAlias);
            versalex.setProperty(ILexiCom.HOST, LocalListPath, "localsigncertpassword", certprops.PASSPHRASE.certexportvalue);
            versalex.save(LocalListPath[0]);

        } catch (Exception e) {


            System.out.println("Exception thrown in setupAS2props" + e.getMessage());
        } finally {
            if (versalex != null) {

                versalex.close();
            }

        }


    }



    public void createActions(String actionRange ) throws Exception
    {

        try {
            String[] actionVals = actionRange.split("-");

            for (int k = Integer.parseInt(actionVals[0]); k <= Integer.parseInt(actionVals[1]); k++) {
                setActionPath(k);
                versalex.setProperty(ILexiCom.ACTION, vexpath, "Commands", "PUT -DEL *");

            }


        } catch (Exception e) {


            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }

    public void setHostPath(int host_num)
            throws Exception
    {

        try {
            this.hostNo = host_num;
            this.hostAlias = hostprops.ALIAS.hostvalue + this.hostNo;
            // System.out.println("Into setVexPath"+hostprops.DEFAULTALIAS.hostvalue);
            if (as2util.checkHostExists(this.hostAlias)) {

                vexpath[0] = this.hostAlias;
            } else {
                vexpath[0] = versalex.activateHost(hostprops.DEFAULTALIAS.hostvalue, hostAlias, true);
            }


        } catch (Exception e) {


            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }

    public void setMailboxPath(int mbx_num) throws Exception

    {

        try {

            mbxAlias = this.hostAlias + mbxprops.ALIAS.mbxvalue + mbx_num;

            for (String mailboxlist : versalex.list(ILexiCom.MAILBOX, vexpath)) {
                // System.out.println("Mailbox Name"+mailboxlist);
                vexpath[1] = mailboxlist;
                versalex.setProperty(ILexiCom.HOST, vexpath, hostprops.ADVANCED.hostkey, hostprops.ADVANCED.hostvalue);
                if (mailboxlist.toLowerCase().startsWith("mymailbox")) {
                    versalex.remove(ILexiCom.MAILBOX, vexpath);
                }
                vexpath[1] = versalex.create(ILexiCom.MAILBOX, vexpath, mbxAlias, true);

            }

        //    vexpath[2] = versalex.create(ILexiCom.ACTION, vexpath, actionAlias, true);


        } catch (Exception e) {


            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }

    public void setActionPath(int action_num) throws Exception

    {
        try {

            actionAlias = actionprops.ALIAS.actionvalue + action_num;
            vexpath[2] = versalex.create(ILexiCom.ACTION, vexpath, actionAlias, true);

        } catch (Exception e) {


            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }

    public void setVexPath() throws Exception {

        try {

            System.out.println("Into setVexPath" + hostprops.DEFAULTALIAS.hostvalue);
            vexpath[0] = versalex.activateHost(hostprops.DEFAULTALIAS.hostvalue, hostAlias, true);
            for (String mailboxlist : versalex.list(ILexiCom.MAILBOX, vexpath)) {
                System.out.println("Mailbox Name" + mailboxlist);
                vexpath[1] = mailboxlist;
                versalex.setProperty(ILexiCom.HOST, vexpath, hostprops.ADVANCED.hostkey, hostprops.ADVANCED.hostvalue);

                versalex.remove(ILexiCom.MAILBOX, vexpath);
                vexpath[1] = versalex.create(ILexiCom.MAILBOX, vexpath, mbxAlias, true);

            }

            vexpath[2] = versalex.create(ILexiCom.ACTION, vexpath, actionAlias, true);


        } catch (Exception e) {


            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }


    enum hostprops {

        PORT("Port", "5080"),
        ADVANCED("Advanced", "RESTEnabled=false"),
        DEFAULTALIAS("DefaultAlias", "Generic AS2"),
        ALIAS("hostalias", "AS2H");
        String hostkey;
        String hostvalue;

        hostprops(String name, String value) {
            this.hostkey = name;
            this.hostvalue = value;
        }

    }


    enum mbxprops {
        ALIAS("mbxalias", "_MBX"),
        USESAMECERTS("Usesamecerts", "true"),
        USESIGNCERTS("Usesigncert", "true"),
        USEENCCERTS("Useencrcert", "true"),
        OVERRIDLOCALLISTCERT("Overridelistenercerts", "true"),
        ENCRYPTEDREQUEST("Encryptedrequest", "true"),
        RECEIPTDESIRED("Receiptdesired", "true"),
        SIGNEDREQ("Signedrequest", "true"),
        ENCRYPTIONMETHOD("Encryptionmethod", "4");

        String mbxkey;
        String mbxvalue;

        mbxprops(String name, String value) {
            this.mbxkey = name;
            this.mbxvalue = value;
        }

    }


    enum actionprops {
        ALIAS("actionalias", "send");
        String actionkey;
        String actionvalue;

        actionprops(String name, String value) {
            this.actionkey = name;
            this.actionvalue = value;
        }

    }


    enum certprops {
        PASSPHRASE("PrivateKeyPwd", "cleo"),
        COUNTRYCODE("CountryCode", "IN"),
        EXPORTFOLDER("ExportFolder", "systestcerts"),
        CERTS("CertsFolderName", "certs");

        String certexportkey;
        String certexportvalue;

        certprops(String name, String value) {
            this.certexportkey = name;
            this.certexportvalue = value;
        }

    }


}


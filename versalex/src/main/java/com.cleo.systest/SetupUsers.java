package com.cleo.systest;


import com.cleo.lexicom.external.ILexiCom;
import com.cleo.security.hashing.VLPBKDF2Authenticator;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * Created by muthukumarc on 03-11-2016.
 */
public class SetupUsers {
    public int hostNo;
    public int mbxNo;
    public int actionNo;
    public String hostAlias;
    public String mbxAlias;
    public String actionAlias;
    public String certPath;
    ILexiCom versalex = null;
    String[] vexpath = new String[3];
    UtilTestProfile usersUtil;

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


    public void setDefaultServerMailboxProps() throws Exception {
        try {
            System.out.println("PwdHASH *******"+vexpath.toString());
            VLPBKDF2Authenticator authenticator = new VLPBKDF2Authenticator();
            String pwdHash = authenticator.createHash("", "cleo");
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "Pwdhash", pwdHash);
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "Homedirectory", this.mbxAlias);

        } catch (Exception e) {



            System.out.println("Exception in setDefaultMailboxProps" + e.getMessage());

        }

    }

    public void setDefaultMailboxProps() throws Exception {
        try {

            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "Username", this.mbxAlias);
            versalex.setProperty(ILexiCom.MAILBOX, vexpath, "Password", mbxprops.MBXPWD.mbxvalue);

        } catch (Exception e) {

            System.out.println("Exception in setDefaultMailboxProps" + e.getMessage());

        }

    }

    /**
     * Import Certificate for VersalEx Products .. This is for Receiver Side and Set Local Listener and Mailbox Level Properties for Certificates created
     */


    public void setLocalListener_SSHFtp_Props(String portVal) throws Exception {
        try {


            String[] local_listener_path = {"Local Listener"};
            System.out.println("Port Value passed"+portVal);
            versalex.setProperty(ILexiCom.HOST, local_listener_path, "Sshftpisselected", "true");
            versalex.setProperty(ILexiCom.HOST, local_listener_path, "Sshftpport",portVal );
            versalex.save(local_listener_path[0]);

        } catch (Exception e) {

            System.out.println("Exception in setLocalListener_SSHFtp_Props" + e.getMessage());

        }

    }


    public void setLocalListener_FTP_Props(String portVal) throws Exception {
        try {


            String[] local_listener_path = {"Local Listener"};
            versalex.setProperty(ILexiCom.HOST, local_listener_path, "Ftpisselected", "true");
            versalex.setProperty(ILexiCom.HOST, local_listener_path, "Ftpport",portVal );
            versalex.save(local_listener_path[0]);

        } catch (Exception e) {

            System.out.println("Exception in setLocalListener_FTP_Props" + e.getMessage());

        }

    }



    public void setupUserprops(String productName, String productLocation,  String protocol_Type,String share_macro, String custom_HostProps, String custom_MbxProps, String custom_ActionProps,int totalMboxes,String newAlias,String portVal,String single_Host) throws Exception {


                int total_hosts = 0;
                String[] vexHostPath = new String[3];
                int mbxNum=1;
                try {

                    usersUtil = new UtilTestProfile();
                    usersUtil.setProduct(productName);
                    usersUtil.setVexPath(productLocation);
                    versalex = usersUtil.getVersalex();
                    if(protocol_Type.toLowerCase().startsWith("sshftp"))
                    {
                        System.out.println("Into SSH FTP");
                        setLocalListener_SSHFtp_Props(portVal);

                    }
                    if(protocol_Type.toLowerCase().startsWith("ftp"))
                    {
                        System.out.println("Into FTP");
                        setLocalListener_FTP_Props(portVal);

                    }

                    // int total_hossts=as2util.getTotalHosts()+1;

                    for (int j = 1; j <=totalMboxes; j++ )
                    {
                        if(Boolean.parseBoolean(single_Host) ) {
                            if(j==1)
                            {
                                setHostPath(1, "Users", newAlias);
                            }
                            else
                            {

                                versalex.setProperty(ILexiCom.HOST, vexpath, "Defaulthomedir", share_macro + File.separator + protocol_Type.toLowerCase()+ "\\%username%");
                                usersUtil.setCustomProps("HOST", custom_HostProps, vexpath);
                                setMailboxServerPath(j);
                                setDefaultServerMailboxProps();
                                usersUtil.setCustomProps("MAILBOX", custom_MbxProps, vexpath);

                            }

                        }
                        else
                        {
                                setHostPath(j, "Users", newAlias);
                                versalex.setProperty(ILexiCom.HOST, vexpath, "Defaulthomedir", share_macro + File.separator + protocol_Type.toLowerCase()+ "\\%username%");
                                usersUtil.setCustomProps("HOST", custom_HostProps, vexpath);
                                setMailboxServerPath(1);
                                setDefaultServerMailboxProps();
                                usersUtil.setCustomProps("MAILBOX", custom_MbxProps, vexpath);
                        }

                            versalex.save(vexpath[0]);


                    }
                    System.out.println("Host Custom Props"+custom_HostProps);


        } catch (Exception e) {


            System.out.println("Exception thrown in setupUserprops" + e.getMessage());
        } finally {
            if (versalex != null) {

                versalex.close();
            }

        }


    }

    public void createActions(String actionRange) throws Exception

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

    public void setHostPath(int host_num, String default_alias,String newAlias) throws Exception

    {

        try {
            this.hostNo = host_num;
            this.hostAlias = newAlias + host_num;
            if (usersUtil.checkHostExists(this.hostAlias)) {
                vexpath[0] = this.hostAlias;
            } else {
                vexpath[0] = versalex.activateHost(default_alias, hostAlias, true);
            }
            versalex.setProperty(ILexiCom.HOST, vexpath, SetupUsers.hostprops.ADVANCED.hostkey, AS2Setup.hostprops.ADVANCED.hostvalue);


        } catch (Exception e) {


            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }

    public void setMailboxServerPath(int mbx_num) throws Exception

    {

        try {
            mbxAlias = this.hostAlias + "_MBX" + mbx_num;
            vexpath[1] = versalex.create(ILexiCom.MAILBOX, vexpath, mbxAlias, true);
        } catch (Exception e) {
            System.out.println("Exception thrown in setVexPath" + e.getMessage());
        } finally {


        }


    }


    public void setMailboxClientPath(int mbx_num) throws Exception

    {

        try {

            mbxAlias = this.hostAlias + mbxprops.ALIAS.mbxvalue + mbx_num;

            for (String mailboxlist : versalex.list(ILexiCom.MAILBOX, vexpath)) {
                // System.out.println("Mailbox Name"+mailboxlist);
                vexpath[1] = mailboxlist;
                if (mailboxlist.toLowerCase().startsWith("mymailbox")) {
                    versalex.remove(ILexiCom.MAILBOX, vexpath);
                }
                vexpath[1] = versalex.create(ILexiCom.MAILBOX, vexpath, mbxAlias, true);

            }

           // vexpath[2] = versalex.create(ILexiCom.ACTION, vexpath, actionAlias, true);


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

            //  System.out.println("Into setVexPath" + hostprops.DEFAULTALIAS.hostvalue);
            //  vexpath[0] = versalex.activateHost(hostprops.DEFAULTALIAS.hostvalue, hostAlias, true);
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
        DEFAULTSERVERALIAS("DefaultSrvAlias", "Users"),
        DEFAULTCLIENTALIAS("DefaultClientAlias", "Generic FTP"),
        ALIAS("hostalias", "FTPHost"),
        CLIENTHOSTDEFAULTALIAS("Clienthostalias", "Generic FTP");
        String hostkey;
        String hostvalue;

        hostprops(String name, String value) {
            this.hostkey = name;
            this.hostvalue = value;
        }

    }


    enum ftpprops {
        FTPSELECT("Ftpisselected", "true"),
        PORT("Ftpport", "5021");

        String ftpkey;
        String ftpvalue;

        ftpprops(String name, String value) {
            this.ftpkey = name;
            this.ftpvalue = value;
        }

    }


    enum mbxprops {
        ALIAS("mbxalias", "_MBX"),
        MBXPWD("mbxpwd", "cleo");

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


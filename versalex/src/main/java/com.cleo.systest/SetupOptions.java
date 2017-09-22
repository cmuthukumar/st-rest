package com.cleo.systest;

import com.cleo.lexicom.LexiCom;
import com.cleo.lexicom.SOCKSProxyDialog;
import com.cleo.lexicom.beans.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import com.cleo.lexicom.external.ILexiCom;
import com.cleo.lexicom.external.LexiComFactory;
import com.cleo.lexicom.external.LexiComOutgoingThread;
import com.cleo.lexicom.systemmetrics.data.SystemTransferRateData;
import com.cleo.lexicom.vlnavigator.ldap.*;
import com.cleo.lexicom.beans.Proxies.Proxy;
import com.cleo.lexicom.dns.SRVRecord;
import com.cleo.lexicom.vlnavigator.VLNavigator;

import com.cleo.lexicom.vlnavigator.Users;


import com.cleo.lexicom.beans.Options.DBConnection;

public class SetupOptions extends  LexiComOutgoingThread
{
    //static ILexiCom lex;
    //lex=LexiComFactory.getVersaLex(LexiComFactory.HARMONY, "C:\\Harmony", LexiComFactory.CLIENT_OR_SERVER);
    // ILexiCom lex=LexiComFactory.getVersaLex(LexiComFactory.HARMONY, "C:\\Harmony", LexiComFactory.CLIENT_OR_SERVER);

    public SetupOptions(int VersalEx_Product,String Install_location) throws Exception
    {
        //System.out.println("Super Method Called for LexHostBean and Passed Versale ExOBj");
        super(LexiComFactory.getVersaLex(VersalEx_Product, Install_location, LexiComFactory.CLIENT_ONLY));
        System.out.println("Super Method Called for LexHostBean and Passed Versale ExOBj");




    }

    public void setUpLdapServer(String ldap_type,String host,String port,String security_mode,String base_dn,String username,String emailaddress,String fullname,String homedir,String password,String search_filter,String usernameattrib) throws Exception{

        try{


            System.out.println("Setting UP LDAP Server"+ldap_type+"Host"+host);
            DocumentBuilderFactory docBuilderFactory=	DocumentBuilderFactory.newInstance();
            docBuilderFactory.setIgnoringElementContentWhitespace(true);
            File xmlFile;
            NodeList elementlist;
            Node currElem;
            NamedNodeMap attrbs;
            Integer record_idx=0;
            // LdapServer ldapServer =null;
            //    LdapServer ldapServer=null;
            //LexiComFactory.getLexiCom("C:\\Harmony", LexiComFactory.HARMONY);
            // LexiComFactory.getVersaLex(3,"C:\\Harmony", LexiComFactory.CLIENT_ONLY);
            //   LexHostBean lexhost=(LexHostBean)LexiComFactory.getVersaLex(3, "C:\\Harmony", LexiComFactory.CLIENT_ONLY);

            //LexHostBean.options.getDBConnection();s
            // DBConnection conn=(DBConnection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test_versalex","root","root");
            // DriverManager.getConnection("jdbc:mysql://localhost:3306/test_versalex","root","root");
            if(LexHostBean.getSystemInbox()!=null)
                System.out.println("Lexhost bean obj exists");

            //   LdapServer ldapServer = (LdapServer)LexHostBean.options.getLDAPServer();
            //    LexHostBean.options.setLDAPServer(LdapServer.ACTIVE_DIRECTORY);
            LdapServer ldapServer = (LdapServer)LexHostBean.options.getLDAPServer();
            if(ldapServer==null)
            {
                System.out.println("LDAPSesrver is Null ...So Enabling LDAP in VLNAvigator Users Level");
                LdapServer server = (LdapServer)VLNavigator.users.getLDAPServer();

                server.setEnabled(true);
                LexHostBean.options.setLDAPServer(server);
                ldapServer=(LdapServer)LexHostBean.options.getLDAPServer();
            }
            else
            {

                SRVRecord[] recordchk = ldapServer.getSRVRecords();
                System.out.println("Records Length is " + recordchk.length);
                record_idx=recordchk.length;
            }
            System.out.println("Writing First record and Record Index is "+record_idx);
            System.out.println("Args... Passsed By user is ldap_type"+ldap_type+security_mode+"base_dn"+base_dn+"Username"+username+"Password"+password+"emailaddress"+emailaddress+"fullname"+fullname+"search_filter"+search_filter+"homedir"+homedir);

            ldapServer.setType(ldap_type);
            ldapServer.setSecurity(security_mode);
            ldapServer.setDomain(base_dn);
            ldapServer.setFilter(search_filter);
            ldapServer.setLDAPUsername(username);
            ldapServer.setLDAPPassword(password);
            ldapServer.setEmailAddressAttribute(emailaddress);
            ldapServer.setFullNameAttribute(fullname);
            ldapServer.setHomeDirAttribute(homedir);
            ldapServer.setAttribute(usernameattrib);

            SRVRecord record = new SRVRecord();
            record.setHost(host);
            record.setEnabled(true);
            record.setPort(Integer.parseInt(port));
            record.setTTL(600L);
            record.setPriority(0);
            record.setWeight(0);
            ldapServer.setSRVRecords(record_idx,record);
            LexHostBean.options.setLDAPServer(ldapServer);
            LexHostBean.options.save();
            System.out.println("Saved LDAP Server");
        }
        catch(Exception e)
        {


            System.out.println("Exception thrown while Setting up LDAP server is "+e.getMessage());
            e.printStackTrace();
        }

    }

    public void setupDBConnection(String jdbc_url,String driverstring,String dbusername,String dbpassword,int trasnfer_log,String transferLoggingDBConnectionStr){
        try{
            Integer dbrecord_idx=0;
            System.out.println("Setting up DB Connections 1.JDBC Url::"+jdbc_url+"2.UserName::"+dbusername+"3.Password::"+dbpassword+"Transfer Log::"+trasnfer_log+"transferLoggingDBConnectionStr::"+transferLoggingDBConnectionStr);
          //  Options.DBConnection[]  cons=  new Options.DBConnection[1];
            Options.DBConnection[]  connchk= LexHostBean.options.getDBConnection();

            if(connchk.length >0 )

            {

                dbrecord_idx=connchk.length;

            }

            System.out.println("DB Index Number is"+dbrecord_idx);
            Options.DBConnection connn = LexHostBean.options.newDBConnection();
            connn.setConnectionString(jdbc_url);
            connn.setDriverString(driverstring);
            connn.setConnectionType("Other");
            connn.setUserName(dbusername);
            connn.setPassword(dbpassword);
           // cons[0]=connn;

            LexHostBean.options.setDBConnection(dbrecord_idx,connn);

            if(trasnfer_log==1)
            {
                LexHostBean.options.setTransferLogging(Options.TRANSFER_LOG_DATABASE);
                LexHostBean.options.setTransferLoggingDBConnectionStr(transferLoggingDBConnectionStr);
            }

            LexHostBean.options.save();
            System.out.println("DB Connection Saved");

        }
        catch(Exception e)
        {

            System.out.println("Exception thrown while setting up DB Server "+e.getMessage());

        }

    }


    public void setupProxy(String proxy_type,String host_address,int proxy_port,String revProxy_flag ){
        try{

            boolean revFwdEnabled = LexHostBean.proxies.isReverseForwardConnectionsEnabled();
            System.out.println("Setting up Proxy Connections" );
          //  Proxies.Proxy proxy = LexHostBean.proxies.getActiveVLProxy();
            Proxy p1=LexHostBean.proxies.newProxy();
            p1.setType(proxy_type);
            p1.setEnableReverseProxying(revProxy_flag);
            p1.setAddress(host_address);
            p1.setPort(proxy_port);
            p1.setBackupProxy("false");
            p1.setLoadBalance("false");
            p1.setReverseForwardConnections("false");
            p1.setUseListenerSSHCerts("true");
            p1.setUseListenerSSLCerts("true");
            p1.setUseSameCerts("true");
            p1.setUsingVLProxy("true");
            LexHostBean.proxies.setProxy(0,p1);
            LexHostBean.proxies.writeXML(OpAuditTrail.ADDED, false /*sync*/);
            LexHostBean.options.save();
            System.out.println("PROXY Settings Saved");
            Proxies.Proxy[] proxies = LexHostBean.proxies.getVLProxyProxies();
            System.out.println("Total Proxies Configured:+"+proxies.length);

        }
        catch(Exception e)
        {

            System.out.println("Exception thrown while setting up DB Server "+e.getMessage());

        }

    }



    public void setupShares(String ShareVals){
        try{
            System.out.println("Setting up Shares::"+ShareVals);
            Options shares=  LexHostBean.options;
            shares.setCustomVars(0,ShareVals);
            LexHostBean.options.save();
            System.out.println("Setup Shares Saved");

        }
        catch(Exception e)
        {

            System.out.println("Exception thrown while setting up setupShares "+e.getMessage());

        }

    }




    public static void main(String args[]) throws Exception
    {

        try{
            Class lex = LexiCom.class;
            System.out.println(lex);
            System.out.println("Into Main");
            System.out.println("Args Passed by User is "+"VersalEx Product::"+args[0]+"Install_Location"+args[1]);

            int versalex_product=0;
            //System.getProperties().list(System.out);
            if(args[0].equalsIgnoreCase("Harmony"))
            {
                versalex_product=3;

            }
            if(args[0].equalsIgnoreCase("VLTrader"))
            {
                versalex_product=2;

            }
            if(args[0].equalsIgnoreCase("Lexicom"))
            {
                //This is for LexiCom
                versalex_product=1;

            }
            System.out.println("Passing Aargs to Const 1.VersalEx Prod::"+versalex_product+"2.Install_Loc::"+args[1]+"3.LDAP/DB/PROXY::"+args[2]);
            SetupOptions callLdap=new  SetupOptions(versalex_product,args[1]);

            if(args[2].equalsIgnoreCase("LDAP"))
            {

                System.out.println("Into LDAP");
                //System.out.println("Args::args[2]"+args[2]+"args[3]"+args[3]+"args[4]"+args[4]+"args[5]"+args[5]);
                callLdap.setUpLdapServer(args[3],args[4],args[5],args[6],args[7],args[8],args[9],args[10],args[11],args[12],args[13],args[14]);
                //callLdap.setUpLdapServer("Active Directory","10.80.80.209","390","SSL","Overnight,DC=Engineering,DC=localxx","rootxx","mailxx","displayNamexx","homeDirectoryxx","cleoxx","department=QAxx");
            }

            if(args[2].equalsIgnoreCase("DB"))
            {
                System.out.println("Into DB");
                //callLdap.setupDBConnection("jdbc:mysql://localhost:5089/test_versalex","com.mysql.jdbc.Driver","muthu","muthu",1,"jdbc:mysql://localhost:5089/test_versalex");
                callLdap.setupDBConnection(args[3],args[4],args[5],args[6],Integer.parseInt(args[7]),args[8]);


            }

            if(args[2].equalsIgnoreCase("PROXY"))
            {
                callLdap.setupProxy(args[3],args[4],Integer.parseInt(args[5]),args[6]);

            }

            if(args[2].equalsIgnoreCase("SHARE"))
            {
                System.out.println("Setup Shares...Values passed is"+args[3]);
                callLdap.setupShares(args[3]);

            }



        }
        catch(Exception e)
        {

            System.out.println("Exception thrown"+e.getMessage());
            e.printStackTrace();

        }
        finally{

            System.out.println("Finally Block Exiting");

             System.exit(0);

        }

    }

    public void run() {
        // TODO Auto-generated method stub



    }



}

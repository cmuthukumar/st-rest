package com.cleo.systest;

import com.cleo.lexicom.LexiCom;
import com.cleo.lexicom.Sync;
//import com.cleo.lexicom.Sync.Versalex;
import com.cleo.lexicom.LexRemote;
//import com.cleo.lexicom.Syncer;
//import com.cleo.lexicom.beans.LexBean;
import com.cleo.lexicom.external.ILexiCom;
//import com.cleo.lexicom.external.ISyncer;
import com.cleo.lexicom.external.LexiComFactory;

public class SetupSync {

    public static void main(String[] args) throws Exception {
        try {

            ILexiCom lex = LexiComFactory.getVersaLex(LexiComFactory.HARMONY, args[0], LexiComFactory.CLIENT_OR_SERVER);

            synchronized (lex.getSyncer()) {
                Sync.Versalex vex = LexiCom.sync.newVersalex(false);
                vex.isetSerialNumber(args[1]);
                vex.setAddress(args[2]);
                vex.setPort(args[3]);
                vex.setSyncpath("certs/*;data/*;conf/Options.xml;conf/Proxies.xml;conf/AS400.xml;conf/WinUnixFolders.xml;conf/Schedule.xml;hosts/Local Listener.xml;hosts/*.xml;conf/TradingPartners.xml;conf/ipbl.xml;importedIDPs/*.xml");
                vex.isetbSecure(false);
                vex.setBackup(false);
                vex.isetbNotInitialized(false);
                String st = vex.getId();
                System.out.println("Serial No" + st);
                //   boolean valid = LexiCom.syncer.requestSyncConnection("administrator", "Admin", vex);
                LexiCom.sync.setVersalex(-1, vex);

                LexiCom.sync.writeXML();
                LexiCom.commandline.lexRemote.syncRequest(LexRemote.SYNC_ALL_CONFIGURED, vex.igetSerialNumber(), null, null, false);
                LexiCom.commandline.lexRemote.syncRequest(LexRemote.SYNC_RESET_AND_UPDATE, vex.igetSerialNumber(), null, null, false);

            }

            System.exit(-1);

        } catch (Exception e) {

            System.out.println("Exception while setting Syncing is" + e.getMessage());

        }


    }


}

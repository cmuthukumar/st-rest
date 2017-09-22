package com.cleo.systest;

import com.cleo.lexicom.external.ILexiCom;
import com.cleo.lexicom.external.ISchedule;
import com.cleo.lexicom.external.LexiComFactory;

import java.util.Arrays;

public class SetupSchedule {

    public static void main(String[] args) throws Exception {
        try{

             String[] vexHostPath = new String[3];
            ILexiCom versalex = null;


            System.out.println("Into Schedule");
            versalex = LexiComFactory.getVersaLex(3, "C:\\Harmony", LexiComFactory.CLIENT_OR_SERVER);
            versalex.startService();
            ISchedule schedule = versalex.getSchedule();
            schedule.setAutoStartup(true);
            ISchedule.Item item;
            ISchedule.Item.Calendar calendar;
            ISchedule.Item.Calendar.Time time;
            for (String hostelem : versalex.list(ILexiCom.HOST, vexHostPath)) {

                // System.out.println("Returning True");
                System.out.println("Host Elem is" + hostelem);
                vexHostPath[0] = "AS2H1";

                for (String mbxelem : versalex.list(ILexiCom.MAILBOX, vexHostPath)) {
                    vexHostPath[1] = "AS2H1_MBX1";
                    System.out.println("Mailbox Elem is" + mbxelem);
                    for (String actionelem : versalex.list(ILexiCom.ACTION, vexHostPath)) {
                        vexHostPath[2] = "send1";
                        System.out.println("Action Elem is" + actionelem);
                        System.out.println("Setting Scheduler for Path" + Arrays.toString(vexHostPath));

                        item = schedule.newItem(vexHostPath);
                         schedule.saveFromService("true");
                        //item.setOnlyIfFile(Boolean.parseBoolean(vexHostProps.get("schedule_onlyiffile").toLowerCase()), Boolean.parseBoolean(vexHostProps.get("schedule_continuous").toLowerCase()));
                        item.setOnlyIfFile(true, true);
                        schedule.updateItem(item, false);
                        schedule.save();
                        System.out.println("Scheduling done");


                    }
                }


            }

            }
        catch(Exception e)
        {

            System.out.println("Setting up Scheduler failed with exception"+e.getMessage());

        }
        finally{

            System.exit(0);

        }

    }



}

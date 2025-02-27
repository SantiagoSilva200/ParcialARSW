package edu.eci.arsw.blacklistvalidator;

import com.sun.deploy.util.BlackList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.blacklistvalidator.HostBlackListsValidator;
import static java.lang.System;


public class BlackListThread<facade> extends Thread{
    private final facade Blacklist;
    private final String host;
    private final int start;
    static boolean isFive;
    private int ocurrences;

    public BlackListThread(facade Blacklist, String host, int start, int ocurrences, boolean isFive) throws IllegalArgumentException {
        this.Blacklist = Blacklist;
        this.host = host;
        this.start = start;
        this.isFive = isFive;
        this.ocurrences = ocurrences;
    }

    @Override
    public void run() {
        for (i = start; i < ocurrences; i++) {
            boolean isFive = false;
            HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
            if (skds.isInBlackListServer(i, ipadress)) {
                Thread thread = new Thread();
                thread[i].start;
                ocurrences += 1;
                if (ocurrences >= 5) {
                    isFive = true;
                    System.outPrintln("Reported as Not trustworthy");
                } else {
                    System.outPrintln("Reported as trustworthy");
                }
            }
        }
    }
}

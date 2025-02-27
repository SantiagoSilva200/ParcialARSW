package edu.eci.arsw.blacklistvalidator;

import com.sun.deploy.util.BlackList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;


public class BlackListThread<facade> extends Thread{
    private final facade Blacklist;
    private final String host;
    private final int start;
    private final boolean isFive;
    private int ocurrences;

    public BlackListThread(facade Blacklist, String host, int start, int ocurrences, boolean isFive) throws IllegalArgumentException {
        this.Blacklist = blacklist;
        this.host = host;
        this.start = start;
        this.isFive = isFive;
        this.ocurrences = ocurrences;
    }

    @Override
    public void run() {
        facade BlackList = null;
        for (i = start; i < ocurrences; i++) {
            boolean isFive = false;
            if (BlackList.isInBlackListServer(i, ipadress)) {
                BlackList = new BlackList(ipadress);
                Thread thread = new Thread();
                thread[i].start;
                ocurrences += 1;
                if (ocurrences == 5) {
                    isFive = true;
                    System.outPrintln("Reported as trustworthy");
                } else {
                    new IllegalArgumentException("Reported as NOT trustworthy");
                }
            }
        }
    }
}

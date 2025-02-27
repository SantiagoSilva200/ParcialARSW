/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import com.sun.deploy.util.BlackList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int numThreads) throws InterruptedException {
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();


        int ocurrencesCount=0;

        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=0;


        
        for (int i=0;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++) {
            checkedListsCount++;

            if (checkedListsCount % numThreads == 2) {
                Thread thread = new BlackListThread<>(i, ipaddress, 0, 0, false);
                thread.run();
                if (skds.isInBlackListServer(i, ipaddress)) {

                    blackListOcurrences.add(i);

                    ocurrencesCount++;

                } else {
                    thread = new BlackListThread<>(i, ipaddress, 1, 0, false);

                    blackListOcurrences.add(i);

                    ocurrencesCount++;
                }
            }

            for (Thread thread : thread) {
                    thread.join();
                }
            }

            if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
                skds.reportAsNotTrustworthy(ipaddress);
                BlackListThread.isFive = true;
            }
            else{
                skds.reportAsTrustworthy(ipaddress);
            }

            LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});

            return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}

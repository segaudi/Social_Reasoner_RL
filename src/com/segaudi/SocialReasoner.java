package com.segaudi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchenyou on 12/22/16.
 */
public class SocialReasoner {
    public static void main(String[] args) throws Exception{
        System.out.println("Social Reasoner Running ");
        Config config = new Config();
        System.out.println(config.cache_path);
        RapportDB rapportDB = new RapportDB();
        rapportDB.initRapportDB();
        convDyad dyad1 = rapportDB.getDyad(0);
        System.out.println(dyad1.size());
        convSession session1 = dyad1.getSession(0);
        System.out.println(session1.sizes());
        List<convEntry> mergedList = session1.getMergedList();
        for (int i=0; i<mergedList.size();i++){
            System.out.println(mergedList.get(i).getStrategyName() + " Spoken by Person" + mergedList.get(i).getPerson());
        }
    }
}


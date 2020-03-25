package Proxy;

import java.util.Map;
import java.util.Set;

public class DataCache {

    private static DataCache instance;

    private Set<String> motherSideFemales;
    private Set<String> motherSideMales;
    private Set<String> fatherSideFemales;
    private Set<String> fatherSideMales;
    Map<String,String> allPersons;

    private String authToken;

    public static DataCache getInstance() {
        //there can only be one instance of datacache at a time
        if(instance == null) {
            instance = new DataCache();
        }

        return instance;
    }

    private DataCache() {

    }
    //logout clears all data
}
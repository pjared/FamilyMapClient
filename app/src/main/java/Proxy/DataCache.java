package Proxy;

import android.app.Person;

import java.util.Map;
import java.util.Set;

public class DataCache {

    private static DataCache instance;

    private Set<String> motherSideFemales;
    private Set<String> motherSideMales;
    private Set<String> fatherSideFemales;
    private Set<String> fatherSideMales;

    Map<String, Person> allPersons;

    private String authToken;

    private String serverHost;
    private String userPort;

    String fullUserName;

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
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getUserPort() {
        return userPort;
    }

    public void setUserPort(String userPort) {
        this.userPort = userPort;
    }

    public String getFullUserName() {
        return fullUserName;
    }

    public void setFullUserName(String fullUserName) {
        this.fullUserName = fullUserName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


}
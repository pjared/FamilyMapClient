package Proxy;

import android.os.Looper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import Model.Event;
import Model.Person;

public class DataCache {

    private static DataCache instance;

    private Set<String> motherSideFemales;
    private Set<String> motherSideMales;
    private Set<String> fatherSideFemales;
    private Set<String> fatherSideMales;

    //Map<String, Person> allPersons;
    //Map<String, Event> allEvents;
    ArrayList<Person> allPersons;
    ArrayList<Event> allEvent;

    private String authToken;

    private String personID;

    private String serverHost;
    private String userPort;


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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /*public String findPerson(String personID) {
        for(Map.Entry<String, Person> entry: allPersons.entrySet()) {
            if (entry.getKey() == personID) {
                String personName = entry.getValue().getFirstName() + " "
                                    + entry.getValue().getLastName();
                return personName;
            }
        }
        return "";
    } */

    public String findPerson(String personID) {
        String personName = "";
        for(Person person: allPersons) {
            if(personID.equals(person.getPersonID())) {
                personName = person.getFirstName() + " " + person.getLastName();
            }
        }
        return personName;
    }

    public void setAllPersons(ArrayList<Person> allPersons) {
        this.allPersons = allPersons;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public ArrayList<Event> getAllEvent() {
        return allEvent;
    }

    public void setAllEvent(ArrayList<Event> allEvent) {
        this.allEvent = allEvent;
    }
}
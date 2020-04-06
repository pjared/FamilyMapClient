package Proxy;

import android.os.Looper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.FactoryConfigurationError;

import Model.Event;
import Model.Person;

public class DataCache {

    private static DataCache instance;

    private Set<Person> motherSideFemales;
    private Set<Person> motherSideMales;
    private Set<Person> fatherSideFemales;
    private Set<Person> fatherSideMales;

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

    private DataCache() {}

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

    public Person findPerson(String personID) {
        for(Person person: allPersons) {
            if(personID.equals(person.getPersonID())) {
                return person;
            }
        }
        return null;
    }

    public void setAllPersons(ArrayList<Person> allPersons) {
        this.allPersons = allPersons;
        sortPersons();
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

    private void sortPersons() {
        fatherSideFemales = new HashSet<>();
        fatherSideMales = new HashSet<>();
        motherSideFemales = new HashSet<>();
        motherSideMales = new HashSet<>();
        //How am I going to sort people?
        //First by getting the base users father.
        Person userPerson = findPerson(personID);
        fatherSideMales.add(findPerson(userPerson.getFatherID()));
        parseFatherSide(userPerson.getFatherID());
        motherSideFemales.add(findPerson(userPerson.getMotherID()));
        parseMotherSide(userPerson.getFatherID());
    }

    private void parseFatherSide(String parentID) {
        Person currentPerson = findPerson(parentID);
        if(currentPerson.getMotherID() == null) {
            return;
        }

        Person mother = findPerson(currentPerson.getMotherID());
        Person father = findPerson(currentPerson.getFatherID());

        if(mother != null) { //If mother is null, father will also be
            fatherSideMales.add(father);
            fatherSideFemales.add(mother);
            parseFatherSide(mother.getPersonID());
            parseFatherSide(father.getPersonID());
        }
    }

    private void parseMotherSide(String parentID) {
        Person currentPerson = findPerson(parentID);
        Person mother = findPerson(currentPerson.getMotherID());
        Person father = findPerson(currentPerson.getFatherID());

        if(mother != null) { //If mother is null, father will also be
            motherSideMales.add(father);
            motherSideFemales.add(mother);
            parseFatherSide(mother.getPersonID());
            parseFatherSide(father.getPersonID());
        }
    }
}
package Proxy;

import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.FactoryConfigurationError;

import Model.Event;
import Model.Person;

public class DataCache {
    private static DataCache instance;

    private DataCache() {}

    public static DataCache getInstance() {
        //there can only be one instance of datacache at a time
        if(instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    //------------------------This is the Settings part-------------------------------
    private boolean femaleSwitch = true;
    private boolean maleSwitch = true;
    private boolean motherSwitch = true;
    private boolean fatherSwitch = true;
    private boolean spouseSwitch = true;
    private boolean familyTreeSwitch = true;
    private boolean lifeStorySwitch = true;
    private boolean settingsChange = false;

    public ArrayList<Person> getDisplayPeople() {
        ArrayList<Person> displayPeople = new ArrayList<>();

        String personGender = findPerson(personID).getGender();
        if(femaleSwitch && personGender.equals("f")) {
            displayPeople.add(findPerson(personID));
        }
        if(maleSwitch && personGender.equals("m")) {
            displayPeople.add(findPerson(personID));
        }
        if (motherSwitch) {
            if(femaleSwitch) {
                displayPeople.addAll(motherSideFemales);
            }
            if (maleSwitch) {
                displayPeople.addAll(motherSideMales);
            }
        }
        if (fatherSwitch) {
            if(femaleSwitch) {
                displayPeople.addAll(fatherSideFemales);
            }
            if (maleSwitch) {
                displayPeople.addAll(fatherSideMales);
            }
        }
        if(!femaleSwitch && !maleSwitch) {
            //display should be clear
            displayPeople.clear();
        }

        return displayPeople;
    }

    public boolean isSettingsChange() {
        return settingsChange;
    }

    public void setSettingsChange(boolean settingsChange) {
        this.settingsChange = settingsChange;
    }


    public boolean isFemaleSwitch() {
        return femaleSwitch;
    }

    public void setFemaleSwitch(boolean femaleSwitch) {
        this.femaleSwitch = femaleSwitch;
    }

    public boolean isMaleSwitch() {
        return maleSwitch;
    }

    public void setMaleSwitch(boolean maleSwitch) {
        this.maleSwitch = maleSwitch;
    }

    public boolean isMotherSwitch() {
        return motherSwitch;
    }

    public void setMotherSwitch(boolean motherSwitch) {
        this.motherSwitch = motherSwitch;
    }

    public boolean isFatherSwitch() {
        return fatherSwitch;
    }

    public void setFatherSwitch(boolean fatherSwitch) {
        this.fatherSwitch = fatherSwitch;
    }

    public boolean isSpouseSwitch() {
        return spouseSwitch;
    }

    public void setSpouseSwitch(boolean spouseSwitch) {
        this.spouseSwitch = spouseSwitch;
    }

    public boolean isFamilyTreeSwitch() {
        return familyTreeSwitch;
    }

    public void setFamilyTreeSwitch(boolean familyTreeSwitch) {
        this.familyTreeSwitch = familyTreeSwitch;
    }

    public boolean isLifeStorySwitch() {
        return lifeStorySwitch;
    }

    public void setLifeStorySwitch(boolean lifeStorySwitch) {
        this.lifeStorySwitch = lifeStorySwitch;
    }

    //-------------------This is the map stuff------------------------------------------

    private Set<Person> motherSideFemales;
    private Set<Person> motherSideMales;
    private Set<Person> fatherSideFemales;
    private Set<Person> fatherSideMales;

    private ArrayList<Person> allPersons;
    private ArrayList<Event> allEvent;
    private Map<String, ArrayList<String>> mappedEvents;
    private Person activityPerson = null;

    public void setAllPersons(ArrayList<Person> allPersons) {
        this.allPersons = allPersons;
        sortPersons();
    }

    public ArrayList<Person> getAllPersons() {
        return allPersons;
    }

    ArrayList<Event> displayEvents;
    public ArrayList<Event> getAllEvent(){
        displayEvents = new ArrayList<>();
        ArrayList<Person> displayPeople = getDisplayPeople();

        /*for(Event event:allEvent) {
            if (displayPersonCheck(event.getPersonID())) {
                displayEvents.add(event);
            }
        } */ //This seems to for sure work if other doesn't

        for(Person person : displayPeople) {
            ArrayList<String> events = mappedEvents.get(person.getPersonID());
            for(String event:events) {
                displayEvents.add(findEvent(event));
            }
        }

        return displayEvents;
    }

    private boolean displayPersonCheck(String personID) {
        for(Person person: getDisplayPeople()) {
            if(personID.equals(person.getPersonID())) {
                return true;
            }
        }
        return false;
    }

    public boolean findCurrentEvent(String eventID) {
        for(Event event:displayEvents) {
            if (event.getEventID().equals(eventID)) {
                return true;
            }
        }
        return false;
    }

    public void setAllEvent(ArrayList<Event> allEvent) {
        this.allEvent = allEvent;
        mappedEvents = new HashMap<>();
        for (Event event: allEvent) {
            if(!mappedEvents.containsKey(event.getPersonID())) {
                mappedEvents.put(event.getPersonID(),new ArrayList<String>());
                mappedEvents.get(event.getPersonID()).add(event.getEventID());
            } else {
                mappedEvents.get(event.getPersonID()).add(event.getEventID());
            }
        }
    }

    public Event getFirstSpouseEvent(String spouseID) {
        Event firstEvent = null;
        ArrayList<String> spouseEvents = mappedEvents.get(spouseID);

        return getFirstEvent(spouseEvents);
    }

    public ArrayList<Event> getOrderedEvents(String markerPersonID) {
        ArrayList<String> personEvents = mappedEvents.get(markerPersonID);
        if(personEvents == null || personEvents.size() == 1) {
            return null;
        }

        ArrayList<Event> userEvents = new ArrayList<>();
        ArrayList<Event> orderedEvents = new ArrayList<>();

        for(String eventID:personEvents) {
            Event currentEvent = findEvent(eventID);
            if(currentEvent.getEventID().toLowerCase().equals("birth")) {
                orderedEvents.add(currentEvent);
            } else {
                userEvents.add(currentEvent);
            }
        }

        while(userEvents.size() != 0) {
            Event lowestEvent = null;
            for(Event event:userEvents) {
                if(lowestEvent == null) {
                    lowestEvent = event;
                    continue;
                }
                if(event.getYear() < lowestEvent.getYear()) {
                    lowestEvent = event;
                }
            }
            orderedEvents.add(lowestEvent);
            userEvents.remove(lowestEvent);
        }
        return orderedEvents;
    }

    public Event getFirstPersonEvent(String personID) {
        if(!mappedEvents.containsKey(personID)) {
            return null;
        }
        ArrayList<String> personEvents = mappedEvents.get(personID);
        return getFirstEvent(personEvents);
    }

    public Event getFirstEvent(ArrayList<String> eventIDs) {
        Event firstEvent = null;
        for(String eventID:eventIDs) {
            Event currentEvent = findEvent(eventID);
            if(firstEvent == null) {
                firstEvent = currentEvent;
            }
            if(currentEvent.getEventType().toLowerCase().equals("birth")) {
                return currentEvent;
            }

            if (currentEvent.getYear() < firstEvent.getYear()) {
                firstEvent = currentEvent;
            }
        }
        return firstEvent;
    }

    public Person findPerson(String personID) {
        for(Person person: allPersons) {
            if(personID.equals(person.getPersonID())) {
                return person;
            }
        }
        return null;
    }

    public Event findEvent(String eventID) {
        for(Event event: allEvent) {
            if(eventID.equals(event.getEventID())) {
                return event;
            }
        }
        return null;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean isServerSuccess() {
        return serverSuccess;
    }

    public void setServerSuccess(boolean serverSuccess) {
        this.serverSuccess = serverSuccess;
    }

    public Person getActivityPerson() {
        return activityPerson;
    }

    public void setActivityPerson(Person activityPerson) {
        this.activityPerson = activityPerson;
    }

    //-------------------Sorting for the map---------------------------------------

    private void sortPersons() {
        fatherSideFemales = new HashSet<>();
        fatherSideMales = new HashSet<>();
        motherSideFemales = new HashSet<>();
        motherSideMales = new HashSet<>();

        Person userPerson = findPerson(personID);
        fatherSideMales.add(findPerson(userPerson.getFatherID()));
        parseFatherSide(userPerson.getFatherID());
        motherSideFemales.add(findPerson(userPerson.getMotherID()));
        parseMotherSide(userPerson.getMotherID());


    }

    private void parseFatherSide(String parentID) {
        Person currentPerson = findPerson(parentID);
        if(currentPerson.getMotherID() == null && currentPerson.getFatherID() == null) {
            return;
        }
        Person mother = findPerson(currentPerson.getMotherID());
        Person father = findPerson(currentPerson.getFatherID());

        if(father != null) {
            fatherSideMales.add(father);
            parseFatherSide(father.getPersonID());
        }
        if(mother != null) {
            fatherSideFemales.add(mother);
            parseFatherSide(mother.getPersonID());
        }
    }

    private void parseMotherSide(String parentID) {
        Person currentPerson = findPerson(parentID);
        if(currentPerson.getMotherID() == null && currentPerson.getFatherID() == null) {
            return;
        }
        Person mother = findPerson(currentPerson.getMotherID());
        Person father = findPerson(currentPerson.getFatherID());

        if(mother != null) {
            motherSideFemales.add(mother);
            parseMotherSide(mother.getPersonID());
        }
        if(father != null) {
            motherSideMales.add(father);
            parseMotherSide(father.getPersonID());
        }
    }

    //-------------------------This is the login stuff---------------------------------------

    private String authToken;
    private String personID;
    private String serverHost;
    private String userPort;
    private boolean serverSuccess;


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
}
package com.example.familymap;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import Model.Event;
import Model.Person;
import Proxy.DataCache;
import Proxy.LoginTask;

import static org.junit.Assert.assertEquals;

public class EventFilterTest {
    private ArrayList<Person> allPerson;
    private ArrayList<Event> allEvent;

    @Before
    public void setUp() {
        //add some people
        allPerson = new ArrayList<>();
        allEvent = new ArrayList<>();
        //Put this in a seperate call at the bottom of the file so I don't have to scroll past it.
        addInfo();

        //How do I test without being able to use login task?
        //LoginTask loginTask = new LoginTask( "xXFazeXx","123");

        //might just be able to call a login task
    }

    @Test
    public void eventFilterTest() throws Exception {
        DataCache dCache = DataCache.getInstance();
        //add those people to be sorted.
        dCache.setPersonID("f004e7fc-3143-46a1-9637-d6ff5f9ed5c2");
        dCache.setAllPersons(allPerson);
        dCache.setAllEvent(allEvent);

        //user mother father
        assertEquals(allEvent, dCache.getAllEvent());
    }

    private void addInfo() {
        allPerson.add(new Person("sheila","f004e7fc-3143-46a1-9637-d6ff5f9ed5c2"
                , "Sheila","Parker","f"
                ,"99f37bae-0c0a-450a-be33-4744af7a32f6"
                ,"bee7753c-0ab4-437c-bde4-f8cede3e0e5a", ""));

        //mother
        allPerson.add(new Person("sheila","bee7753c-0ab4-437c-bde4-f8cede3e0e5a"
                , "Leann","Woosley","f"
                ,"ceae3dc2-1f43-4fb0-8089-6d643768271b"
                ,"f9b4b892-163d-46a7-8d16-e6346b20d4ea"
                , "99f37bae-0c0a-450a-be33-4744af7a32f6"));

        //father
        allPerson.add(new Person("sheila","99f37bae-0c0a-450a-be33-4744af7a32f6"
                , "Mariela","Parker","m"
                ,"21a72456-3f00-4d23-8482-84c60ba4498e"
                ,"17c3de0e-740f-4f23-ae14-80aa1b2b01c0"
                , "bee7753c-0ab4-437c-bde4-f8cede3e0e5a"));

        //String eventID, String associatedUser, String personID, float latitude, float longitude, String country, String city, String eventType, int year
        //Mother birth
        //child birth
        allEvent.add(new Event("b573338d-9656-4ce5-9787-2d24588a5839"
                ,"sheila"
                ,"f004e7fc-3143-46a1-9637-d6ff5f9ed5c2"
                ,(float)18.1000003814697
                ,(float)-14.0500001907349
                ,"Mauritania"
                ,"Nouakchott"
                ,"birth"
                ,1142));

        allEvent.add(new Event("45f7cca1-4ebb-4929-b05b-60a46ea99b48"
                ,"sheila"
                ,"bee7753c-0ab4-437c-bde4-f8cede3e0e5a"
                ,(float)23.7000007629395
                ,(float)90.38330078125
                ,"Bangladesh"
                ,"Dhaka"
                ,"birth"
                ,1232));
        //father birth
        //
        allEvent.add(new Event("54b2e7ab-2d2d-4b05-9c22-8152dfb6eb7f"
                ,"sheila"
                ,"99f37bae-0c0a-450a-be33-4744af7a32f6"
                ,(float)-14.5832996368408
                ,(float)28.2833003997803
                ,"Zambia"
                ,"Lusaka"
                ,"marriage"
                ,1202));

    }
}

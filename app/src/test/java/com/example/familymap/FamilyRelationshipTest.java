package com.example.familymap;

import org.junit.*;

import java.util.ArrayList;

import Model.Person;
import Proxy.DataCache;

import static org.junit.Assert.assertEquals;

public class FamilyRelationshipTest {
    private ArrayList<Person> allPerson;

    @Before
    public void setUp() {
        allPerson = new ArrayList<>();
        //adding first user, mother, and a father
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
    }

    @Test
    public void relationshipPass() throws Exception {
        DataCache dCache = DataCache.getInstance();
        //have to load some people into here.
        dCache.setPersonID("f004e7fc-3143-46a1-9637-d6ff5f9ed5c2");
        dCache.setAllPersons(allPerson);

        //Display people should be equal to all persons
        ArrayList<Person> displayPersons = dCache.getDisplayPeople();
        assertEquals(allPerson, displayPersons);
    }

}

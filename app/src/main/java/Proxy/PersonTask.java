package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import com.example.familymap.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Model.Person;
import Requests.LoginRequest;
import Results.LoginResult;
import Results.PersonResult;

public class PersonTask extends AsyncTask<URL, Integer, PersonResult> {
    PersonResult pResult;

    private Context mContext;
    private MainActivity mainActivity;

    public PersonTask() {}

    public PersonTask(Context mContext, MainActivity mainActivity) {
        this.mContext = mContext;
        this.mainActivity = mainActivity;
    }

    @Override
    protected PersonResult doInBackground(URL... urls) {
        DataCache dCache = DataCache.getInstance();
        String url = "http://" + dCache.getServerHost()
                + ":" + dCache.getUserPort() + "/person";

        URL newURL;
        PersonResult persons = null;
        try {
            newURL = new URL(url);
            HttpClient httpClient = new HttpClient(newURL);
            persons = httpClient.getPersons();
            dCache.setAllPersons(persons.getData());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //This code will get all of the events - was told to do these in the same task
        url = "http://" + dCache.getServerHost()
                + ":" + dCache.getUserPort() + "/event";
        try {
            newURL = new URL(url);
            HttpClient httpClient = new HttpClient(newURL);
            dCache.setAllEvent(httpClient.getEvents().getData());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    @Override
    protected void onPostExecute(PersonResult result) {
        DataCache dCache = DataCache.getInstance();
        Person userPerson = dCache.findPerson(dCache.getPersonID());
        if(result.isSuccess()) {
            Toast.makeText(mContext, "Welcome " + userPerson.getFirstName() + " "
                            + userPerson.getLastName() + "!", Toast.LENGTH_SHORT).show();
            mainActivity.switchFragments();
        } else {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
//grab all events -
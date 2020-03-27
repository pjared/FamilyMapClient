package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

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

    public PersonTask() {}

    public PersonTask(Context mContext) {
        this.mContext = mContext;
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
            dCache.setAllEvent(httpClient.getEvents().getData());
            dCache.setAllPersons(persons.getData());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    protected void onPostExecute(PersonResult result) {
        DataCache dCache = DataCache.getInstance();
        String personName = dCache.findPerson(dCache.getPersonID());
        //personID is null, need to get it.
        if(result.isSuccess()) {
            Toast.makeText(mContext, "Welcome " + personName + "!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
//grab all events -
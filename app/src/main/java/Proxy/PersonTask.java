package Proxy;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;

import Requests.LoginRequest;
import Results.LoginResult;
import Results.PersonResult;

public class PersonTask extends AsyncTask<URL, Integer, PersonResult> {
    PersonResult pResult;

    private Context mContext;

    public PersonTask() {}

    @Override
    protected PersonResult doInBackground(URL... urls) {
        HttpClient httpClient = new HttpClient(urls[0]);

        PersonResult persons;
        persons = httpClient.getPersons();

        return persons;
    }
}

package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    protected void onPostExecute(PersonResult result) {
        DataCache dCache = DataCache.getInstance();
        if(result.isSuccess()) {
            //Now we need to get the persons first and last name
            storeData(result);
            Toast.makeText(mContext, "Welcome " + dCache.getFullUserName() + "!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void storeData(PersonResult result) {

    }
}
//grab all events -
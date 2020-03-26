package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.URL;

import Requests.LoginRequest;
import Results.LoginResult;

public class LoginTask extends AsyncTask<URL, Integer, LoginResult> {
    private LoginRequest lRequest;

    private Context mContext;

    public LoginTask() {}

    public LoginTask(String userName, String password) {
        lRequest = new LoginRequest(userName, password);
    }

    @Override
    protected LoginResult doInBackground(URL... urls) {
        HttpClient httpClient = new HttpClient(urls[0]);

        LoginResult login;
        login = httpClient.login(lRequest);

        return login;
    }

    @Override
    protected void onPostExecute(LoginResult result) {
        if(!result.isSuccess()) {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            PersonTask task = new PersonTask();
            //need to somehow grab the url from this method
            /*task.execute(new URL("https://" + serverEditText.toString() +
                    ":" + portEditText.toString() + "/person")); */
        }
    }
    //Add an onpost execute, takes a loginResult paramater - Toast if failed. do another task
    //gets the family, from there do the toast that success.
}

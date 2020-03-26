package Proxy;

import android.graphics.BitmapRegionDecoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;

public class LoginTask extends AsyncTask<URL, Integer, LoginResult> {
    private LoginRequest lRequest;

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

    //Add an onpost execute, takes a loginResult paramater - Toast if failed. do another task
    //gets the family, from there do the toast that success.
}

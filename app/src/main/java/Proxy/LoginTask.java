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

public class LoginTask extends AsyncTask<URL, Integer, LoginTask.LoginTaskResult> {

    private RegisterRequest rRequest;
    private LoginRequest lRequest;

    LoginTask() {}

    LoginTask(String userName, String password) {
        lRequest = new LoginRequest(userName, password);
    }

    LoginTask(String userName, String password, String email, String firstName, String lastName, String gender) {
        rRequest = new RegisterRequest(userName, password, email, firstName, lastName, gender);
    }

    @Override
    protected LoginTaskResult doInBackground(URL... urls) {
        HttpClient httpClient = new HttpClient();
        LoginResult login = new LoginResult();

        login = httpClient.login(lRequest);
        return new LoginTaskResult("poob");
    }

    public static class LoginTaskResult {
        //should return a name or a loginResult
        private String name;
        private Exception exception;

        LoginTaskResult(String name) {
            this.name = name;
        }

        LoginTaskResult(Exception exception) {
            this.exception = exception;
        }

        public String getName() {
            return name;
        }

        public boolean hasException() {
            return exception != null;
        }

        public Exception getException() {
            return exception;
        }
    }
}

package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.familymap.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;

import Requests.LoginRequest;
import Results.LoginResult;

public class LoginTask extends AsyncTask<URL, Integer, LoginResult> {
    private LoginRequest lRequest;

    Context mContext;
    private MainActivity mainActivity;

    public void setmContext(Context mContext, MainActivity mainActivity) {
        this.mContext = mContext;
        this.mainActivity = mainActivity;
    }

    public LoginTask() {}

    public LoginTask(String userName, String password) {
        lRequest = new LoginRequest(userName, password);
    }

    @Override
    protected LoginResult doInBackground(URL... urls) {
        DataCache dCache = DataCache.getInstance();
        String url = "http://" + dCache.getServerHost()
                + ":" + dCache.getUserPort() + "/user/login";
        LoginResult login = null;
        URL newURL;

        try {
            newURL = new URL(url);
            HttpClient httpClient = new HttpClient(newURL);
            login = httpClient.login(lRequest);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return login;
    }

    @Override
    protected void onPostExecute(LoginResult result) {
        DataCache dCache = DataCache.getInstance();
        if(result.isSuccess()) {
            dCache.setAuthToken(result.getAuthToken());
            dCache.setPersonID(result.getPersonID());
            PersonTask task = new PersonTask(mContext, mainActivity);
            task.execute();
        } else {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

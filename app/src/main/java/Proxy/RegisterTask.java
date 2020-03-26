package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.URL;

import Requests.RegisterRequest;
import Results.RegisterResult;

public class RegisterTask extends AsyncTask<URL, Integer, RegisterResult> {
    //this is going to be where I send the registers.
    private RegisterRequest rRequest;
    private Context mContext;

    public RegisterTask() {}

    public RegisterTask(String userName, String password, String email, String firstName, String lastName, String gender) {
        rRequest = new RegisterRequest(userName, password, email, firstName, lastName, gender);
    }

    @Override
    protected RegisterResult doInBackground(URL... urls) {
        HttpClient httpClient = new HttpClient(urls[0]);

        RegisterResult register;
        register = httpClient.register(rRequest);

        return register;
    }

    @Override
    protected void onPostExecute(RegisterResult result) {
        if(result.isSuccess()) {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(!result.isSuccess()) {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

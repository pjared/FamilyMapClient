package Proxy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.familymap.LoginFragment;
import com.example.familymap.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;

import Requests.RegisterRequest;
import Results.RegisterResult;

public class RegisterTask extends AsyncTask<URL, Integer, RegisterResult> {
    //this is going to be where I send the registers.
    private RegisterRequest rRequest;

    Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public RegisterTask() {}

    public RegisterTask(String userName, String password, String email, String firstName, String lastName, String gender) {
        rRequest = new RegisterRequest(userName, password, email, firstName, lastName, gender);
    }

    //need to ask about how to correctly send the URL since it get jumbled
    @Override
    protected RegisterResult doInBackground(URL... urls) {
        RegisterResult register;
        HttpClient httpClient = new HttpClient();
        register = httpClient.register(rRequest);
        return register;
    }

    @Override
    protected void onPostExecute(RegisterResult result) {
        DataCache dCache = DataCache.getInstance();
        if(result.isSuccess()) {
            dCache.setPersonID(result.getPersonID());
            dCache.setAuthToken(result.getAuthToken());
            PersonTask task = new PersonTask(mContext);
            task.execute();
        } else {
            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

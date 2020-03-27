package Proxy;

import android.util.JsonReader;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.EventResult;
import Results.LoginResult;
import Results.PersonResult;
import Results.RegisterResult;

public class HttpClient {
    private String serverHost;
    private String serverPort;
    private URL url;

    public HttpClient() {}

    //instead of this just do a URL?
    public HttpClient(String serverHost, String serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public HttpClient(URL url) {
        this.url = url;
    }

    //for the login passoff, just need to make a login proxy and then can do others

    public PersonResult getPersons() {
        PersonResult pResult = new PersonResult();
        DataCache dCache = DataCache.getInstance();
        try {
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);

            http.addRequestProperty("Authorization", dCache.getAuthToken());
            http.addRequestProperty("Accept", "application/json");

            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                pResult = new Gson().fromJson(respData, PersonResult.class);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return pResult;
    }

    public EventResult getEvents() {
        EventResult eResult = new EventResult();
        return eResult;
    }

    public RegisterResult register(RegisterRequest r) {
        RegisterResult newRegister = new RegisterResult();
        DataCache dCache = DataCache.getInstance();
        String userUrl = "http://" + dCache.getServerHost()
                + ":" + dCache.getUserPort() + "/user/register";
        try {
            url = new URL(userUrl);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            http.connect();
            String reqData =
                "{" +
                    "\"userName\": \"" + r.getUserName() + "\"," +
                    "\"password\": \"" + r.getPassword() + "\"," +
                    "\"email\": \"" + r.getEmail() + "\"," +
                    "\"firstName\": \"" + r.getFirstName() + "\"," +
                    "\"lastName\": \"" + r.getLastName() + "\"," +
                    "\"gender\": \"" + r.getGender() + "\"" +
                "}";

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                newRegister = new Gson().fromJson(respData, RegisterResult.class);
                //response data is the JSONed result, so need to break it up with GSON and store.
                System.out.println(respData);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                newRegister.setMessage("Register failed\n Username taken/already exists");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return newRegister;
    }

    public LoginResult login(LoginRequest l) {
        LoginResult newLogin = new LoginResult();
        try {
            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            String reqData =
                "{" +
                    "\"userName\": \"" + l.getUserName() + "\"," +
                    "\"password\": \"" + l.getPassword() + "\"" +
                "}";

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                newLogin = new Gson().fromJson(respData, LoginResult.class);
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                //use this to find out what kind of error happened here
                newLogin.setMessage("Missing field/invalid login");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return newLogin;
    }

    public static class BadResponseCodeException extends Exception {
        private final int responseCode;
        private final URL url;

        BadResponseCodeException(int responseCode, URL url) {
            super(String.format("Received response code (%d) for url (%s)", responseCode, url.toString()));
            this.responseCode = responseCode;
            this.url = url;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public URL getUrl() {
            return url;
        }
    }

    /*	The readString method shows how to read a String from an InputStream.  */
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}


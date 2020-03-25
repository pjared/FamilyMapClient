package Proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;
import Results.RegisterResult;

public class HttpClient {
    private String serverHost;
    private String serverPort;

    public HttpClient() {}

    public HttpClient(String serverHost, String serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    //for the login passoff, just need to make a login proxy and then can do others
    public RegisterResult register(RegisterRequest r) {
        RegisterResult newRegister = new RegisterResult();

        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            String reqData =
                "{" +
                    "\"userName\": \"" + r.getUserName() + "\"" +
                    "\"password\": \"" + r.getPassword() + "\"" +
                    "\"email\": \"" + r.getEmail() + "\"" +
                    "\"firstName\": \"" + r.getFirstName() + "\"" +
                    "\"lastName\": \"" + r.getLastName() + "\"" +
                    "\"gender\": \"" + r.getGender() + "\"" +
                "}";

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData); //need to read this when it goes through to see how to distrubute the data
                //also need to use it to give the user their authToken
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                //use this to find out what kind of error happened here
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
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            String reqData =
                "{" +
                    "\"userName\": \"" + l.getUserName() + "\"" +
                    "\"password\": \"" + l.getPassword() + "\"" +
                "}";

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData); //need to read this when it goes through to see how to distrubute the data
                //also need to use it to give the user their authToken
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                //use this to find out what kind of error happened here
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


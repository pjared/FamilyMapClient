package Proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpClient {

    public String getUrl(URL url) throws IOException, BadResponseCodeException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get response body input stream
            InputStream responseBody = connection.getInputStream();

            // Read response body bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = responseBody.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            // Convert response body bytes to a string
            return outputStream.toString();
        } else {
            throw new BadResponseCodeException(connection.getResponseCode(), url);
        }
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
}


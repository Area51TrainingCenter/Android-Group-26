package pe.area51.reversegeocoder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alumno on 8/23/16.
 */
public class HttpConnection {

    public static String doHttpGet(final String url) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        //El API Nominatim exige que pongamos un User Agent para identificar nuestra aplicación.
        httpURLConnection.setRequestProperty("User-Agent", "Android testing app.");
        final int responseCode = httpURLConnection.getResponseCode();
        final InputStreamReader inputStreamReader = new InputStreamReader(
                responseCode < 400 ?
                        httpURLConnection.getInputStream() :
                        httpURLConnection.getErrorStream()
        );
        final StringBuilder stringBuilder = new StringBuilder();
        final int bufferSize = 2048;
        final char[] buffer = new char[bufferSize];
        int n = 0;
        while ((n = inputStreamReader.read(buffer)) != -1) {
            stringBuilder.append(buffer, 0, n);
        }
        return stringBuilder.toString();
    }

}

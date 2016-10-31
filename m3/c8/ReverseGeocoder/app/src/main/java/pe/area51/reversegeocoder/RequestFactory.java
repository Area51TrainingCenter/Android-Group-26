package pe.area51.reversegeocoder;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by alumno on 8/25/16.
 */
public class RequestFactory {

    private final static String SERVER_URL = "http://nominatim.openstreetmap.org";
    private final static String REVERSE_GEOCODING_PATH = "/reverse";
    private final static String QUERY_FORMAT_JSON = "format=json";
    private final static String QUERY_LATITUDE = "lat=";
    private final static String QUERY_LONGITUDE = "lon=";
    private final static String CHARSET_UTF8 = "utf-8";

    public static Request<Address> doReverseGeocodingRequest(final double latitutde, final double longitude) {
        final Request<Address> request = new Request<>(buildReverseGeocodingQuery(latitutde, longitude), new AddressParser());
        return request;
    }

    private static String buildReverseGeocodingQuery(final double latitude, final double longitude) {
        try {
            final String encodedLatitude = URLEncoder.encode(String.valueOf(latitude), CHARSET_UTF8);
            final String encodedLongitude = URLEncoder.encode(String.valueOf(longitude), CHARSET_UTF8);
            return SERVER_URL +
                    REVERSE_GEOCODING_PATH +
                    "?" +
                    QUERY_FORMAT_JSON +
                    "&" +
                    QUERY_LATITUDE + encodedLatitude +
                    "&" +
                    QUERY_LONGITUDE + encodedLongitude;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

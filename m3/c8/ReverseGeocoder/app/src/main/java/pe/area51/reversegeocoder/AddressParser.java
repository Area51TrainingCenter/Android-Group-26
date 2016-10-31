package pe.area51.reversegeocoder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alumno on 8/23/16.
 */
public class AddressParser implements Parser<Address> {

    public Address parse(final String serverResponse) throws JSONException {
        final JSONObject serverResponseJsonObject = new JSONObject(serverResponse);
        final String latitude = serverResponseJsonObject.getString("lat");
        final String longitude = serverResponseJsonObject.getString("lon");
        final String displayName = serverResponseJsonObject.getString("display_name");
        final JSONObject addressJsonObject = serverResponseJsonObject.getJSONObject("address");
        final String city = addressJsonObject.getString("city");
        final String country = addressJsonObject.getString("country");
        return new Address(longitude, latitude, displayName, city, country);
    }

}

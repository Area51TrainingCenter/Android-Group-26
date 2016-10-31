package pe.area51.reversegeocoder;

import org.json.JSONException;

/**
 * Created by alumno on 8/25/16.
 */
public interface Parser<ResponseBody> {

    public ResponseBody parse(final String serverResponse) throws JSONException;

}

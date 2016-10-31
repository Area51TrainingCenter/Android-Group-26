package pe.area51.reversegeocoder;

import android.os.AsyncTask;
import android.support.annotation.StringRes;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Formatter;

/**
 * Created by alumno on 8/23/16.
 */
public class Request<ResponseBody> {

    public interface RequestCallback<ResponseBody> {

        void onResponse(final ResponseObject<ResponseBody> responseObject);

    }

    public void doAsync(final RequestCallback<ResponseBody> requestCallback) {
        new AsyncTask<Void, Void, ResponseObject<ResponseBody>>() {

            @Override
            protected ResponseObject<ResponseBody> doInBackground(Void... params) {
                return executeGetRequest(Request.this);
            }

            @Override
            protected void onPostExecute(ResponseObject responseObject) {
                requestCallback.onResponse(responseObject);
            }
        }.execute();
    }

    public ResponseObject doSync() {
        return executeGetRequest(this);
    }

    private final String urlQuery;
    private final Parser<ResponseBody> parser;

    public Request(String urlQuery, Parser<ResponseBody> parser) {
        this.urlQuery = urlQuery;
        this.parser = parser;
    }

    public String getUrlQuery() {
        return urlQuery;
    }

    public Parser<ResponseBody> getParser() {
        return parser;
    }

    private static <ResponseBody> ResponseObject<ResponseBody> executeGetRequest(final Request<ResponseBody> request) {
        String serverResponse = "";
        @StringRes int errorMessage = 0;
        boolean hasError = false;
        ResponseBody responseBodyObject = null;
        try {
            serverResponse = HttpConnection.doHttpGet(request.getUrlQuery());
            responseBodyObject = request.getParser().parse(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage = R.string.connection_error;
            hasError = true;
        } catch (JSONException e) {
            Log.e("Request", "Server response: " + serverResponse);
            e.printStackTrace();
            errorMessage = R.string.server_response_error;
            hasError = true;
        }
        return new ResponseObject<>(responseBodyObject, errorMessage, hasError);
    }

}

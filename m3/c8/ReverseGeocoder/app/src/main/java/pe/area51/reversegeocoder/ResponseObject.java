package pe.area51.reversegeocoder;

import android.support.annotation.StringRes;

/**
 * Created by alumno on 8/25/16.
 */
public class ResponseObject<ResponseBody> {

    private final ResponseBody responseBody;
    @StringRes
    private final int errorMessageId;
    private final boolean hasError;

    public ResponseObject(ResponseBody responseBody, int errorMessageId, boolean hasError) {
        this.responseBody = responseBody;
        this.errorMessageId = errorMessageId;
        this.hasError = hasError;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public int getErrorMessageId() {
        return errorMessageId;
    }

    public boolean hasError() {
        return hasError;
    }
}

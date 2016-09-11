package pe.area51.mybroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Alumno on 09/09/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    public final static String ACTION_BUTTON_CLICKED = "pe.area51.mybroadcastreceiver.BUTTON_CLICKED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            final String message;
            switch (intent.getAction()) {
                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    final boolean hasChangedToAirplaneMode = intent.getBooleanExtra("state", false);
                    if (hasChangedToAirplaneMode) {
                        message = context.getString(R.string.airplane_mode_on);
                    } else {
                        message = context.getString(R.string.airplane_mode_off);
                    }
                    break;
                case ACTION_BUTTON_CLICKED:
                    message = context.getString(R.string.button_clicked);
                    break;
                default:
                    message = "";
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}

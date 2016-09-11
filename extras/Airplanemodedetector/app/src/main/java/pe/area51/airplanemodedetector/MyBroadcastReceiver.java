package pe.area51.airplanemodedetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                final boolean isAirplaneOn = intent.getBooleanExtra("state", false);
                if (isAirplaneOn) {
                    showMessage(context, R.string.airplane_mode_on);
                } else {
                    showMessage(context, R.string.airplane_mode_off);
                }
            }
        }
    }

    private static void showMessage(final Context context, @StringRes final int stringId) {
        showMessage(context, context.getString(stringId));
    }

    private static void showMessage(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

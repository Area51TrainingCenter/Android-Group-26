package pe.area51.myservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

public class MyService extends Service {

    public static final int FOREGROUND_NOTIFICATION_ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showMessage("onCreate()");
        startForeground(FOREGROUND_NOTIFICATION_ID, createForegroundNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showMessage("onStartCommand()");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        showMessage("onDestroy()");
        super.onDestroy();
    }

    private Notification createForegroundNotification() {
        return new NotificationCompat.Builder(this)
                .setContentTitle("MyService")
                .setContentText("MyService...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
    }

    private void showMessage(final String message) {
        Toast.makeText(MyService.this, message, Toast.LENGTH_SHORT).show();
    }

}

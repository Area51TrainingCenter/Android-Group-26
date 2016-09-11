package pe.area51.mybroadcastreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_send_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent buttonClickedIntent = new Intent(MyBroadcastReceiver.ACTION_BUTTON_CLICKED);
                sendBroadcast(buttonClickedIntent);
            }
        });
    }
}

package pe.area51.myservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_startservice).setOnClickListener(this);
        findViewById(R.id.button_stopservice).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_startservice:
                startService();
                break;
            case R.id.button_stopservice:
                stopService();
                break;
        }
    }

    private void startService() {
        startService(new Intent(this, MyService.class));
    }

    private void stopService() {
        stopService(new Intent(this, MyService.class));
    }
}

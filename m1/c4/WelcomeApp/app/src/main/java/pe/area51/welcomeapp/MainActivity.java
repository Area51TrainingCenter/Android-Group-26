package pe.area51.welcomeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nameEditText = (EditText) findViewById(R.id.edittext_name);
        final Button showWelcomeButton = (Button) findViewById(R.id.button_show_welcome);
        showWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final Intent welcomeActivityIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                welcomeActivityIntent.putExtra(WelcomeActivity.ARG_NAME, name);
                startActivity(welcomeActivityIntent);
            }
        });
    }
}

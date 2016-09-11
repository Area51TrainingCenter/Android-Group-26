package pe.area51.welcomeapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    public static final String ARG_NAME = "name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final TextView welcomeMessageTextView = (TextView) findViewById(R.id.textview_welcome_message);
        final String name = getIntent().getStringExtra(ARG_NAME);
        welcomeMessageTextView.setText(getString(R.string.welcome_message, name));
    }
}

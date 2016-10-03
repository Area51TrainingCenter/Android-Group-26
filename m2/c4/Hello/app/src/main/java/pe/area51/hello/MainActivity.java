package pe.area51.hello;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final WelcomeFragment welcomeFragment = (WelcomeFragment) fragmentManager.findFragmentById(R.id.fragment_welcome);
        final LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragment_login);
        loginFragment.setOnShowWelcomeListener(new LoginFragment.OnShowWelcomeListener() {
            @Override
            public void onShowWelcome(final String name) {
                final String welcomeMessage = getString(R.string.welcome_message, name);
                welcomeFragment.setWelcomeMessage(welcomeMessage);
            }
        });
    }
}

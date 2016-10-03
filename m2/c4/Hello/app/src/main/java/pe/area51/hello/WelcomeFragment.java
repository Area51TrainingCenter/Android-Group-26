package pe.area51.hello;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {

    private TextView welcomeTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        welcomeTextView = (TextView) view.findViewById(R.id.textview_welcome);
        return view;
    }

    public void setWelcomeMessage(final String welcomeMessage) {
        welcomeTextView.setText(welcomeMessage);
    }
}

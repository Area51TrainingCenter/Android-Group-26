package pe.area51.hello;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private OnShowWelcomeListener onShowWelcomeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText nameEditText = (EditText) view.findViewById(R.id.edittext_name);
        view.findViewById(R.id.button_show_welcome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShowWelcomeListener != null) {
                    final String name = nameEditText.getText().toString();
                    onShowWelcomeListener.onShowWelcome(name);
                }
            }
        });
        return view;
    }

    public void setOnShowWelcomeListener(final OnShowWelcomeListener onShowWelcomeListener) {
        this.onShowWelcomeListener = onShowWelcomeListener;
    }

    public interface OnShowWelcomeListener {

        void onShowWelcome(final String name);

    }
}

package pe.area51.master_detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ListFragment.OnNoteSelectedListener {

    private static final String CONTENT_FRAGMENT_TAG = "content_fragment_tag";
    private static final String LIST_FRAGMENT_TAG = "list_fragment_tag";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        final ListFragment listFragment;
        if (savedInstanceState != null) {
            listFragment = (ListFragment) fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
        } else {
            listFragment = new ListFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder, listFragment, LIST_FRAGMENT_TAG)
                    .commit();
        }
        listFragment.setOnNoteSelectedListener(this);
    }

    @Override
    public void onNoteSelected(Note note) {
        final ContentFragment contentFragment = ContentFragment.newInstance(note);
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_placeholder, contentFragment, CONTENT_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
}

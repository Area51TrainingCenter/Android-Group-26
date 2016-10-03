package pe.area51.multifragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ListFragment.ListFragmentInterface {

    private FragmentManager fragmentManager;

    private static final String TRANSACTION_SHOW_CONTENT_PORTRAIT = "content_portrait";

    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            final ListFragment listFragment = new ListFragment();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (isOrientationLandscape()) {
                final ContentFragment contentFragment = ContentFragment.newInstance(null);
                fragmentTransaction
                        .replace(R.id.framelayout_placeholder_list, listFragment, LIST_FRAGMENT_TAG)
                        .replace(R.id.framelayout_placeholder_content, contentFragment, CONTENT_FRAGMENT_TAG);
            } else {
                fragmentTransaction
                        .replace(R.id.framelayout_placeholder_list, listFragment, LIST_FRAGMENT_TAG);
            }
            fragmentTransaction.commit();
            listFragment.setListFragmentInterface(this);
        } else {
            final Fragment listFragment = fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
            if (listFragment != null) {
                ((ListFragment) listFragment).setListFragmentInterface(this);
            }
            final Fragment contentFragment = fragmentManager.findFragmentByTag(CONTENT_FRAGMENT_TAG);
            if (contentFragment != null) {
                if (isOrientationLandscape()) {
                    fragmentManager.popBackStack(TRANSACTION_SHOW_CONTENT_PORTRAIT, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.framelayout_placeholder_content, contentFragment, CONTENT_FRAGMENT_TAG)
                                    .commit();
                            fragmentManager.removeOnBackStackChangedListener(this);
                        }
                    });
                } else {
                    fragmentManager
                            .beginTransaction()
                            .remove(listFragment)
                            .remove(contentFragment)
                            .commit();
                    fragmentManager.executePendingTransactions();
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.framelayout_placeholder_list, listFragment, LIST_FRAGMENT_TAG)
                            .commit();
                    fragmentManager
                            .beginTransaction()
                            .addToBackStack(TRANSACTION_SHOW_CONTENT_PORTRAIT)
                            .replace(R.id.framelayout_placeholder_list, contentFragment, CONTENT_FRAGMENT_TAG)
                            .commit();
                }
            }
        }
    }

    @Override
    public void onNoteSelected(final Note note) {
        final ContentFragment contentFragment = ContentFragment.newInstance(note);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isOrientationLandscape()) {
            fragmentTransaction
                    .replace(R.id.framelayout_placeholder_content, contentFragment, CONTENT_FRAGMENT_TAG);
        } else {
            fragmentTransaction
                    .addToBackStack(TRANSACTION_SHOW_CONTENT_PORTRAIT)
                    .replace(R.id.framelayout_placeholder_list, contentFragment, CONTENT_FRAGMENT_TAG);
        }
        fragmentTransaction.commit();
    }

    private boolean isOrientationLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

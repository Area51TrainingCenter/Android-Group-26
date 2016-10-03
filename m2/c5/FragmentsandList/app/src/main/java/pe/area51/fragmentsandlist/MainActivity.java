package pe.area51.fragmentsandlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ListFragment.ListFragmentInterface {

    private FragmentManager fragmentManager;

    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /*
        Utilizamos el fragment manager del Activity para poder realizar transacciones de fragments.
        Debemos utilizar el fragment manager del API de soporte, ya que estamos utilizando el Activity
        y los fragments del API de soporte. para esto utilizamos el método "getSupportFragmentManager"
        y NO el método "getFragmentManager", ya que este último utiliza el fragment manager del framework base.
         */
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        /*
        Si el "savedInstanceState" es nulo, entonces es la primera vez que se inicia el Activity.
        Si en caso contrario no es nulo, entonces el Activity se está restaurando (por un cambio de
        configuración -como rotar la pantalla- o por restaurarla desde caché por ejemplo).
         */
        if (savedInstanceState == null) {
            final ListFragment listFragment = new ListFragment();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, listFragment, LIST_FRAGMENT_TAG)
                    .commit();
            listFragment.setListFragmentInterface(this);
        } else {
            /*
            Como el Activity se está restaurando entonces también ha restaurado a sus fragments,
            por lo que podemos identificarlos por su etiqueta para obtenerlos.
             */
            final Fragment fragment = fragmentManager.findFragmentByTag(LIST_FRAGMENT_TAG);
            if (fragment != null) {
                //Reestablecemos el listener (El listener no se reestablece automáticamente).
                ((ListFragment) fragment).setListFragmentInterface(this);
            }
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        final ContentFragment contentFragment = ContentFragment.newInstance(note);
        fragmentManager.beginTransaction()
                /*
                Agregamos esta transacción al backstack. Esto permitirá que cuando se presiones el botón "Back",
                entonces se deshaga la transacción y se restaure el estado anterior. De forma practica, permitirá
                regresar al fragment anterior (al estado anterior).
                 */
                .addToBackStack(null)
                .replace(R.id.container, contentFragment, CONTENT_FRAGMENT_TAG)
                .commit();
    }
}

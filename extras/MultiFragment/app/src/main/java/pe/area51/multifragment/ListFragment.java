package pe.area51.multifragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private final static int TEST_NOTES_SIZE = 30;
    private ListView listView;

    private ListFragmentInterface listFragmentInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.listview_notes);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setAdapter(new NotesAdapter(getActivity(), getTestNotes(TEST_NOTES_SIZE)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listFragmentInterface != null) {
                    final Note note = ((NotesAdapter) listView.getAdapter()).getItem(position);
                    listFragmentInterface.onNoteSelected(note);
                }
            }
        });
    }

    private ArrayList<Note> getTestNotes(final int size) {
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            final Note note = new Note(i,
                    "New Note Title " + i,
                    "New note content " + i,
                    System.currentTimeMillis(),
                    System.currentTimeMillis());
            notes.add(note);
        }
        return notes;
    }

    public void setListFragmentInterface(ListFragmentInterface listFragmentInterface) {
        this.listFragmentInterface = listFragmentInterface;
    }

    private static class NotesAdapter extends ArrayAdapter<Note> {

        public NotesAdapter(final Context context, final List<Note> notes) {
            super(context, 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            Esta forma de generar elementos de lista no es eficiente, ya que no estamos reutilizando los elementos
	        de lista que salen de nuestro campo de visión ("convertView"), además que estamos ejecutando continuamente
	        el método "findViewById". Deberíamos utilizar por ejemplo el patrón ViewHolder para mejorar el rendimiento:
	        "http://developer.android.com/intl/es/training/improving-layouts/smooth-scrolling.html".
	        */
            final Note note = getItem(position);
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View listElement = layoutInflater.inflate(R.layout.element_note, parent, false);
            ((TextView) listElement.findViewById(R.id.textview_title)).setText(note.getTitle());
            ((TextView) listElement.findViewById(R.id.textview_content)).setText(note.getContent());
            return listElement;
        }
    }

    public interface ListFragmentInterface {

        public void onNoteSelected(final Note note);

    }

}

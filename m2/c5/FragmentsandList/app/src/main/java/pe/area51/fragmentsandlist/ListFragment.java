package pe.area51.fragmentsandlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    public static final String TAG = "ListFragment";

    private ListView listViewItems;

    private ListFragmentInterface listFragmentInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listViewItems = (ListView) view.findViewById(R.id.listview_items);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final NoteArrayAdapter noteArrayAdapter = new NoteArrayAdapter(getActivity(), createTestNotes(100));
        listViewItems.setAdapter(noteArrayAdapter);
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listFragmentInterface != null) {
                    final Note note = ((NoteArrayAdapter) listViewItems.getAdapter()).getItem(position);
                    listFragmentInterface.onNoteSelected(note);
                }
            }
        });
    }

    public void setListFragmentInterface(ListFragmentInterface listFragmentInterface) {
        this.listFragmentInterface = listFragmentInterface;
    }

    private List<Note> createTestNotes(final int size) {
        final List<Note> notes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final Note note = new Note(
                    i + 1,
                    "Title " + (i + 1),
                    "This is the content of the note " + (i + 1) + ".",
                    System.currentTimeMillis()
            );
            notes.add(note);
        }
        return notes;
    }

    public static class NoteArrayAdapter extends ArrayAdapter<Note> {

        public static final String TAG = "NoteArrayAdapter";

        NoteArrayAdapter(final Context context, final List<Note> notes) {
            super(context, 0, notes);
        }

        private static class NoteViewHolder {
            private TextView textViewDate;
            private TextView textViewTitle;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView() - position: " + position + "; convertView " + (convertView != null ? "!= null" : "== null"));
            final View viewListElement;
            final NoteViewHolder noteViewHolder;
            if (convertView != null) {
                viewListElement = convertView;
                noteViewHolder = (NoteViewHolder) viewListElement.getTag();
            } else {
                final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                viewListElement = layoutInflater.inflate(R.layout.element_note, parent, false);
                noteViewHolder = new NoteViewHolder();
                noteViewHolder.textViewDate = (TextView) viewListElement.findViewById(R.id.textview_date);
                noteViewHolder.textViewTitle = (TextView) viewListElement.findViewById(R.id.textview_title);
                viewListElement.setTag(noteViewHolder);
            }
            final Note note = getItem(position);
            noteViewHolder.textViewDate.setText(String.valueOf(note.getCreationTimestamp()));
            noteViewHolder.textViewTitle.setText(note.getTitle());
            return viewListElement;
        }
    }

    public interface ListFragmentInterface {

        public void onNoteSelected(final Note note);

    }
}

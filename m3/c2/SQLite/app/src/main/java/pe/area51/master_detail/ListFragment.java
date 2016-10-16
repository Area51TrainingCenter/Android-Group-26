package pe.area51.master_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private final static String TAG = "ListFragment";

    private ListView listView;

    private OnNoteSelectedListener onNoteSelectedListener;

    private NoteAdapter noteAdapter;

    private SQLiteManager sqLiteManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.listview_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        sqLiteManager = SQLiteManager.getInstance(getActivity());
        noteAdapter = new NoteAdapter(getContext(), sqLiteManager.getNotes());
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onNoteSelectedListener != null) {
                    final Note note = noteAdapter.getItem(position);
                    onNoteSelectedListener.onNoteSelected(note);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                final String title = "Title";
                final String content = "Content";
                final long creationTimestamp = System.currentTimeMillis();
                final long modificationTimestamp = System.currentTimeMillis();
                final long id = sqLiteManager.insertNote(new Note(-1, title, content, creationTimestamp, modificationTimestamp));
                final Note note = new Note(id, title, content, creationTimestamp, modificationTimestamp);
                noteAdapter.add(note);
                return true;
            default:
                return false;
        }
    }

    public void setOnNoteSelectedListener(OnNoteSelectedListener onNoteSelectedListener) {
        this.onNoteSelectedListener = onNoteSelectedListener;
    }

    public interface OnNoteSelectedListener {

        void onNoteSelected(final Note note);

    }

    public static class NoteAdapter extends ArrayAdapter<Note> {

        public NoteAdapter(final Context context, final ArrayList<Note> notes) {
            super(context, 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            Podemos usar un "ViewHolder" para mejorar aún más el rendimiento:
            https://developer.android.com/training/improving-layouts/smooth-scrolling.html
            */
            Log.d(TAG, "getView() position: " + position);
            final View view;
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(R.layout.element_note, parent, false);
            } else {
                view = convertView;
            }
            final TextView textViewTitle = (TextView) view.findViewById(R.id.textview_title);
            final TextView textViewContent = (TextView) view.findViewById(R.id.textview_content);
            final Note note = getItem(position);
            textViewTitle.setText(note.getTitle());
            textViewContent.setText(note.getContent());
            return view;
        }
    }
}

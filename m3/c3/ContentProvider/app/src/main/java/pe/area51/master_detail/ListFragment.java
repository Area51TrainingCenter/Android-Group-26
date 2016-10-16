package pe.area51.master_detail;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
        noteAdapter = new NoteAdapter(getContext(), getAllNotes());
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

    private ArrayList<Note> getAllNotes() {
        final Cursor cursor = getActivity().getContentResolver().query(NoteContract.URI, null, null, null, null);
        final ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            final long id = cursor.getLong(cursor.getColumnIndex(NoteContract.ID));
            final String title = cursor.getString(cursor.getColumnIndex(NoteContract.TITLE));
            final String content = cursor.getString(cursor.getColumnIndex(NoteContract.CONTENT));
            final long creationTimestamp = cursor.getLong(cursor.getColumnIndex(NoteContract.CREATION_TIMESTAMP));
            final long modificationTimestamp = cursor.getLong(cursor.getColumnIndex(NoteContract.MODIFICATION_TIMESTAMP));
            final Note note = new Note(id, title, content, creationTimestamp, modificationTimestamp);
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    private void addNewNote() {
        final String title = "Test Title";
        final String content = "Test Content";
        final long creationTimestamp = System.currentTimeMillis();
        final long modificationTimestamp = System.currentTimeMillis();
        final ContentValues contentValues = new ContentValues();
        contentValues.put(NoteContract.TITLE, title);
        contentValues.put(NoteContract.CONTENT, content);
        contentValues.put(NoteContract.CREATION_TIMESTAMP, creationTimestamp);
        contentValues.put(NoteContract.MODIFICATION_TIMESTAMP, modificationTimestamp);
        final Uri newNoteUri = getActivity()
                .getContentResolver().insert(NoteContract.URI, contentValues);
        final long id = ContentUris.parseId(newNoteUri);
        ((NoteAdapter) listView.getAdapter())
                .add(new Note(id, title, content, creationTimestamp, modificationTimestamp));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note:
                addNewNote();
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

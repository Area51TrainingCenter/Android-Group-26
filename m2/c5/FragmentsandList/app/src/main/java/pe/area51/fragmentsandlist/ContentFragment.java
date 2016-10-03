package pe.area51.fragmentsandlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {

    private TextView titleTextView;
    private TextView contentTextView;

    private final static String ARG_NOTE_TITLE = "title";
    private final static String ARG_NOTE_CONTENT = "content";

    public static ContentFragment newInstance(final Note note) {
        final ContentFragment contentFragment = new ContentFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(ARG_NOTE_TITLE, note.getTitle());
        arguments.putString(ARG_NOTE_CONTENT, note.getContent());
        contentFragment.setArguments(arguments);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content, container, false);
        titleTextView = (TextView) view.findViewById(R.id.textview_title);
        contentTextView = (TextView) view.findViewById(R.id.textview_content);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
    }

    private void showNote() {
        final String noteTitle = getArguments().getString(ARG_NOTE_TITLE);
        final String noteContent = getArguments().getString(ARG_NOTE_CONTENT);
        titleTextView.setText(noteTitle);
        contentTextView.setText(noteContent);
    }

}

package pe.area51.master_detail;

import android.net.Uri;

public class NoteContract {

    public static final Uri URI = Uri.parse("content://pe.area51.master_detail.ContentProvider/note");

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String CREATION_TIMESTAMP = "creationTimestamp";
    public static final String MODIFICATION_TIMESTAMP = "modificationTimestamp";

    public static final String MIME_ITEM = "vnd.android.cursor.item/pe.area51.master_detail.Note";
    public static final String MIME_DIR = "vnd.android.cursor.dir/pe.area51.master_detail.Note";

}

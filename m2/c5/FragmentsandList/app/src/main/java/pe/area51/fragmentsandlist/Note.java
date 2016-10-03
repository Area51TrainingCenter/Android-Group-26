package pe.area51.fragmentsandlist;

public class Note {

    private final long id;
    private final String title;
    private final String content;
    private final long creationTimestamp;

    public Note(long id, String title, String content, long creationTimestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }
}

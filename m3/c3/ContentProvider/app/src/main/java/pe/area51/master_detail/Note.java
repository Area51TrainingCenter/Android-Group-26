package pe.area51.master_detail;

public class Note {

    private final long id;
    private final String title;
    private final String content;
    private final long creationTimestamp;
    private final long modificationTimestamp;

    public Note(long id, String title, String content, long creationTimestamp, long modificationTimestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
        this.modificationTimestamp = modificationTimestamp;
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

    public long getModificationTimestamp() {
        return modificationTimestamp;
    }
}

public class SubTask extends Task {

    private final int epicID;

    public SubTask(String title, String description, int epicID) {
        super(title, description);
        this.epicID = epicID;
    }

    public SubTask(int id, String title, String description, TaskStatus status, int epicID) {
        super(id, title, description, status);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", epicID=" + epicID +
                ", status=" + getStatus() +
                '}';
    }
}

package task;

public class SubTask extends Task {

    private final int epicid;

    public SubTask(String title, String description, int epicID) {
        super(title, description);
        this.epicid = epicID;
    }

    public SubTask(int id, String title, String description, TaskStatus status, int epicID) {
        super(id, title, description, status);
        this.epicid = epicID;
    }

    public int getEpicid() {
        return epicid;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", epicID=" + epicid +
                ", status=" + getStatus() +
                '}';
    }
}

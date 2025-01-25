package task;

import java.time.Instant;

public class SubTask extends Task {

    private final int epicid;

    public SubTask(String title, String description, TaskStatus status, int epicid) {
        super(title, description, status);
        this.epicid = epicid;
    }

    public SubTask(String title, String description, int epicID) {
        super(title, description);
        this.epicid = epicID;
    }

    public SubTask(String title, String description, TaskStatus status, Instant startTime, long duration, int epicID) {
        super(title, description, status, startTime, duration);
        this.epicid = epicID;
    }

    public int getEpicid() {
        return epicid;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
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

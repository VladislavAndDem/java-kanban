package task;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public SubTask(String title, String description, TaskStatus status, LocalDateTime startTime, Duration duration, int epicID) {
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
                ", id=" + getId() +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", endTime='" + getEndTime() +
                ", epicID=" + epicid +
                '}';
    }
}

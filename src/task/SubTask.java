package task;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private final int epicId;

    public SubTask(String title, String description, int epicID) {
        super(title, description);
        this.epicId = epicID;
    }

    public SubTask(String title, String description, TaskStatus status, LocalDateTime startTime, Duration duration, int epicID) {
        super(title, description, status, startTime, duration);
        this.epicId = epicID;
    }

    public int getEpicId() {
        return epicId;
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
                ", epicID=" + epicId +
                '}';
    }
}

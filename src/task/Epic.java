package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> listSubTask = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String title, String description) {
        super(title, description); // вызов родительского конструктора
    }

    public Epic(String title, String description, TaskStatus status, LocalDateTime startTime, Duration duration) {
        super(title, description, status, startTime, duration);
        this.endTime = super.getEndTime();
    }

    //   Добовление подзадачи в лист епика
    public void addSubTask(SubTask subTask) {
        listSubTask.add(subTask);
    }

    //   Очищаем лист с подзадачами епика
    public void clearSubTask() {
        listSubTask.clear();
    }

    public ArrayList<SubTask> getListSubTask() {
        return new ArrayList<>(listSubTask);
    }

    public void setListSubTask(ArrayList<SubTask> listSubTask) {
        this.listSubTask = listSubTask;
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                " id='" + getId() +
                ", title= " + getTitle() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", status = " + getStatus() +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", listSubTask.size = " + listSubTask.size() +
                '}';
    }
}

package task;

import java.time.Instant;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> listSubTask = new ArrayList<>();
    private Instant endTime;

    public Epic(String title, String description) {
        super(title, description); // вызов родительского конструктора
    }

    public Epic(String title, String description, TaskStatus status) {
        super(title, description, status);
    }

    public Epic(String title, String description, TaskStatus status, Instant startTime, long duration) {
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
        return listSubTask;
    }

    public void setListSubTask(ArrayList<SubTask> listSubTask) {
        this.listSubTask = listSubTask;
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Task.Epic{" +
                "title= " + getTitle() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", listSubTask.size = " + listSubTask.size() +
                ", status = " + getStatus() +
                '}';
    }
}

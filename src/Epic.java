import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> listSubTask = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description); // вызов родительского конструктора
    }

    public Epic(int id, String title, String description, TaskStatus status) {
        super(id, title, description, status);
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
    public String toString() {
        return "Epic{" +
                "title= " + getTitle() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", listSubTask.size = " + listSubTask.size() +
                ", status = " + getStatus() +
                '}';
    }
}

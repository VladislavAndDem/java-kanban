package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    List<SubTask> getEpicSubtasks(Epic epic);

    Task getTaskByID(int id);

    Epic getEpicByID(int id);

    SubTask getSubtaskByID(int id);

    Task addNewTask(Task task);

    Epic addNewEpic(Epic epic);

    SubTask addNewSubTask(SubTask subTask);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    SubTask updateSubtask(SubTask subTask);

    void deleteTasks();

    void deleteEpics();

    void deleteSubTasks();

    void deleteTaskByID(int id);

    void deleteEpicByID(int id);

    void deleteSubtaskByID(int id);

    List<Task> getHistory();
}

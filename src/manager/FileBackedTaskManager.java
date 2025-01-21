package manager;

import task.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private static final String HEADER_CSV_FILE = "id,type,name,description,status,epicID\n";
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void loadFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            String line;
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line.isEmpty()) {
                    break;
                }
                if (line.equals(HEADER_CSV_FILE)) {
                    Task task = fromString(line);

                    switch (task.getType()) {
                        case TASK:
                            addTaskDontIncreaseId(task);
                            break;
                        case EPIC:
                            Epic epic = (Epic) task;
                            addEpicDontIncreaseId(epic);
                            break;
                        case SUBTASK:
                            SubTask subTask = (SubTask) task;
                            addSubtaskDontIncreaseId(subTask);
                            break;
                    }

                }
            }

            // Обновляем счетчик идентификаторов максимальным значением из файла
            String line2;
            while (bufferedReader.ready()) {
                line2 = bufferedReader.readLine();
                if (line2.isEmpty()) {
                    break;
                }

                Task task = fromString(line2);
                if (getTaskId() < task.getId()) {
                    setUpdateId(task.getId());
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось считать данные из файла.");
        }
    }

    public void save() {


        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEADER_CSV_FILE);

            for (Task task : super.getTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : super.getEpics()) {
                writer.write(toString(epic));
            }
            for (SubTask subTask : super.getSubTasks()) {
                writer.write(toString(subTask));
            }

            writer.write("\n");


        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл", e);
        }

    }

    private String getParentEpicId(Task task) {
        if (task.getType().equals(TaskType.SUBTASK)) {
            return Integer.toString(((SubTask) task).getEpicid());
        }
        return "";
    }

    //    Метод сохранения задачи в строку
    public String toString(Task task) {
        String[] toJoin = {Integer.toString(task.getId()), task.getType().toString(), task.getTitle(),
                task.getDescription(), task.getStatus().toString(), getParentEpicId(task)};
        return String.join(",", toJoin);
    }

    //    Метод создания задачи из строки
    private Task fromString(String value) {
        String[] params = value.split(",");
        int id = Integer.parseInt(params[0]);
        String type = params[1];
        String title = params[2];
        String description = params[3];
        TaskStatus status = TaskStatus.valueOf(params[4].toUpperCase());
        Integer epicId = type.equals("SUBTASK") ? Integer.parseInt(params[5]) : null;

        if (type.equals("EPIC")) {
            Epic epic = new Epic(title, description, status);
            epic.setId(id);
            epic.setStatus(status);
            return epic;
        } else if (type.equals("SubTask")) {
            SubTask subTask = new SubTask(title, description, status, epicId);
            subTask.setId(id);
            return subTask;
        } else {
            Task task = new Task(title, description, status);
            task.setId(id);
            return task;
        }
    }

    @Override
    public Task getTaskByID(int id) {
        Task task = super.getTaskByID(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic epic = super.getEpicByID(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubtaskByID(int id) {
        SubTask subtask = super.getSubtaskByID(id);
        save();
        return subtask;
    }

    @Override
    public void deleteTaskByID(int id) {
        super.deleteTaskByID(id);
        save();
    }

    @Override
    public void deleteEpicByID(int id) {
        super.deleteEpicByID(id);
        save();
    }

    @Override
    public void deleteSubtaskByID(int id) {
        super.deleteSubtaskByID(id);
        save();
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = super.getTasks();
        save();
        return tasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epics = super.getEpics();
        save();
        return epics;
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subTasks = super.getSubTasks();
        save();
        return subTasks;
    }

    @Override
    public Task addNewTask(Task task) {
        super.addNewTask(task);
        save();
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask addNewSubTask(SubTask subTask) {
        super.addNewSubTask(subTask);
        save();
        return subTask;
    }

    @Override
    public Task updateTask(Task task) {
        super.updateTask(task);
        save();
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {
        super.updateSubtask(subTask);
        save();
        return subTask;
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(Epic epic) {
        ArrayList<SubTask> subTasks = super.getEpicSubtasks(epic);
        save();
        return subTasks;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasks = super.getHistory();
        save();
        return tasks;
    }
}

package manager;

import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private int taskId = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();


    private int getIncreasedD() {
        return ++taskId;
    }

    @Override
    public ArrayList<Task> getTasks() {

        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public Task getTaskByID(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return tasks.get(id);
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epics.get(id);
    }

    @Override
    public SubTask getSubtaskByID(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTasks.get(id);
    }

    @Override
    public Task addNewTask(Task task) {
        task.setId(getIncreasedD()); // в объекте таск инициализироали id
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        epic.setId(getIncreasedD());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask addNewSubTask(SubTask subTask) {
        subTask.setId(getIncreasedD());
        Epic epic = epics.get(subTask.getEpicid());
        epic.addSubTask(subTask); // кладем подзадачу в лист подзадач епика
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(epic);
        return subTask;
    }

    @Override
    public Task updateTask(Task task) {
        Integer taskID = task.getId();
        if (taskID == null || !tasks.containsKey(taskID)) { // если мапа не содержит такой ключ
            return null;
        }
        tasks.replace(taskID, task);
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Integer epicID = epic.getId();
        if (epicID == null || !epics.containsKey(epicID)) {
            return null;
        }
        Epic parentEpic = epics.get(epicID);
//        Присваиваем листу лист родительского епика
        ArrayList<SubTask> parentEpicListSubTask = parentEpic.getListSubTask();
        if (!parentEpicListSubTask.isEmpty()) { // если лист не пустой
            for (SubTask subTask : parentEpicListSubTask) {
                subTasks.remove(subTask.getId());
            }
        }
        epics.replace(epicID, epic);
//      если у обновленного епика есть подзадачи, добавляем их в лист подзадач
        ArrayList<SubTask> newEpicListSubTask = epic.getListSubTask();
        if (!newEpicListSubTask.isEmpty()) {
            for (SubTask subTask : newEpicListSubTask) {
                subTasks.put(subTask.getId(), subTask);
            }
        }

        updateEpicStatus(epic);
        return epic;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {
        Integer subTaskID = subTask.getId();
        if (subTaskID == null || !subTasks.containsKey(subTaskID)) {
            return null;
        }
        int epicID = subTask.getEpicid();
        SubTask oldSubtask = subTasks.get(subTaskID);
        subTasks.replace(subTaskID, subTask);
        // обновляем подзадачу в списке подзадач эпика и проверяем статус эпика
        Epic epic = epics.get(epicID);
        ArrayList<SubTask> subTaskList = epic.getListSubTask();
        subTaskList.remove(oldSubtask);
        subTaskList.add(subTask);
        epic.setListSubTask(subTaskList);
        updateEpicStatus(epic);
        return subTask;
    }

    // метод обновляющий статус епика на основе статусов его подзадач
    private void updateEpicStatus(Epic epic) {
        int allIsDoneCount = 0;
        int allIsInNewCount = 0;
//  инициализируем список list  списком епика, содержашего подзадачи
        ArrayList<SubTask> list = epic.getListSubTask();

        for (SubTask subtask : list) {
            if (subtask.getStatus() == TaskStatus.DONE) {
                allIsDoneCount++;
            }
            if (subtask.getStatus() == TaskStatus.NEW) {
                allIsInNewCount++;
            }
        }
        if (allIsDoneCount == list.size()) { /*если колличество подзадачь со статусами DONE рано длинне листа, то епик
        принимает статус DONE*/
            epic.setStatus(TaskStatus.DONE);
        } else if (allIsInNewCount == list.size()) { /*если колличество подзадачь со статусами NEW рано длинне листа, то епик
        принимает статус NEW*/
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public ArrayList<SubTask> getEpicSubtasks(Epic epic) {
        return epic.getListSubTask();
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
        if (getTasks().isEmpty()) {
            System.out.println("Список всех задач пуст");
        }
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
        if (getEpics().isEmpty()) {
            System.out.println("Список всех епиков и их подзадач пуст");
        }
    }

    @Override
    public void deleteSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTask();
            epic.setStatus(TaskStatus.NEW);
        }
        System.out.println("Список всех подзадач пуст");
    }

    @Override
    public void deleteTaskByID(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicByID(int id) {
        ArrayList<SubTask> epicSubtasks = epics.get(id).getListSubTask();
        epics.remove(id);
        for (SubTask subtask : epicSubtasks) {
            subTasks.remove(subtask.getId());
        }
    }

    @Override
    public void deleteSubtaskByID(int id) {
        SubTask subtask = subTasks.get(id);
        int epicID = subtask.getEpicid();
        subTasks.remove(id);
//        обновляем список подзадач и статус эпика
        Epic epic = epics.get(epicID);
        ArrayList<SubTask> subtaskList = epic.getListSubTask();
        subtaskList.remove(subtask);
        epic.setListSubTask(subtaskList);
        updateEpicStatus(epic);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void printAllManager() {
        System.out.println("Печатаем tasks:");
        if (!getTasks().isEmpty()) {
            for (int i = 0; i < getTasks().size(); i++) {
                System.out.println(getTasks().get(i));
            }
        } else {
            System.out.println("содержит - null");
        }
        System.out.println("Печатаем epics:");
        if (!getEpics().isEmpty()) {
            for (int i = 0; i < getEpics().size(); i++) {
                System.out.println(getEpics().get(i));
            }
        } else {
            System.out.println("содержит - null");
        }
        System.out.println("Печатаем subtask:");
        if (!getSubTasks().isEmpty()) {
            for (int i = 0; i < getSubTasks().size(); i++) {
                System.out.println(getSubTasks().get(i));
            }
        } else {
            System.out.println("содержит - null");
        }
        System.out.println("История просмотров:");
        for (Task T : historyManager.getHistory()) {
            System.out.println(T);
        }

    }
}

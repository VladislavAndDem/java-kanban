import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int taskId = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private int getUppId() {
        taskId++;
        return taskId;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public Task getTaskByID(int id) {
        return tasks.get(id);
    }

    public Epic getEpicByID(int id) {
        return epics.get(id);
    }

    public SubTask getSubtaskByID(int id) {
        return subTasks.get(id);
    }

    public Task addNewTask(Task task) {
        task.setId(getUppId()); // в обекте таск инициализироали id
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addNewEpic(Epic epic) {
        epic.setId(getUppId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask addNewSubTask(SubTask subTask) {
        subTask.setId(getUppId());
        Epic epic = epics.get(subTask.getEpicID());
        epic.addSubTask(subTask); // кладем подзадачу в лист подзадач епика
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(epic);


        return subTask;
    }

    //-------------------------------------------------------
    public Task updateTask(Task task) {
        Integer taskID = task.getId();
        if (taskID == null || !tasks.containsKey(taskID)) { // если мапа не содержит такой ключ
            return null;
        }
        tasks.replace(taskID, task);
        return task;
    }

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

    public SubTask updateSubtask(SubTask subTask) {
        Integer subTaskID = subTask.getId();
        if (subTaskID == null || !subTasks.containsKey(subTaskID)) {
            return null;
        }
        int epicID = subTask.getEpicID();
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

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void deleteSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTask();
            epic.setStatus(TaskStatus.NEW);
        }
    }

    public void deleteTaskByID(int id) {
        tasks.remove(id);
    }

    public void deleteEpicByID(int id) {
        ArrayList<SubTask> epicSubtasks = epics.get(id).getListSubTask();
        epics.remove(id);
        for (SubTask subtask : epicSubtasks) {
            subTasks.remove(subtask.getId());
        }
    }

    public void deleteSubtaskByID(int id) {
        SubTask subtask = subTasks.get(id);
        int epicID = subtask.getEpicID();
        subTasks.remove(id);
//        обновляем список подзадач и статус эпика
        Epic epic = epics.get(epicID);
        ArrayList<SubTask> subtaskList = epic.getListSubTask();
        subtaskList.remove(subtask);
        epic.setListSubTask(subtaskList);
        updateEpicStatus(epic);
    }


    public void printAllManager() {
        System.out.println("Печатаем tasks:");
        if (!getTasks().isEmpty()) {
            for (Task arg : getTasks().values()) { //test
                System.out.println(arg);
            }
        } else {
            System.out.println("содержит - null");
        }
        System.out.println("Печатаем epics:");
        if (!getEpics().isEmpty()) {
            for (Epic ep : getEpics().values()) {
                System.out.println(ep);
            }
        } else {
            System.out.println("содержит - null");
        }
        System.out.println("Печатаем subtask:");
        if (!getSubTasks().isEmpty()) {
            for (SubTask sb : getSubTasks().values()) {
                System.out.println(sb);
            }
        } else {
            System.out.println("содержит - null");
        }


    }

    @Override
    public String toString() {
        return "" + tasks + " "
                + epics + " " + subTasks;
    }
}

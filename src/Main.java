import taskManager.InMemoryTaskManager;
import taskManager.Managers;
import task.Task;
import task.Epic;
import task.SubTask;
import task.TaskStatus;


public class Main {

    private static final InMemoryTaskManager inMemoryTaskManager = Managers.getDefault();

    public static void main(String[] args) {

        addTasks();
        printAllTasks();
        printViewHistory();
    }

    private static void addTasks() {
        Task task1 = new Task("Задача 1", "Описание 1");
        inMemoryTaskManager.addNewTask(task1);

        Task task1Uppdate = new Task(task1.getId(), "Задача 1.1",
                "Описание 1.1", TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateTask(task1Uppdate);
        inMemoryTaskManager.addNewTask(new Task("Задача 2", "Описание 2"));


        Epic epic1 = new Epic("Епик 1", "Описание Епика 1");
        inMemoryTaskManager.addNewEpic(epic1);
        SubTask epic1SubTusk1 = new SubTask("Подзадача 1 епика 1", "Описание подзадача 1 епика 1",
                epic1.getId());
        SubTask epic1SubTusk2 = new SubTask("Подзадача 2 епика 1", "Описание подзадача 2 епика 1",
                epic1.getId());
        SubTask epic1SubTusk3 = new SubTask("Подзадача 3 епика 1", "Описание подзадача 3 епика 1",
                epic1.getId());
        inMemoryTaskManager.addNewSubTask(epic1SubTusk1);
        inMemoryTaskManager.addNewSubTask(epic1SubTusk2);
        inMemoryTaskManager.addNewSubTask(epic1SubTusk3);
        epic1SubTusk2.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateSubtask(epic1SubTusk2);
    }

    private static void printAllTasks() {
        System.out.println("Задачи:");
        for (Task task : Main.inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : Main.inMemoryTaskManager.getEpics()) {
            System.out.println(epic);

            for (Task task : Main.inMemoryTaskManager.getEpicSubtasks(epic)) {
                System.out.println("--> " + task);
            }
        }

        System.out.println("Подзадачи:");
        for (Task subtask : Main.inMemoryTaskManager.getSubTasks()) {
            System.out.println(subtask);
        }
    }

    private static void printViewHistory() {
        //просматриваем 11 задач, в истории должны отобразиться последние 10
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getEpicByID(3);
        Main.inMemoryTaskManager.getTaskByID(1);
        Main.inMemoryTaskManager.getSubtaskByID(2);
        Main.inMemoryTaskManager.getSubtaskByID(5);
        Main.inMemoryTaskManager.getSubtaskByID(4);
        Main.inMemoryTaskManager.getEpicByID(3);
        Main.inMemoryTaskManager.getSubtaskByID(3);
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getSubtaskByID(5);

        System.out.println();
        System.out.println("История просмотров:");
        for (Task task : Main.inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
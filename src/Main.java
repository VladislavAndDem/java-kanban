import java.util.SortedMap;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        Task task1 = new Task("Первая таска 1", "описание таск 1");
        Task task2 = new Task("Вторая Таска 2", "описание таск 2");

        Task task1Add = manager.addNewTask(task1);
        Task task2Add = manager.addNewTask(task2);

        Task task1Update = new Task(task1.getId(), "Первая таска 1.1.", "Описание таск 1.1",
                TaskStatus.IN_PROGRESS);
        Task task2Update = new Task(task2.getId(), "Первая таска 2.1.", "Описание таск 2.1",
                TaskStatus.IN_PROGRESS);

        Task task1Updated = manager.updateTask(task1Update);
        Task task2Updated = manager.updateTask(task2Update);


        Epic epic1 = new Epic("Первый епик 1", "Описание епика 1");
        Epic epic2 = new Epic("Второй епик 1", "Описание епика 2");

        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        System.out.println(epic1);

        SubTask subTask1Epic1 = new SubTask("Подзадача 1 епика 1", "Описание подзадачи 1 епика 1",
                epic1.getId());
        SubTask subTask2Epic1 = new SubTask("Подзадача 2 епика 1", "Описание подзадачи 2 епика 1",
                epic1.getId());
        SubTask subTask1Epic2 = new SubTask("Подзадача 1 епика 2", "Описание подзадачи 1 епика 2",
                epic2.getId());
        manager.addNewSubTask(subTask1Epic1);
        manager.addNewSubTask(subTask2Epic1);
        manager.addNewSubTask(subTask1Epic2);

        System.out.println();
        manager.printAllManager();
        System.out.println();
        //System.out.println(manager.getEpics());

        System.out.println("Обновляем статус подзадачь и епика:");
        subTask1Epic1.setStatus(TaskStatus.IN_PROGRESS);
        subTask2Epic1.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subTask1Epic1);
        manager.updateSubtask(subTask2Epic1);
        manager.updateEpic(epic1);

        manager.printAllManager();

        subTask1Epic1.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subTask1Epic1);
        manager.updateEpic(epic1);


        System.out.println();
        System.out.println("Статус епика1 -> DONE");
        System.out.println(manager.getEpics().get(epic1.getId()));

        System.out.println();
        System.out.println("Удаляем епик1 по ID:");
        manager.deleteEpicByID(epic1.getId());
        System.out.println(manager.getEpics());

        System.out.println();
        System.out.println("Удаляем все епики");
        manager.deleteEpics();
        System.out.println(manager.getEpics());

        System.out.println();
        System.out.println("Удаляем все таски:");
        manager.deleteTasks();
        manager.printAllManager();
    }
}

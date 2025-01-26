import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;


public class Main {

    private static final InMemoryTaskManager inMemoryTaskManager = Managers.getDefault();

    public static void main(String[] args) {

        Epic epic1 = inMemoryTaskManager.addNewEpic(new Epic(
                "Епик 1",
                "Описание Епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 1, 0),
                Duration.ofHours(1)));
        SubTask subTask1_1 = new SubTask(
                "Подзадача 1 епика 1",
                "Описание подзадача 1 епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 2, 0),
                Duration.ofHours(1),
                epic1.getId());

        SubTask subTask2_1 = new SubTask("Подзадача 2 епика 1",
                "Описание подзадача 2 епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 3, 0),
                Duration.ofHours(1),
                epic1.getId());

        SubTask subTask3_1 = new SubTask(
                "Подзадача 3 епика 1",
                "Описание подзадача 3 епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 4, 0),
                Duration.ofHours(1),
                epic1.getId());


        SubTask subTaskOne = inMemoryTaskManager.addNewSubTask(subTask1_1);
       // SubTask subTaskTwo = inMemoryTaskManager.addNewSubTask(subTask2_1);
       // SubTask subTaskThree = inMemoryTaskManager.addNewSubTask(subTask3_1);

        System.out.println(subTaskOne);
    }
}
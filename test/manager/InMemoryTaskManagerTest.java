package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    Task task1;
    Epic epic1;
    SubTask subTask1_1;
    SubTask subTask2_1;
    SubTask subTask3_1;

    public static String format(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    @BeforeEach
    public void setUp() {
        taskManager = Managers.getDefault();

        task1 = new Task(
                "Задача 1",
                "Описание 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 0, 1),
                Duration.ofHours(1));

        epic1 = new Epic(
                "Епик 1",
                "Описание Епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 2, 0),
                Duration.ofHours(1));


    }

    @Test
    void addNewTask() {
        //проверяем, что InMemoryTaskManager добавляет задачи и может найти их по id;
        taskManager.addNewTask(task1);
        final Task savedTask = taskManager.getTaskByID(task1.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task1, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task1, tasks.getFirst(), "Задачи не совпадают.");
    }

    /*@Test
    void addNewEpicAndSubtasks() {
        //проверяем, что InMemoryTaskManager добавляет эпики и подзадачи и может найти их по id;
        Epic epicOne = taskManager.addNewEpic(epic1);
        subTask1_1 = new SubTask(
                "Подзадача 1 епика 1",
                "Описание подзадача 1 епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 4, 0),
                Duration.ofHours(1),
                epic1.getId());

        subTask2_1 = new SubTask("Подзадача 2 епика 1",
                "Описание подзадача 2 епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 6, 0),
                Duration.ofHours(1),
                epic1.getId());

        subTask3_1 = new SubTask(
                "Подзадача 3 епика 1",
                "Описание подзадача 3 епика 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 2, 4, 12),
                Duration.ofHours(1),
                epic1.getId());
        SubTask subTaskOne = taskManager.addNewSubTask(subTask1_1);
        SubTask subTaskTwo = taskManager.addNewSubTask(subTask2_1);
        SubTask subTaskThree = taskManager.addNewSubTask(subTask3_1);
        final Epic savedEpic = taskManager.getEpicByID(epicOne.getId());
        final SubTask savedSubtask1 = taskManager.getSubtaskByID(subTaskOne.getId());
        final SubTask savedSubtask2 = taskManager.getSubtaskByID(subTaskTwo.getId());
        final SubTask savedSubtask3 = taskManager.getSubtaskByID(subTaskThree.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertEquals(epic1, savedEpic, "Эпики не совпадают.");
        assertEquals(subTask1_1, savedSubtask1, "Подзадачи не совпадают.");
        assertEquals(subTask3_1, savedSubtask3, "Подзадачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic1, epics.getFirst(), "Эпики не совпадают.");

        final List<SubTask> subtasks = taskManager.getSubTasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Подзадачи не совпадают.");
    }*/

    /*@Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        // метод обновления Task должнен возвращать задачу с тем же идентификатором

        Task task1v1 = new Task("новое имя",
                "новое описание",
                TaskStatus.DONE,
                LocalDateTime.of(2025, Month.JANUARY, 1, 5, 0),
                Duration.ofHours(1));
        task1v1.setId(task1.getId());
        Task actual = taskManager.updateTask(task1v1);
        assertEquals(task1, actual, "Вернулась задача с другим id");
    }*/

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        // метод обновления Epic должнен возвращать задачу с тем же идентификатором

        taskManager.addNewEpic(epic1);
        final Epic updatedEpic = new Epic(
                "новое имя",
                "новое описание",
                TaskStatus.DONE,
                epic1.getStartTime(),
                epic1.getDuration());
        updatedEpic.setId(epic1.getId());
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(epic1, actual, "Вернулся эпик с другим id");
    }

    /*@Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        // метод обновления SunTask должнен возвращать задачу с тем же идентификатором

        taskManager.addNewEpic(epic1);
        taskManager.addNewSubTask(subTask1_1);
        final SubTask updatedSubtask = new SubTask(
                "новое имя",
                "новое описание",
                TaskStatus.DONE,
                subTask1_1.getStartTime(),
                subTask1_1.getDuration(),
                epic1.getId());
        updatedSubtask.setId(subTask1_1.getId());
        final SubTask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(subTask1_1, actual, "Вернулась подзадача с другим id");
    }*/

    @Test
    public void subTaskForEpicContainsIdThisEpic() {
        //подзадача эпика должна содержать ид этого епика
        final Epic epic = new Epic("Имена", "описание");
        SubTask subTusk = new SubTask("Володя", "Молодец",
                epic.getId());
        assertEquals(epic.getId(), subTusk.getEpicid(), "Подзадача не относится к епику");
    }

    @Test
    void testTaskChangesInManager() {

        taskManager.addNewTask(task1); //id = 1, status = NEW

        task1.setTitle("Новое название");
        task1.setDescription("Новое описание");

        assertEquals("Новое название", taskManager.getTasks().get(0).getTitle());
        assertEquals("Новое описание", taskManager.getTasks().get(0).getDescription());
    }

}


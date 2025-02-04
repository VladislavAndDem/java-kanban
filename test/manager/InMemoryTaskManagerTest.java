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

    public static String format(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    @BeforeEach
    void beforeEach() {
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
        final Task task = taskManager.addNewTask(new Task("Задача 1",
                "Описание 1",
                TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 0, 1),
                Duration.ofHours(1)));

        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

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

    @Test
    public void subTaskForEpicContainsIdThisEpic() {
        //подзадача эпика должна содержать ид этого епика
        final Epic epic = new Epic("Имена", "описание");
        SubTask subTusk = new SubTask("Володя", "Молодец",
                epic.getId());
        assertEquals(epic.getId(), subTusk.getEpicId(), "Подзадача не относится к епику");
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


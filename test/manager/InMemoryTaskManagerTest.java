package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
import task.Task;
import task.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask() {
        //проверяем, что InMemoryTaskManager добавляет задачи и может найти их по id;
        final Task task = taskManager.addNewTask(new Task("Задача 1", "Описание 1"));
        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpicAndSubtasks() {
        //проверяем, что InMemoryTaskManager добавляет эпики и подзадачи и может найти их по id;
        final Epic epic1 = taskManager.addNewEpic(new Epic("Епик 1", "Описание Епика 1"));
        final SubTask subTask1Epic1 = taskManager.addNewSubTask(new SubTask("Подзадача 1 епика 1",
                "Описание подзадача 1 епика 1", epic1.getId()));
        final SubTask subTask2Epic1 = taskManager.addNewSubTask(new SubTask("Подзадача 2 епика 1",
                "Описание подзадача 2 епика 1", epic1.getId()));
        final SubTask subTask3Epic1 = taskManager.addNewSubTask(new SubTask("Подзадача 3 епика 1",
                "Описание подзадача 3 епика 1",
                epic1.getId()));
        final Epic savedEpic = taskManager.getEpicByID(epic1.getId());
        final SubTask savedSubtask1 = taskManager.getSubtaskByID(subTask1Epic1.getId());
        final SubTask savedSubtask2 = taskManager.getSubtaskByID(subTask2Epic1.getId());
        final SubTask savedSubtask3 = taskManager.getSubtaskByID(subTask3Epic1.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertEquals(epic1, savedEpic, "Эпики не совпадают.");
        assertEquals(subTask1Epic1, savedSubtask1, "Подзадачи не совпадают.");
        assertEquals(subTask3Epic1, savedSubtask3, "Подзадачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic1, epics.getFirst(), "Эпики не совпадают.");

        final List<SubTask> subtasks = taskManager.getSubTasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        // метод обновления Task должнен возвращать задачу с тем же идентификатором
        final Task expected = new Task("имя", "описание");
        taskManager.addNewTask(expected);
        final Task updatedTask = new Task(expected.getId(), "новое имя", "новое описание", TaskStatus.DONE);
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулась задачи с другим id");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        // метод обновления Epic должнен возвращать задачу с тем же идентификатором
        final Epic expected = new Epic("имя", "описание");
        taskManager.addNewEpic(expected);
        final Epic updatedEpic = new Epic(expected.getId(), "новое имя", "новое описание", TaskStatus.DONE);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        // метод обновления SunTask должнен возвращать задачу с тем же идентификатором
        final Epic epic = new Epic("имя", "описание");
        taskManager.addNewEpic(epic);
        final SubTask expected = new SubTask("имя", "описание", epic.getId());
        taskManager.addNewSubTask(expected);
        final SubTask updatedSubtask = new SubTask(expected.getId(), "новое имя", "новое описание",
                TaskStatus.DONE, epic.getId());
        final SubTask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

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
        Task task = new Task("Задача", "Описание");
        taskManager.addNewTask(task); //id = 1, status = NEW

        task.setTitle("Новое название");
        task.setDescription("Новое описание");

        assertEquals("Новое название", taskManager.getTasks().get(0).getTitle());
        assertEquals("Новое описание", taskManager.getTasks().get(0).getDescription());
    }
}


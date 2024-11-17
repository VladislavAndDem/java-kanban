package taskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.TaskStatus;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    private  TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        // get History Должен возвращать Список Из 10 Задач
        for (int i = 0; i < 11; i++) { // добавляем 11 задач в мапу tasks
            taskManager.addNewTask(new Task("Некоторая задача", "Некоторое описание"));
        }

        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTaskByID(task.getId()); // получаем все 11 объектов из мапы tasks по id
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество элементов в истории ");
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        // get History Должен Возвращать Старую Подзадачу После Обновления
        Task washFloor = new Task("Задача 1", "Описание 1");
        taskManager.addNewTask(washFloor);
        taskManager.getTaskByID(washFloor.getId());
        taskManager.updateTask(new Task(washFloor.getId(), "Задача 1.1",
                "Описание 1.1", TaskStatus.IN_PROGRESS));
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(washFloor.getTitle(), oldTask.getTitle(), "В истории не сохранилась старая версия задачи");
        assertEquals(washFloor.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");

    }

}
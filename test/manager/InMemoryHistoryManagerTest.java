package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void getHistoryShouldReturnListWithoutDuplicates() {
        // get History Должен возвращать Список Из 10 Задач
        for (int i = 0; i < 5; i++) { // добавляем 5 задач в мапу tasks
            taskManager.addNewTask(new Task("Некоторая задача", "Некоторое описание"));
        }
        // получаем таски по id
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(3);
        taskManager.getTaskByID(3);
        // В истории должно быть два элемента
        List<Task> list = taskManager.getHistory();
        assertEquals(2, list.size(), "Неверное количество элементов в истории ");
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
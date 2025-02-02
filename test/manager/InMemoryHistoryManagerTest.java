package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager manager;
    private Task task;
    private Task task2;

    @BeforeEach
    void setUp() {
        manager = new InMemoryHistoryManager();
        task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        task2 = new Task(
                "Задача2",
                "Описание задачи2",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1)
        );
    }

    @Test
    void testCreateTaskToHistory() {
        task.setId(1);

        // Добавляем задачу в историю
        manager.add(task);

        // Получаем историю задач
        List<Task> history = manager.getHistory();

        // Проверяем, что задача добавлена в историю
        assertEquals(1, history.size(), "History должна содержать одну задачу");
        assertEquals(task.getId(), history.getFirst().getId(), "tasks.Task Id должен совпадать");
        assertEquals(task.getTitle(), history.getFirst().getTitle(), "tasks.Task title должен совпадать");
        assertEquals(task.getDescription(), history.getFirst().getDescription(), "tasks.Task description должен совпадать");
        assertEquals(task.getStatus(), history.getFirst().getStatus(), "tasks.Task status должен совпадать");
    }

    @Test
    void testMultipleTasksInHistory() {
        task.setId(1);
        task2.setId(2);

        // Добавляем задачи в историю
        manager.add(task);
        manager.add(task2);

        // Получаем историю задач
        List<Task> history = manager.getHistory();

        // Проверяем, что история содержит две задачи
        assertEquals(2, history.size(), "History должна содержать две задачи");

        // Проверяем, что задачи в истории соответствуют добавленным
        assertEquals(task.getId(), history.get(0).getId(), "Первая задача должна быть task");
        assertEquals(task2.getId(), history.get(1).getId(), "Вторая задача должна быть task2");
    }
}
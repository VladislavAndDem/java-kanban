package server;

import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryHandlerTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer hts = new HttpTaskServer(manager);

    public HistoryHandlerTest() throws IOException {
    }

    @Test
    public void testHandle() throws IOException, InterruptedException {
        Task task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        Task task2 = new Task(
                "Задача2",
                "Описание задачи2",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1)
        );
        manager.addNewTask(task);
        manager.addNewTask(task2);
        manager.getTaskByID(1);
        manager.getTaskByID(2);

        hts.startServer();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        List<Task> tasksHistory = manager.getHistory();
        assertEquals(2, tasksHistory.size(), "Некорректное количество задач");
        assertEquals(2, tasksHistory.getLast().getId(), "Некорректный порядок задач");

        hts.stopServer();
    }
}
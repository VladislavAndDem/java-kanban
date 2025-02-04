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

import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrioritizedHandlerTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer hts = new HttpTaskServer(manager);

    public PrioritizedHandlerTest() throws IOException {
    }

    @Test
    public void testHandle() throws IOException, InterruptedException {
        Task task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 5, 0),
                Duration.ofHours(1)
        );
        manager.addNewTask(task);

        hts.startServer();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        hts.stopServer();
    }
}
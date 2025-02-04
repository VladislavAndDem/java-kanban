package server;


import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static server.GsonFactory.getGson;

public class TaskHandlerTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer hts = new HttpTaskServer(manager);
    Gson gson = getGson();

    public TaskHandlerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.deleteTasks();
        hts.startServer();
    }

    @AfterEach
    public void shutDown() {
        hts.stopServer();
    }

    @Test
    public void testGetTasksWith200Response() throws IOException, InterruptedException {
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

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");
    }

    @Test
    public void testGetTaskByIdWith200Response() throws IOException, InterruptedException {
        Task task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        manager.addNewTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Задача", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }


    @Test
    public void testDeleteTaskWith200Response() throws IOException, InterruptedException {
        Task task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        manager.addNewTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Task> tasksFromManager = manager.getTasks();
        assertEquals(200, response.statusCode());
        assertEquals(0, tasksFromManager.size(), "Некорректное количество задач");
    }

    @Test
    public void testUpdateTaskWith200Response() throws IOException, InterruptedException {
        Task task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        manager.addNewTask(task);

        Task newTask = new Task(
                "Новая задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1)
        );

        String taskJson = gson.toJson(newTask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Новая задача", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testCreateTaskWith201Response() throws IOException, InterruptedException {
        Task task = new Task(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        String taskJson = gson.toJson(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Задача", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }
}
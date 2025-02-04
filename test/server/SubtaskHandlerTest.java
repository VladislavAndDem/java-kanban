package server;

import com.google.gson.Gson;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.SubTask;
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

public class SubtaskHandlerTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer hts = new HttpTaskServer(manager);
    Gson gson = getGson();

    public SubtaskHandlerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        manager.deleteEpics();
        manager.deleteSubTasks();
        hts.startServer();
    }

    @AfterEach
    public void shutDown() {
        hts.stopServer();
    }

    @Test
    public void testGetSubtasksWith200Response() throws IOException, InterruptedException {
        Epic epic = new Epic(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        SubTask subtask = new SubTask(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1),
                1
        );

        manager.addNewEpic(epic);
        manager.addNewSubTask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> subtasksFromManager = manager.getSubTasks();

        assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
    }

    @Test
    public void testGetSubtaskByIdWith200Response() throws IOException, InterruptedException {
        Epic epic = new Epic(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        SubTask subtask = new SubTask(
                "Подзадача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1),
                1
        );
        manager.addNewEpic(epic);
        manager.addNewSubTask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> subtasksFromManager = manager.getSubTasks();
        assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Подзадача", subtasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testDeleteSubtaskWith200Response() throws IOException, InterruptedException {
        Epic epic = new Epic(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        SubTask subtask = new SubTask(
                "Подзадача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1),
                1
        );

        manager.addNewEpic(epic);
        manager.addNewSubTask(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<SubTask> subtasksFromManager = manager.getSubTasks();
        assertEquals(0, subtasksFromManager.size(), "Некорректное количество задач");
    }

    @Test
    public void testUpdateSubtaskWith200Response() throws IOException, InterruptedException {
        Epic epic = new Epic(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        SubTask subtask = new SubTask(
                "Подзадача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1),
                1
        );
        SubTask newSubtask = new SubTask(
                "Новая подзадача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 3, 0),
                Duration.ofHours(1),
                1
        );
        newSubtask.setId(2);
        manager.addNewEpic(epic);
        manager.addNewSubTask(subtask);

        String subtaskJson = gson.toJson(newSubtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> subtasksFromManager = manager.getSubTasks();
        assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Новая подзадача", subtasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testCreateSubtaskWith201Response() throws IOException, InterruptedException {
        Epic epic = new Epic(
                "Задача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 0, 0),
                Duration.ofHours(1)
        );
        SubTask subtask = new SubTask(
                "Подзадача",
                "Описание задачи",
                TaskStatus.NEW,
                LocalDateTime.of(2025, JANUARY, 1, 1, 0),
                Duration.ofHours(1),
                1
        );
        manager.addNewEpic(epic);
        String subtaskJson = gson.toJson(subtask);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> subtasksFromManager = manager.getSubTasks();

        assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        assertEquals(1, subtasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Подзадача", subtasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }
}
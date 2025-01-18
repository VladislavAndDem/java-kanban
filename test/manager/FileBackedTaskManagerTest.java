package manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends InMemoryTaskManagerTest {
    public static final Path path = Path.of("data.test.csv");
    File file = new File(String.valueOf(path));

    private FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    public void beforeEach() {

        fileBackedTaskManager = new FileBackedTaskManager(Managers.getDefault(), file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldCorrectlySaveAndLoad() {
        Task task = new Task("name", "description", TaskStatus.NEW);
        fileBackedTaskManager.addNewTask(task);
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Managers.getDefault(), file);

        // Проверка на существование файла
        if (file.exists()) {
            fileBackedTaskManager.loadFromFile();
        } else {
            // Обработка ошибки загрузки файла
            System.out.println("Файл не найден");
        }

        List<Task> tasks = fileBackedTaskManager.getTasks();

        assertEquals(List.of(task), tasks);
    }

    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() {
        //проверка на сохранение и загрузку задачь, епиков и подзадач
        FileBackedTaskManager fileManager = new FileBackedTaskManager(Managers.getDefault(), file);
        fileManager.save();
        fileManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getTasks());
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getEpics());
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getSubTasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        // проверка на сохранение и загрузку пустой истории
        FileBackedTaskManager fileManager = new FileBackedTaskManager(Managers.getDefault(), file);
        fileManager.save();
        fileManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getHistory());
    }
}
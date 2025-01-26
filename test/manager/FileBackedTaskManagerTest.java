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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileBackedTaskManagerTest extends InMemoryTaskManagerTest {
    /*private static final Path path = Path.of("testFile");
    private FileBackedTaskManager fileBackedTaskManager;
    File temp = new File(String.valueOf(path));

    @BeforeEach
    void setUp() {
        fileBackedTaskManager = new FileBackedTaskManager(temp);
    }

    @AfterEach
    void afferEach() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void shouldSaveTasksEpicsSubtasks() {
        Task task = new Task("Test 1", "Testing task 1", TaskStatus.NEW, Instant.now(), 0);
        fileBackedTaskManager.addNewTask(task);

        *//*Epic epic = new Epic("Epic 1", "Testing epic 1", TaskStatus.NEW, Instant.now(), 0);
        fileBackedTaskManager.addNewEpic(epic);

        SubTask subTask = new SubTask("Subtask 1", "Testing subtask 1", TaskStatus.NEW, Instant.now(), 0, epic.getId());
        fileBackedTaskManager.addNewSubTask(subTask);*//*

        assertTrue(temp.length() > 0);
    }

    @Test
    public void shouldLoadTasksEpicsSubtasks() {
        Task task = new Task("Test 1", "Testing task 1", TaskStatus.NEW, Instant.now(), 0);
        fileBackedTaskManager.addNewTask(task);
        *//*Epic epic = new Epic("Epic 1", "Testing epic 1", TaskStatus.NEW, Instant.now(), 0);
        fileBackedTaskManager.addNewEpic(epic);*//*

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);

        *//*SubTask subTask = new SubTask("Subtask 1", "Testing subtask 1", TaskStatus.NEW, Instant.now(),0, epic.getId());
        fileBackedTaskManager.addNewSubTask(subTask);*//*

        fileBackedTaskManager.loadFromFile();
        assertEquals(tasks, fileBackedTaskManager.getTasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() {
        fileBackedTaskManager.save();
        fileBackedTaskManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getTasks());
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getEpics());
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getSubTasks());
    }*/
}
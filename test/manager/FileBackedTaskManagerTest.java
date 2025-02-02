package manager;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends InMemoryTaskManagerTest {
    private static final Path path = Path.of("testFile");
    private FileBackedTaskManager fileBackedTaskManager;
    File temp = new File(String.valueOf(path));

    @BeforeEach
    public void setUp() {
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
        // Должен сохранять таски, епики и сабтаски
    }

    @Test
    public void shouldLoadTasksEpicsSubtasks() {
        // Должен загружать таски, епики и сабтаски

    }

    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() {
        fileBackedTaskManager.save();
        fileBackedTaskManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getTasks());
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getEpics());
        assertEquals(Collections.EMPTY_LIST, fileBackedTaskManager.getSubTasks());
    }
}
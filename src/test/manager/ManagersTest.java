package test.manager;

import org.junit.jupiter.api.Test;
import taskManager.InMemoryTaskManager;
import taskManager.Managers;
import taskManager.InMemoryHistoryManager;
import static org.junit.jupiter.api.Assertions.*;


class ManagersTest {

    @Test
    void getDefaultShouldInitializeInMemoryTaskManager() {
        assertInstanceOf(InMemoryTaskManager.class, Managers.getDefault());
    }

    @Test
    void getDefaultHistoryShouldInitializeInMemoryHistoryManager() {
        assertInstanceOf(InMemoryHistoryManager.class, Managers.getDefaultHistory());
    }
}
package taskManager;

import task.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTOY_VIEWING = 10;
    private final List<Task> historyList = new ArrayList<>();

    @Override
    public void addHistory(Task task) {
        if (historyList.size() == MAX_HISTOY_VIEWING) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
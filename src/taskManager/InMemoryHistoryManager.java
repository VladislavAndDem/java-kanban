package taskManager;

import task.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_VIEWING = 10;
    private final List<Task> historyList = new ArrayList<>();

    @Override
    public void addHistory(Task task) {

        Task taskCopy = task;
        if (task == null){
            return;
        }
        if (historyList.size() == MAX_HISTORY_VIEWING) {
            historyList.removeFirst();
        }
        historyList.add(taskCopy);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
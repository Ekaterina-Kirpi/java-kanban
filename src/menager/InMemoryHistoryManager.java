package menager;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> historyTasks = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if (historyTasks.size() == 10) {
            historyTasks.remove(0);
        }
        if (task != null) {
            historyTasks.add(task);
        }
    }


    @Override
    public ArrayList<Task> getHistory() {
        return historyTasks;
    }
}





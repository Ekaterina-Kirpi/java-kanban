package menager;

import tasks.Task;

import java.util.List;

public interface TaskManager {
    void saveNewTask(Task task);

    Task getTaskById(int id);

    Task update(Task task);

    List<Task> getAllTasks();

    void removeById(int id);

    void removeAllTask();

    HistoryManager getHistory();

}
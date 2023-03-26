package manager;

import tasks.Task;

import java.util.List;

public interface TaskManager {
    Task saveNewTask(Task task);

    Task getTaskById(int id);

    Task update(Task task);

    List<Task> getAllTasks();

    void removeById(int id);

    void removeAllTask();

    List<Task> getAllOnlyTasks();


    HistoryManager getHistoryManager();
    List<Task> getPrioritizedTasks();

}
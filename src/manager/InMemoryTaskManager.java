package manager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class InMemoryTaskManager implements TaskManager {

    HistoryManager historyManager;
    protected HashMap<Integer, Task> tasksMap;
    private static int id = 1;

    public InMemoryTaskManager() {
        this.tasksMap = new HashMap<>();
        historyManager = Managers.getDefaultHistory();

    }


    @Override
    public void saveNewTask(Task task) {
        tasksMap.put(task.getId(), task);
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasksMap.get(taskId);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public Task update(Task task) {

        tasksMap.put(task.getId(), task);

        return tasksMap.get(id);

    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasksMap.values());
    }

    @Override
    public void removeById(int id) {
        if (tasksMap.containsKey(id)) {
            if (tasksMap.get(id) instanceof EpicTask) {
                for (Task task : ((EpicTask) tasksMap.get(id)).getSubTasks()) {
                    tasksMap.remove(task.getId());
                    historyManager.remove(task.getId());
                }
            }
            tasksMap.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeAllTask() {
        tasksMap = new HashMap<>();
    }


    public static int generateId() {
        return id++;
    }

    public List<EpicTask> getAllEpicTasks() {

        ArrayList<EpicTask> epicTasks = new ArrayList<>();

        for (Task task : tasksMap.values()) {

            if (task.getClass() == EpicTask.class) {
                //проверка эпик ли? если да, то добавить
                epicTasks.add((EpicTask) task);
            }
        }
        return epicTasks;
    }

    public List<SubTask> getSubTasksFromEpic(int id) {

        if (tasksMap.get(id).getClass() != EpicTask.class) return null;

        return ((EpicTask) tasksMap.get(id)).getSubTasks();

    }

    public HistoryManager getHistory() {
        return historyManager;
    }

    public void setHistoryManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }
}

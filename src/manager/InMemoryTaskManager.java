package manager;

import exceptions.ManagerValidateException;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {

    HistoryManager historyManager;
    protected Map<Integer, Task> tasksMap;
    private static int id = 1;
    protected Set<Task> prioritizedTasks;
    private final Comparator<Task> comparator;

    public InMemoryTaskManager() {
        this.tasksMap = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        comparator = (o1, o2) -> {
            if (o1.getStartTime() != null && o2.getStartTime() != null) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
            if (o1.getStartTime() == null) return 1;
            return -1;
        };
        prioritizedTasks = new TreeSet<>(comparator);
    }


    @Override
    public void saveNewTask(Task task) {
        if (!checkTime(task)) throw new ManagerValidateException("Задание пересекается с другими задачами");
        prioritizedTasks.add(task);
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
        return tasksMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Task> getAllOnlyTasks() {
        List<Task> allOnlyTasks = new ArrayList<>();
        for (Task t : tasksMap.values()) {
            if (t.getClass() == Task.class) {
                allOnlyTasks.add((Task) t);
            }
        }
        return allOnlyTasks;
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

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public void setHistoryManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }


    public boolean checkTime(Task task) {
        if (task.getStartTime() == null) return true;
        for (Task t : prioritizedTasks) {
            if (t.getStartTime() != null && t.getEndTime() != null) {
                if ((task.getStartTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getEndTime())) ||
                        ((task.getEndTime().isBefore(t.getEndTime()) && task.getEndTime().isAfter(t.getStartTime())))) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
        //toList();
    }
}
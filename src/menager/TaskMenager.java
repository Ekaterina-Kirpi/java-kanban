package menager;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskMenager {
    private HashMap<Integer, Task> tasksMap;
    private static int id = 1;

    public TaskMenager() {

        this.tasksMap = new HashMap<>();
    }

    public void saveNewTask(Task task) {
        tasksMap.put(task.getId(), task);
    }

    public Task getTaskById(int taskId) {
        return tasksMap.get(taskId);
    }

    public Task update(Task task, int id) {
        tasksMap.put(id, task);
        return tasksMap.get(id);
    }

    //сабтаск может только быть в рамках эпик
    public List<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        //for (Task task : tasksMap.values()) {// тут не поняла? можно даже не перебирая мапу,
        //сразу все значения перебросить в лист?
        tasks.addAll(tasksMap.values());
        // }
        return tasks;
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


    public void removeById(int id) {
        tasksMap.remove(id);
    }

    public void removeAllTask() {
        tasksMap = new HashMap<>();
    }

    public static int generateId() {
        return id++;
    }


}

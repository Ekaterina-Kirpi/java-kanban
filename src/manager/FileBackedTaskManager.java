package manager;

import exceptions.ManagerSaveException;
import tasks.EpicTask;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    //implements TaskManager
    private File file;

    public FileBackedTaskManager() {
        this.file = new File("list.csv");
        this.tasksMap = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public FileBackedTaskManager(String fileName) {
        this.file = new File(fileName);
    }

    public void save() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("id,type,name,status,description,startTime,duration,endTime,epic\n"); // startTime,duration,endTime
            for (Task task : this.getAllTasks()) {
                writer.write(toString(task) + "\n");
            }
            writer.write("\n");
            writer.write(historyToString(historyManager));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }

    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();
        List<String> stringList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                String line = br.readLine();
                stringList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        for (int i = 1; i < stringList.size() - 2; i++) {
            fileBackedTaskManager.saveNewTask(fromString(stringList.get(i)));
        }
        List<Integer> ids = historyFromString(stringList.get(stringList.size() - 1));
        for (int id : ids) {
            fileBackedTaskManager.historyManager.addTask(fileBackedTaskManager.getTaskById(id));
        }

        return fileBackedTaskManager;
    }

    static Task fromString(String value) {
        String[] values = value.split(",");

        switch (values[1]) {
            case "TASK":
                return new Task(Integer.parseInt(values[0]), values[2], values[4], Status.valueOf(values[3]),
                        values[5].equals("null") ? null : Instant.parse(values[5]), Long.parseLong(values[6]));
            case "SUBTASK":
                return new SubTask(Integer.parseInt(values[0]), values[2], values[4],
                        Status.valueOf(values[3]), Integer.parseInt(values[8]),
                        values[5].equals("null") ? null : Instant.parse(values[5]), Long.parseLong(values[6]));
            case "EPIC":
                return new EpicTask(Integer.parseInt(values[0]), values[2], values[4],
                        Status.valueOf(values[3]));
            default:
                return null;
        }
    }

    static String historyToString(HistoryManager manager) {
        List<Task> tasks = manager.getHistory();
        if (tasks.isEmpty()) return "";
        String value = "";
        for (int i = 0; i < tasks.size() - 1; i++) {
            value += tasks.get(i).getId() + ",";
        }
        return value + tasks.get(tasks.size() - 1).getId();
    }

    static List<Integer> historyFromString(String value) {
        String[] ids = value.split(",");
        List<Integer> tasks = new ArrayList<>();
        for (String id : ids) {
            tasks.add(Integer.parseInt(id));
        }
        return tasks;
    }

    static String toString(Task task) {
        Type thisType;
        if (task instanceof SubTask) {
            thisType = Type.SUBTASK;
        } else if (task instanceof EpicTask) {
            thisType = Type.EPIC;
        } else {
            thisType = Type.TASK;
        }
        String taskString = String.format("%d,%s,%s,%s,%s,%s,%s,%s,", task.getId(), thisType.toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), task.getStartTime() == null ? "null" : task.getStartTime().toString(),
                task.getDuration(), task.getEndTime() == null ? "null" : task.getEndTime().toString());
        return task instanceof SubTask ? taskString + ((SubTask) task).getParentId() : taskString;
    }

    @Override
    public void saveNewTask(Task task) throws ManagerSaveException {
        super.saveNewTask(task);
        save();
    }

    @Override
    public Task getTaskById(int taskId) throws ManagerSaveException {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Task update(Task task) throws ManagerSaveException {
        Task task1 = super.update(task);
        save();
        return task1;
    }

    @Override
    public List<Task> getAllTasks() {
       /* List<Task> listTask = super.getAllTasks();
        save();*/
        return super.getAllTasks();
    }

    @Override
    public void removeById(int id) throws ManagerSaveException {
        super.removeById(id);
        save();
    }

    @Override
    public void removeAllTask() throws ManagerSaveException {
        super.removeAllTask();
        save();
    }

    @Override
    public List<EpicTask> getAllEpicTasks() throws ManagerSaveException {
        List<EpicTask> listEpicTask = super.getAllEpicTasks();
        save();
        return listEpicTask;
    }

    @Override
    public List<SubTask> getSubTasksFromEpic(int id) throws ManagerSaveException {
        List<SubTask> listSubTask = super.getSubTasksFromEpic(id);
        save();
        return listSubTask;
    }

    @Override
    public HistoryManager getHistory() throws ManagerSaveException {
        HistoryManager historyManager1 = super.getHistory();
        save();
        return historyManager1;
    }

    @Override
    public void setHistoryManager(HistoryManager historyManager) {
        super.setHistoryManager(historyManager);
    }


}


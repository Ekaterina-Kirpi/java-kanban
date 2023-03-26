package server;

import com.google.gson.*;
import manager.FileBackedTaskManager;
import manager.HistoryManager;
import manager.TaskManager;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;
//написать HttpTaskManager и соединить с KVTaskClient

public class HttpTaskManager extends FileBackedTaskManager {
    private static final String KEY_TASK = "tasks";
    private static final String KEY_SUBTASK = "subtasks";
    private static final String KEY_EPIC = "epics";
    private static final String KEY_HISTORY = "history";

    private static KVTaskClient client;
    private static final Gson gson =
            new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();

    public HttpTaskManager(HistoryManager historyManager, String path) throws IOException, InterruptedException {
        super(historyManager);
        client = new KVTaskClient(path);

        JsonElement jsonTasks = JsonParser.parseString(client.load(KEY_TASK));
        if (!jsonTasks.isJsonNull()) {
            JsonArray jsonTasksArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonTask : jsonTasksArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                this.saveNewTask(task);
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(client.load(KEY_EPIC));
        if (!jsonEpics.isJsonNull()) {
            JsonArray jsonEpicsArray = jsonEpics.getAsJsonArray();
            for (JsonElement jsonEpic : jsonEpicsArray) {
                EpicTask task = gson.fromJson(jsonEpic, EpicTask.class);
                this.saveNewTask(task);
            }
        }

        JsonElement jsonSubtasks = JsonParser.parseString(client.load(KEY_SUBTASK));
        if (!jsonSubtasks.isJsonNull()) {
            JsonArray jsonSubtasksArray = jsonSubtasks.getAsJsonArray();
            for (JsonElement jsonSubtask : jsonSubtasksArray) {
                SubTask task = gson.fromJson(jsonSubtask, SubTask.class);
                this.saveNewTask(task);
            }
        }

        JsonElement jsonHistoryList = JsonParser.parseString(client.load(KEY_HISTORY));
        if (!jsonHistoryList.isJsonNull()) {
            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();
            for (JsonElement jsonTaskId : jsonHistoryArray) {
                int taskId = jsonTaskId.getAsInt();
                if (this.getAllEpicTasks().stream()
                        .flatMap(task -> task.getSubTasks().stream()).anyMatch(task -> task.getId() == taskId)) {
                    this.getTaskById(taskId);
                } else if (this.getAllEpicTasks().stream().anyMatch(task -> task.getId() == taskId)) {
                    this.getTaskById(taskId);
                } else if (this.getAllTasks().stream().anyMatch(task -> task.getId() == taskId)) {
                    this.getTaskById(taskId);
                }
            }
        }
    }

    @Override
    public void save() {
        client.put(KEY_TASK, gson.toJson(tasksMap.values()));
        client.put(KEY_SUBTASK, gson.toJson(tasksMap.values().stream()
                .filter(task -> task.getClass() == EpicTask.class)
                .flatMap(task -> ((EpicTask) task).getSubTasks().stream()).collect(Collectors.toList())));
        client.put(KEY_EPIC, gson.toJson(tasksMap.values().stream()
                .filter(task -> task.getClass() == EpicTask.class).collect(Collectors.toList())));
        client.put(KEY_HISTORY, gson.toJson(this.getHistoryManager().getHistoryList()
                .stream()
                .map(Task::getId)
                .collect(Collectors.toList())));
    }
}
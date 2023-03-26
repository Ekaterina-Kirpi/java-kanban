package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskManager;
import server.HttpTaskServer;
import server.InstantAdapter;
import server.KVServer;
import tasks.EpicTask;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskManagerTest {
    private static TaskManager manager;
    private static KVServer kvServer;

    @BeforeEach
    public void start() {
        try {
            kvServer = new KVServer();
            kvServer.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            manager = new HttpTaskManager(historyManager, "http://localhost:8078");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void loadTasksTest() {
        Task task1 = new Task("description1", "name1", Status.NEW, Instant.now(), 1);
        Task task2 = new Task("description2", "name2", Status.NEW, Instant.now().plusSeconds(240), 2);
        manager.saveNewTask(task1);
        manager.saveNewTask(task2);
        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
        List<Task> list = manager.getHistoryManager().getHistoryList();
        assertEquals(manager.getAllTasks(), list);
    }

    @Test
    public void loadEpicsTest() {
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");
        Instant time2 = Instant.parse("2023-03-23T12:40:00.00Z");
        EpicTask epic1 = new EpicTask("description1", "name1", Status.NEW);
        EpicTask epic2 = new EpicTask("description2", "name2", Status.NEW);
        SubTask subtask1 = new SubTask("description1", "name1", Status.NEW, time,6);
        SubTask subtask2 = new SubTask("description2", "name2", Status.NEW,
                time2, 20);
        epic1.saveNewSubTask(subtask1);
        epic2.saveNewSubTask(subtask2);


        manager.saveNewTask(epic1);
        manager.saveNewTask(epic2);
        manager.getTaskById(epic1.getId());
        manager.getTaskById(epic2.getId());
        List<Task> list = manager.getHistoryManager().getHistoryList();
        assertEquals(manager.getAllTasks(), list);
    }

    @Test
    public void loadSubtasksTest() {
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");
        Instant time2 = Instant.parse("2023-03-23T13:00:00.00Z");
        EpicTask epic1 = new EpicTask("description1", "name1", Status.NEW);
        SubTask subtask1 = new SubTask("description1", "name1", Status.NEW, time,10);
        SubTask subtask2 = new SubTask("description2", "name2", Status.NEW,
                time2, 20);
        epic1.saveNewSubTask(subtask1);
        epic1.saveNewSubTask(subtask2);


        manager.saveNewTask(subtask1);
        manager.getTaskById(subtask1.getId());
        manager.saveNewTask(subtask2);
        manager.getTaskById(subtask2.getId());
        manager.saveNewTask(epic1);
        List<Task> list = manager.getHistoryManager().getHistoryList();
        assertEquals(manager.getAllTasks().stream().filter(task -> task.getClass() == SubTask.class).collect(Collectors.toList()), list);
    }

    @AfterEach
    public void stopServer() {
        kvServer.stop();
    }
}
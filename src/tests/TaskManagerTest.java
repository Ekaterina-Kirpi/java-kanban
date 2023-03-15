package tests;

import manager.TaskManager;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;
import tasks.Status;
import tasks.Task;
import java.time.Instant;


public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    public TaskManagerTest(T taskManager) {
        this.taskManager = taskManager;
    }

    @Test
    public void saveNewTaskTest() {
        Task task = new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
    }
    @Test
    public void getTaskByIdTest() {
        Task task = new Task(0,"Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        Assertions.assertEquals(task, taskManager.getTaskById(0));
    }
    @Test
    public void updateTest() {
        Task task = new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        Task taskNew = new Task(0, "Другой", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.update(taskNew);
        Assertions.assertEquals(taskNew, taskManager.getTaskById(0));
    }
    @Test
    public void getAllTasksTest() {
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }
    @Test
    public void removeByIdTest() {
        Task task = new Task(0, "Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        taskManager.removeById(0);
        Assertions.assertNull(taskManager.getTaskById(0));
    }
    @Test
    public void removeAllTaskTest() {
        Task task = new Task(0, "Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        taskManager.removeAllTask();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }
    @Test
    public void getHistoryTest() {
        Task task = new Task(0, "Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        taskManager.getTaskById(0);
        Assertions.assertNotNull(taskManager.getHistory());
    }
}

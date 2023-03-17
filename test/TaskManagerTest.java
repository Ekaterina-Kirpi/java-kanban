import exceptions.ManagerValidateException;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @Test
    public void saveNewTaskTest() {
        Task task = new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
    }

    @Test
    public void saveFewTasksTest() {
        Task task = new Task("Test", "Test", Status.DONE, Instant.now(), 30);
        EpicTask task2 = new EpicTask("Test", "Test", Status.DONE);
        SubTask task3 = new SubTask("Test", "Test", Status.DONE, Instant.now().plusSeconds(2400L), 30);
        taskManager.saveNewTask(task);
        task2.saveNewSubTask(task3);
        taskManager.saveNewTask(task2);
        taskManager.saveNewTask(task3);
    }


    @Test
    public void saveNewTaskExceptionTest() {
        Task task = new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        Task task2 = new Task("Побегать", "Часовая пробежка", Status.DONE,
                Instant.now().plusSeconds(600L), 30);
        taskManager.saveNewTask(task);
        Assertions.assertThrows(ManagerValidateException.class, () -> taskManager.saveNewTask(task2));
    }

    @Test
    public void getTaskByIdTest() {
        Task task = new Task(0, "Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        Assertions.assertEquals(task, taskManager.getTaskById(0));
    }

    @Test
    public void getTaskByIdNullTest() {
        Task task = new Task(0, "Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        Assertions.assertNull(taskManager.getTaskById(5));

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
    public void getAllTasksEmptyTest() {
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void getAllTasksTest() {
        List<Task> list = new ArrayList<>() {
            {
                add(new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30));
                add(new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now().plusSeconds(2400L), 30));
            }
        };
        for (Task task : list) {
            taskManager.saveNewTask(task);
        }
        Assertions.assertEquals(list, taskManager.getAllTasks());
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
        Assertions.assertEquals(1, taskManager.getHistoryManager().getHistoryList().size());
    }

    @Test
    public void getHistoryEmptyTest() {
        Assertions.assertTrue(taskManager.getHistoryManager().getHistoryList().isEmpty());
    }
}
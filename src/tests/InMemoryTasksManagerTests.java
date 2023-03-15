package tests;

import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.time.Instant;

/*class InMemoryTasksManagerTests extends TaskManagerTest<InMemoryTaskManager> {
    public InMemoryTasksManagerTests(InMemoryTaskManager taskManager) {
        super(taskManager);
    }

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
    }
}

*/
public class InMemoryTasksManagerTests extends TaskManagerTest<InMemoryTaskManager> {


    public InMemoryTasksManagerTests() {
        super(new InMemoryTaskManager());
    }

    @Test
    public void saveNewTaskTest() {
        super.saveNewTaskTest();
    }
    @Test
    public void getTaskByIdTest() {
        super.getTaskByIdTest();
    }
    @Test
    public void updateTest() {
        super.updateTest();
    }
    @Test
    public void getAllTasksTest() {
        super.getAllTasksTest();

    }
    @Test
    public void removeByIdTest() {
        super.removeByIdTest();

    }
    @Test
    public void removeAllTaskTest() {
        super.removeAllTaskTest();

    }
    @Test
    public void getHistoryTest() {
        super.getHistoryTest();
    }
}

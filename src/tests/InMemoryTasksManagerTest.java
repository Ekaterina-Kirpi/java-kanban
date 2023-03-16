package tests;

import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void initTaskManager() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }
}
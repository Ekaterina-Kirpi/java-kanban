package tests;

import exceptions.ManagerSaveException;
import manager.FileBackedTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.io.File;
import java.util.List;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    @BeforeEach
    void initTaskManager() {
        taskManager = (FileBackedTaskManager) Managers.getFileBacked();
    }

    @Test
    public void testEmptySave() {
        taskManager.removeAllTask();
        taskManager.save();
        taskManager = FileBackedTaskManager.loadFromFile(taskManager.getFile());
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void testSave() {
        List<Task> taskList = taskManager.getAllTasks();
        taskManager.save();
        taskManager = FileBackedTaskManager.loadFromFile(taskManager.getFile());
        Assertions.assertEquals(taskList, taskManager.getAllTasks());
    }

    @Test
    public void testSaveException() {
        taskManager.setFile(new File("afds.djhj"));
        Assertions.assertThrows(ManagerSaveException.class, () -> {
            taskManager.save();
        });
    }
}

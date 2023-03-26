package manager;

import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagerTest {
    HistoryManager historyManager;
    Task task1;
    Task task2;
    Task task3;

    //Task(String name, String description, Status status, Instant startTime, long duration)
    @BeforeEach
    private void beforeEach() {
        historyManager = Managers.getDefaultHistory();
        task1 = new Task("1", "что-то там1", Status.NEW, Instant.now(), 10);
        task1.setId(1);

        task2 = new Task("2", "что-то там2", Status.NEW, Instant.now().plusSeconds(1500L), 10);
        task2.setId(2);

        task3 = new Task("3", "что-то там3", Status.NEW, Instant.now().plusSeconds(1500L), 10);
        task3.setId(3);
    }

    @Test
    public void shouldBeEmptyListOfSubtasks() {
        assertEquals(0, historyManager.getHistoryList().size());
    }

    @Test
    public void shouldNotHaveDoubles() {
        historyManager.addTask(task1);
        historyManager.addTask(task1);

        assertEquals(1, historyManager.getHistoryList().size());
    }

    @Test
    public void shouldBeDeletedFromTheStart() {
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.remove(task1.getId());
        assertEquals(2, historyManager.getHistoryList().size());
    }

    @Test
    public void shouldBeDeletedFromTheMiddle() {
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.remove(task2.getId());
        assertEquals(2, historyManager.getHistoryList().size());
    }

    @Test

    public void shouldBeDeletedFromTheEnd() {
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.remove(task3.getId());
        assertEquals(2, historyManager.getHistoryList().size());
    }

}

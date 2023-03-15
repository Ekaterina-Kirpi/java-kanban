package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTests {
    @Test
    public void testEmptyEpicTask() {
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        Assertions.assertEquals(Status.NEW, epicTask.calcStatus());
    }
    @Test
    public void testAllNewEpicTask() {
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.NEW, Instant.now().plusSeconds(2400L), 30);
        SubTask subTask2 = new SubTask("Другой", "Еще", Status.NEW, Instant.now().plusSeconds(4400L), 30);
        epicTask.saveNewSubTask(subTask);
        epicTask.saveNewSubTask(subTask2);
        Assertions.assertEquals(Status.NEW, epicTask.calcStatus());
    }
    @Test
    public void testAllDoneEpicTask() {
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.DONE, Instant.now().plusSeconds(2400L), 30);
        SubTask subTask2 = new SubTask("Другой", "Еще", Status.DONE, Instant.now().plusSeconds(4400L), 30);
        epicTask.saveNewSubTask(subTask);
        epicTask.saveNewSubTask(subTask2);
        Assertions.assertEquals(Status.DONE, epicTask.calcStatus());
    }
    @Test
    public void testNewAndDoneEpicTask() {
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.DONE, Instant.now().plusSeconds(2400L), 30);
        SubTask subTask2 = new SubTask("Другой", "Еще", Status.NEW, Instant.now().plusSeconds(4400L), 30);
        epicTask.saveNewSubTask(subTask);
        epicTask.saveNewSubTask(subTask2);
        Assertions.assertEquals(Status.IN_PROGRESS, epicTask.calcStatus());
    }
    @Test
    public void testAllInProgressEpicTask() {
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.IN_PROGRESS, Instant.now().plusSeconds(2400L), 30);
        SubTask subTask2 = new SubTask("Другой", "Еще", Status.IN_PROGRESS, Instant.now().plusSeconds(4400L), 30);
        epicTask.saveNewSubTask(subTask);
        epicTask.saveNewSubTask(subTask2);
        Assertions.assertEquals(Status.IN_PROGRESS, epicTask.calcStatus());
    }

}

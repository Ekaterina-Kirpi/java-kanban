import manager.FileBackedTaskManager;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import tasks.EpicTask;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        // TaskManager taskManager = Managers.getDefault();
        // HistoryManager inMemoryHistoryManager = taskManager.getHistory();

        InMemoryTaskManager taskManager = new FileBackedTaskManager("list.csv");

        Task task2 = new Task("Что-то там", "Где-то там", Status.DONE);
        taskManager.saveNewTask(task2);

        Task task = new Task("Побегать", "Часовая пробежка", Status.DONE, Instant.now(), 30);
        taskManager.saveNewTask(task);
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);

        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.DONE, Instant.now().plusSeconds(2400L), 30);
        epicTask.saveNewSubTask(subTask);
        taskManager.saveNewTask(subTask);

        SubTask subTask1 = new SubTask("Сделать гравировку на подарке", "Найти кто делает лазерную гравировку", Status.NEW, Instant.now().plusSeconds(5000L), 30);
        taskManager.saveNewTask(subTask1);
        epicTask.saveNewSubTask(subTask1);

        taskManager.saveNewTask(epicTask);


        System.out.println(taskManager.getPrioritizedTasks());
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        System.out.println(taskManager.getTaskById(epicTask.getId()) + "\n");
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        System.out.println(taskManager.getTaskById(subTask.getId()) + "\n");
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        System.out.println(taskManager.getTaskById(task.getId()) + "\n");
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        System.out.println(taskManager.getTaskById(epicTask.getId()) + "\n");
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        System.out.println(taskManager.getTaskById(task.getId()) + "\n");
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        //System.out.println(taskManager.getHistory().getHistory() + "\n");
        TaskManager fileBackedTasksManager = FileBackedTaskManager.loadFromFile(new File("list.csv"));
        //System.out.println(fileBackedTasksManager.getAllTasks() + "\n");
        System.out.println(fileBackedTasksManager.getHistory().getHistory() + "\n");
        System.out.println(epicTask);
        taskManager.removeById(2);
        System.out.println(fileBackedTasksManager.getTaskById(2));

        System.out.println(taskManager.getHistory().getHistory());//удаляет из истории эпик и его сабтаски.


    }
}
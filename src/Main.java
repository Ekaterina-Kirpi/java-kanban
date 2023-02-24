import manager.*;
import tasks.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        // TaskManager taskManager = Managers.getDefault();
        // HistoryManager inMemoryHistoryManager = taskManager.getHistory();

        TaskManager taskManager = new FileBackedTaskManager("list.csv");
        Task task = new Task("Побегать", "Часовая пробежка", Status.DONE);
        taskManager.saveNewTask(task);
        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        taskManager.saveNewTask(epicTask);

        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.DONE);
        epicTask.saveNewSubTask(subTask);
        taskManager.saveNewTask(subTask);

        SubTask subTask1 = new SubTask("Сделать гравировку на подарке", "Найти кто делает лазерную гравировку", Status.NEW);
        taskManager.saveNewTask(subTask1);
        epicTask.saveNewSubTask(subTask1);
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
        System.out.println(taskManager.getHistory().getHistory() + "\n");
        TaskManager fileBackedTasksManager = FileBackedTaskManager.loadFromFile(new File("list.csv"));
        System.out.println(fileBackedTasksManager.getAllTasks() + "\n");
        System.out.println(fileBackedTasksManager.getHistory().getHistory() + "\n");


    }
}
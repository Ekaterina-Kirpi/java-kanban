import menager.*;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager inMemoryHistoryManager = taskManager.getHistory();
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
        System.out.println(inMemoryHistoryManager.getHistory());
        System.out.println(taskManager.getTaskById(epicTask.getId()));
        System.out.println(inMemoryHistoryManager.getHistory());
        System.out.println(taskManager.getTaskById(subTask.getId()));
        System.out.println(inMemoryHistoryManager.getHistory());
        System.out.println(taskManager.getTaskById(task.getId()));
        System.out.println(inMemoryHistoryManager.getHistory());

    }
}
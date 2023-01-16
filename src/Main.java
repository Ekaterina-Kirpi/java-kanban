import menager.TaskMenager;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        TaskMenager taskMenager = new TaskMenager();

        Task task = new Task("Побегать", "Часовая пробежка", Status.NEW);
        taskMenager.saveNewTask(task);
        System.out.println(task.getId());
        System.out.println(taskMenager.getTaskById(1));
        taskMenager.update(task, 1);
        System.out.println(task);
        Task task1 = new Task("Купить фрукты", "Зайти в магазин", Status.NEW);
        taskMenager.saveNewTask(task1);

        EpicTask epicTask = new EpicTask("Подготовится к ДР", "Идея, подарок, упаковка, встреча", Status.NEW);
        taskMenager.saveNewTask(epicTask);

        SubTask subTask = new SubTask("Выбрать подарок", "Поехать купить", Status.DONE);
        epicTask.saveNewSubTask(subTask);
        taskMenager.saveNewTask(subTask);

        SubTask subTask1 = new SubTask("Сделать гравировку на подарке", "Найти кто делает лазерную гравировку", Status.NEW);
        taskMenager.saveNewTask(subTask1);
        epicTask.saveNewSubTask(subTask1);
        System.out.println(subTask.getId());
        System.out.println(epicTask);
        System.out.println(epicTask.getId());
        System.out.println(epicTask.calcStatus());
        EpicTask epicTask1 = new EpicTask("Выспаться", "Лечь спать до 22ч", Status.NEW);
        taskMenager.saveNewTask(epicTask1);
        System.out.println(taskMenager.getAllTasks());



    }
}

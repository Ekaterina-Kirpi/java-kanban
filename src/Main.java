
public class Main {

    public static void main(String[] args) {
        TaskMenager taskMenager = new TaskMenager();

        SingleTask singleTask = new SingleTask("Побегать", "Часовая пробежка", Status.NEW);
        taskMenager.saveNewTask(singleTask);
        System.out.println(singleTask.getId());
        System.out.println(taskMenager.getTaskById(1));
        taskMenager.update(singleTask, 1);
        System.out.println(singleTask);
        SingleTask singleTask1 = new SingleTask("Купить фрукты", "Зайти в магазин", Status.NEW);
        taskMenager.saveNewTask(singleTask1);

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

    }
}

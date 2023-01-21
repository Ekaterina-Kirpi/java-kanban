package tasks;

public class SubTask extends Task {
    private EpicTask epicTask;
    private int parentId;

    public SubTask(String name, String description, Status status, EpicTask epicTask, int parentId) {
        super(name, description, status);
        this.epicTask = epicTask;
        this.parentId = parentId;
    }

    public SubTask(String name, String description, Status status, int parentId) {
        super(name, description, status);
        this.parentId = parentId;
    }

    public SubTask(String name, String description, Status status) {
        super(name, description, status);
    }

    public SubTask(String name, String description) {
        super(name, description, Status.NEW);
    }
    public int getParentId() {
        return epicTask.getId();
    }


    @Override
    public Status calcStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() + "," +
                "name=" + getName() + "," +
                "description=" + getDescription() + "," +
                "status=" + getStatus() +
                ", parentId=" + parentId +
                '}';
    }
}

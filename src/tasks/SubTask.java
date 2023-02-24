package tasks;

public class SubTask extends Task {
    private EpicTask epicTask;
    private int parentId;

    public SubTask(int id, String name, String description, Status status, EpicTask epicTask, int parentId) {
        super(id, name, description, status);
        this.epicTask = epicTask;
        this.parentId = parentId;
    }

    public SubTask(String name, String description, Status status, EpicTask epicTask, int parentId) {
        super(name, description, status);
        this.epicTask = epicTask;
        this.parentId = parentId;
    }

    public SubTask(int id, String name, String description, Status status, int parentId) {
        super(id, name, description, status);
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


    @Override
    public Status calcStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + status +
                "epicTask=" + epicTask +
                ", parentId=" + parentId +
                ", status=" + status +
                '}';
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
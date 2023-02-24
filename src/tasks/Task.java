package tasks;

import manager.InMemoryTaskManager;
import manager.Type;

public class Task {
    private String name;
    private String description;
    private int id;
    protected Status status;

    public Task(int id ,String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;// будет генерироваться для всех
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = InMemoryTaskManager.generateId();// будет генерироваться для всех
    }

    public Task(String name) {
        this.name = name;
    }

    public Status calcStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {

        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //public void setId(int id) {
    //   this.id = id;
    //}

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
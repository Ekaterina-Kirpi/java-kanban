package tasks;

import manager.InMemoryTaskManager;

import java.time.Instant;
import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    protected Status status;
    private Instant startTime;
    private long duration = 0;
    //нужно добавить endTime, как правильно, чтобы это поле так же записывалось в файл?

    public Task(int id, String name, String description, Status status, Instant startTime, long duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, Status status, Instant startTime, long duration) {
        this(InMemoryTaskManager.generateId(), name, description, status, startTime, duration);
    }

    public Task(int id, String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;// будет генерироваться для всех
    }

    public Task(String name, String description, Status status) {
        this(InMemoryTaskManager.generateId(), name, description, status);// будет генерироваться для всех
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

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Instant getEndTime() {
        if (startTime == null) return null;
        long SECOND_IN_MINUTE = 60L;
        return startTime.plusSeconds(duration * SECOND_IN_MINUTE);
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + getEndTime() + '\'' +
                ", duration='" + duration +
                '}';
    }
}
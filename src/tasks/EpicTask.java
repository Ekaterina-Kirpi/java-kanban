package tasks;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;


public class EpicTask extends Task {
    private final List<SubTask> subTasks;

    public EpicTask(int id, String name, String description, Status status) {
        super(id, name, description, Status.NEW);
        this.subTasks = new ArrayList<>();
    }

    public EpicTask(String name, String description, Status status) {
        super(name, description, Status.NEW);
        this.subTasks = new ArrayList<>();
    }

    public void saveNewSubTask(SubTask subTask) {
        subTask.setParentId(getId());
        subTasks.add(subTask);
    }


    @Override
    public Status calcStatus() {
        if (subTasks.size() == 0) {
            return Status.NEW;
        }
        int done = 0;
        int newStat = 0;
        for (SubTask status : subTasks) {
            if (status.getStatus() == Status.DONE) {
                done++;
            } else if (status.getStatus() == Status.NEW) {
                newStat++;
            }
        }
        if (subTasks.size() == done) {
            return Status.DONE;
        } else if (subTasks.size() == newStat) {
            return Status.NEW;
        } else {
            return Status.IN_PROGRESS;
        }

    }


    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "subTasks=" + subTasks +
                ", status=" + status +
                '}';
    }
}
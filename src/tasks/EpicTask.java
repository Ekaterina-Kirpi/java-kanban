package tasks;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class EpicTask extends Task {
    private final List<SubTask> subTasks;
    private Instant endTime;

    public EpicTask(int id, String name, String description, Status status) {
        super(id, name, description, Status.NEW);
        this.subTasks = new ArrayList<>();
        calculateTime();
    }

    public EpicTask(String name, String description, Status status) {
        super(name, description, Status.NEW);
        this.subTasks = new ArrayList<>();
        calculateTime();

    }

    /*public EpicTask(String name, String description, Status status, Instant startTime) {
        super(name, description, status, startTime, getDuration());
        this.subTasks = new ArrayList<>();
        this.endTime = super.getEndTime();
    }*/


    public void saveNewSubTask(SubTask subTask) {
        calculateTime();
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

    public void calculateTime() { // расчитать время эпика из расчета подзадач
        if (subTasks.size() == 0) {
            return;
        }
        Instant min = subTasks.get(0).getStartTime();
        Instant max = subTasks.get(0).getStartTime();
        for (int i = 1; i < subTasks.size(); i++) {
            if (subTasks.get(i).getStartTime() != null && subTasks.get(i).getStartTime().isBefore(min)) {
                min = subTasks.get(i).getStartTime();
            } else if (subTasks.get(i).getStartTime() != null && subTasks.get(i).getStartTime().isAfter(max)) {
                max = subTasks.get(i).getStartTime();
            }
        }
        endTime = max;
        setStartTime(min);
    }

    public long getDuration() {
        if (subTasks.size() == 0) return 0;
        long sum = 0;
        for (int i = 1; i < subTasks.size(); i++) {
            sum += subTasks.get(i).getDuration();
        }
        return sum;
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public Instant getStartTime() {
        return super.getStartTime();
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return subTasks.equals(epicTask.subTasks) && endTime.equals(epicTask.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks, endTime);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "subTasks=" + subTasks +
                ", id=" + getId() +
                ", status=" + status +
                ", startTime='" + getStartTime() + '\'' +
                ", endTime='" + getEndTime() + '\'' +
                ", duration='" + getDuration() +
                '}';

    }

}
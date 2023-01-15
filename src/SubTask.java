public class SubTask extends Task{

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
}


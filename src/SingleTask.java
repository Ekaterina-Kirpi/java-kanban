public class SingleTask extends Task {
    public SingleTask(String name, String description, Status status) {
        super(name, description, status);
    }

    public SingleTask(String name, String description) {
        super(name, description, Status.NEW);
    }

    @Override
    public Status calcStatus() {
        return status;

    }
}

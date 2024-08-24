public class Todo extends Task {

    public Todo(boolean isDone, String description) {
        super(isDone, description);
    }

    @Override
    public String fileFormat() {
        return "TODO," + super.fileFormat();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

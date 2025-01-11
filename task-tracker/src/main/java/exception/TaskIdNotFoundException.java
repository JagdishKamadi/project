package main.java.exception;

public class TaskIdNotFoundException extends RuntimeException {

    public TaskIdNotFoundException() {
        super("Task not found for this id");
    }

    public TaskIdNotFoundException(Integer id) {
        super("Task not found for this id " + id);
    }
}

package assignment.exceptions;

public class FieldRequiredException extends Exception {

    String field;

    public FieldRequiredException(String field) {
        super(String.format("'%s' field cannot be blank!", field));
    }

    public FieldRequiredException() {
        super("There are missing fields.");
    }
}

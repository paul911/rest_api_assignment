package assignment.exceptions;

public class InvalidEmailFormatException extends Exception {
    private String emailAddress;

    public InvalidEmailFormatException(String emailAddress) {
        super(String.format("'%s' is not a valid email address.", emailAddress));
    }
}

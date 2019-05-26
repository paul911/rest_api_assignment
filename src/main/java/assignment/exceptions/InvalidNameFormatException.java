package assignment.exceptions;

public class InvalidNameFormatException extends Exception {

    private String providedName;

    public InvalidNameFormatException(String providedName) {
        super(String.format("Name provided '%s' does not meet the requirements. Please provide a valid full name, " +
                "in the format 'Firstname Lastname'." , providedName));
    }
}

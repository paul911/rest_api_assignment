package assignment.exceptions;

public class InvalidFormatException extends Exception {

    private String input;
    private String pattern;
    private String example;
    private String maxLength;

    public InvalidFormatException(String field, String formatExample) {
        super(String.format("Invalid format for the '%s' field. Please provide valid input (Example: %s)", field, formatExample));
    }

    public static void assertValidityOfInput(String input, String field, int maxLength, String pattern, String example) throws FieldRequiredException, InvalidFormatException {
        if (input.length() == 0)
            throw new FieldRequiredException(field);
        if (!(input.matches(pattern)) || (input.length() >= maxLength)) {
            throw new InvalidFormatException(field, example);
        }
    }
}

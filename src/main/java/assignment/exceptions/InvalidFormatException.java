package assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidFormatException extends RuntimeException {

    private InvalidFormatException(String field, String formatExample) {
        super(String.format("Invalid format for the '%s' field. Please provide valid input (Example: %s)", field, formatExample));
    }

    public static void assertValidityOfInput(String input, String field, int maxLength, String pattern, String example) {
        if (input.length() == 0)
            throw new FieldRequiredException(field);
        if (!(input.matches(pattern)) || (input.length() >= maxLength)) {
            throw new InvalidFormatException(field, example);
        }
    }
}

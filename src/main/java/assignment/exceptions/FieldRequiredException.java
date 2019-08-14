package assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class FieldRequiredException extends RuntimeException {

    String field;

     FieldRequiredException(String field) {
        super(String.format("'%s' field cannot be blank!", field));
    }

    public FieldRequiredException() {
        super("There are missing fields.");
    }
}

package assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BuyerAlreadyExistsException extends RuntimeException {
    private int buyerId;

    public BuyerAlreadyExistsException(String fullName) {
        super(String.format("Buyer '%s' is already present in the database.", fullName));
    }

    public BuyerAlreadyExistsException() {
        super("Identification Number already taken.");
    }
}

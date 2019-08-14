package assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BuyerNotFoundException extends RuntimeException {
    private int buyerId;

    public BuyerNotFoundException(int buyerId) {
        super(String.format("Buyer is not found with id : '%s'", buyerId));
    }
    public BuyerNotFoundException(String fullName) {
        super(String.format("Buyer '%s' is not found in the database.", fullName));
    }
    public BuyerNotFoundException() {
        super("No buyers found.");
    }
}

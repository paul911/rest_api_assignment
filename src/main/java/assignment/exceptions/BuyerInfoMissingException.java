package assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BuyerInfoMissingException extends RuntimeException {
    public BuyerInfoMissingException(String name) {
        super(String.format("Buyer '%s' does not contain an address or phone number." +
                " Add a valid address and phone number in order to place transaction.", name));

    }
}

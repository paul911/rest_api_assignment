package assignment.exceptions;


public class BuyerAlreadyExistsException extends Exception {
    private int buyerId;

    public BuyerAlreadyExistsException(String fullName) {
        super(String.format("Buyer '%s' is already present in the database.", fullName));
    }

    public BuyerAlreadyExistsException() {
        super("Identification Number already taken.");
    }
}

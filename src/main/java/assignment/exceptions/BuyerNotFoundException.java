package assignment.exceptions;


public class BuyerNotFoundException extends Exception {
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

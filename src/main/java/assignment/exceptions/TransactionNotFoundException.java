package assignment.exceptions;


public class TransactionNotFoundException extends Exception {
    private Integer transactionId;

    public TransactionNotFoundException(Integer transactionId) {
        super(String.format("Transaction is not found with id : '%s'", transactionId));
    }
}

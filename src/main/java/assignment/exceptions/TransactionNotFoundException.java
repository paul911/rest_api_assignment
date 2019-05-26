package assignment.exceptions;


public class TransactionNotFoundException extends Exception {
    private Integer transactionId;

    public TransactionNotFoundException(Integer transactionId) {
        super(String.format("Transaction is not found with id : '%s'", transactionId));
    }
    public TransactionNotFoundException(String buyerName) {
        super(String.format("No transactions found for buyer : '%s'", buyerName));
    }

    public TransactionNotFoundException() {
        super("No transactions found.");
    }
}

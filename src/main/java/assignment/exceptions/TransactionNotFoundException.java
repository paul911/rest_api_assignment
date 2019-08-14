package assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {
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

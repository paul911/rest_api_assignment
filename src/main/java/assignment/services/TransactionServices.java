package assignment.services;

import assignment.DTOs.TransactionDTO;
import assignment.entities.Buyer;
import assignment.entities.Transaction;
import assignment.exceptions.BuyerInfoMissingException;
import assignment.exceptions.TransactionNotFoundException;
import assignment.repositories.BuyersRepository;
import assignment.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServices {

    @Autowired
    private BuyersRepository buyersRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private BuyerServices buyers;

    public Transaction getTransactionById(int id) {
        return transactionsRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public List<Transaction> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public List<Transaction> getTransactionsByPurchaseDate(String date) {
        List<Transaction> transactionsByDate;
        if (!(transactionsByDate = transactionsRepository.findByPurchaseDateContaining(date)).isEmpty())
            return transactionsByDate;
        else throw new TransactionNotFoundException();
    }

    public boolean transactionWithIdAlreadyExists(int id) {
        try {
            getTransactionById(id);
            return true;
        } catch (TransactionNotFoundException tnfe) {
            return false;
        }
    }

    public Transaction updateTransactionWithId(int id, TransactionDTO newTransaction) {
        Transaction oldTransaction = getTransactionById(id);
        oldTransaction.setTransactionValue(newTransaction.getValue());
        oldTransaction.setTransactionDescription(newTransaction.getDescription());
        Buyer transactionOwner = buyers.findBuyerById(newTransaction.getBuyer());
        oldTransaction.assignToBuyer(transactionOwner);
        transactionsRepository.save(oldTransaction);
        return oldTransaction;
    }

    public Transaction createNewTransaction(TransactionDTO transactionDTO) {
        Buyer transactionOwner = buyers.findBuyerById(transactionDTO.getBuyer());

        if (transactionOwner.getAddress() == null || transactionOwner.getPhone() == null)
            throw new BuyerInfoMissingException(transactionOwner.getName());

        Transaction newTransaction = new Transaction(transactionOwner, transactionDTO.getValue(), transactionDTO.getDescription());

        transactionOwner.addTransaction(newTransaction);
        buyersRepository.save(transactionOwner);
        return transactionsRepository.save(newTransaction);
    }

    public void deleteTransaction(Integer transactionId) {
        Transaction transactionToDelete = getTransactionById(transactionId);
        Buyer transactionOwner = buyers.findBuyerById(transactionToDelete.getBuyerId());

        // removes the transaction from the buyer
        transactionOwner.removeTransaction(transactionToDelete);
        buyersRepository.save(transactionOwner);

        transactionsRepository.deleteById(transactionId);
    }

    public TransactionDTO getTransactionInfo(Transaction transaction) {
        TransactionDTO transactionInfo = new TransactionDTO();
        transactionInfo.setBuyer(transaction.getBuyerId());
        transactionInfo.setDescription(transaction.getDescription());
        transactionInfo.setValue(transaction.getTransactionValue());
        transactionInfo.setId(transaction.getOrderNumber());

        return transactionInfo;
    }

    public List<TransactionDTO> getTransactionsInfo(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::getTransactionInfo)
                .collect(Collectors.toList());
    }
}

package assignment.controllers;


import assignment.entities.Buyer;
import assignment.entities.Transaction;
import assignment.exceptions.BuyerNotFoundException;
import assignment.exceptions.FieldRequiredException;
import assignment.exceptions.InvalidFormatException;
import assignment.exceptions.TransactionNotFoundException;
import assignment.repositories.BuyersRepository;
import assignment.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionsController {

    private static final String transactionsPath = "/transactions";

    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private BuyersRepository buyersRepository;

    @GetMapping(TransactionsController.transactionsPath)
    public List<Transaction> index() {
        return transactionsRepository.findAll();
    }

    @GetMapping(TransactionsController.transactionsPath + "/{id}")
    public Transaction show(@PathVariable String id) throws TransactionNotFoundException {
        Integer orderNumber = Integer.parseInt(id);
        return transactionsRepository.findById(orderNumber).orElseThrow(() -> new TransactionNotFoundException(orderNumber));
    }

    @GetMapping(TransactionsController.transactionsPath + "/date/{purchaseDate}")
    public List<Transaction> findByDate(@PathVariable String date) throws TransactionNotFoundException {
        List<Transaction> transactionsByDate;
        if (!(transactionsByDate = transactionsRepository.findBypurchaseDateContaining(date)).isEmpty())
            return transactionsByDate;
        else throw new TransactionNotFoundException();
    }

    @PostMapping(TransactionsController.transactionsPath + "/search/id")
    public List<Transaction> search(@RequestParam Integer orderNumber) {
        return transactionsRepository.findByOrderNumberContaining(orderNumber); //todo message or exception
    }

    @PostMapping(TransactionsController.transactionsPath + "/search")
    public List<Transaction> searchForBuyer(@RequestParam String buyer) {
        return transactionsRepository.findByBuyerName(buyer); //todo message or exception
    }

    @PostMapping(TransactionsController.transactionsPath)
    public Transaction create(@RequestBody Map<String, String> body) throws BuyerNotFoundException, InvalidFormatException, FieldRequiredException {
        String buyerName = body.get("name");
        String transactionValue = body.get("value");
        String transactionDescription = body.get("description");
        Buyer transactionOwner;
        if ((transactionOwner = buyersRepository.findByName(buyerName)) == null)
            throw new BuyerNotFoundException(buyerName);
        else {
            Transaction newTransaction = new Transaction(transactionOwner, transactionValue, transactionDescription);
            transactionOwner.addTransaction(newTransaction);
            buyersRepository.save(transactionOwner);
            return transactionsRepository.save(newTransaction);
        }
    }

    @PutMapping(TransactionsController.transactionsPath + "/{id}")
    public Transaction update(@PathVariable String id, @RequestBody Map<String, String> body) throws TransactionNotFoundException, InvalidFormatException, FieldRequiredException {
        Integer transactionID = Integer.parseInt(id);
        Transaction transaction = transactionsRepository.findById(transactionID).orElseThrow(() -> new TransactionNotFoundException(transactionID));

        String newValue;
        if ((newValue = body.get("value")) != null) {
            newValue = newValue.trim();
            Buyer transctionOwner = buyersRepository.findByName(transaction.getBuyerName());
            transaction.setTransactionValue(newValue);
            transctionOwner.updateTransactionValue(transaction.getTransactionValue(), newValue);
        }
        String description;
        if ((description = body.get("description")) != null)
            transaction.editTransactionDescription(description);
        return transactionsRepository.save(transaction);
    }

    @DeleteMapping(TransactionsController.transactionsPath + "/{id}")
    public boolean delete(@PathVariable String id) throws TransactionNotFoundException {
        Integer transactionID = Integer.parseInt(id);
        Transaction transactionToDelete = transactionsRepository.findById(transactionID)
                .orElseThrow(() -> new TransactionNotFoundException(transactionID));
        Buyer transactionOwner = buyersRepository.findByName(transactionToDelete.getBuyerName());
        transactionOwner.removeTransaction(transactionToDelete);
        buyersRepository.save(transactionOwner);
        transactionsRepository.deleteById(transactionID);
        return true;
        //todo return deleted transaction tostring
    }
}
package assignment.controllers;


import assignment.entities.Buyer;
import assignment.entities.Transaction;
import assignment.exceptions.BuyerNotFoundException;
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
    public Transaction show(@PathVariable String id) {
        return transactionsRepository.findById(Integer.parseInt(id)).orElse(null);
    }

    @PostMapping(TransactionsController.transactionsPath + "/search")
    public List<Transaction> search(@RequestParam Integer orderNumber) {
        return transactionsRepository.findByOrderNumber(orderNumber);
    }

    @PostMapping(TransactionsController.transactionsPath)
    public Transaction create(@RequestBody Map<String, String> body) throws BuyerNotFoundException {
        String buyerName = body.get("buyer name");
        String transactionValue = body.get("transaction value");
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
    public Transaction update(@PathVariable String id, @RequestBody Map<String, String> body) {
        Transaction transaction = transactionsRepository.findById(Integer.parseInt(id)).orElse(null);
        transaction.setTransactionValue(body.get("value"));
        transaction.editTransactionDescription(body.get("description"));
        return transactionsRepository.save(transaction);
    }

    @DeleteMapping(TransactionsController.transactionsPath + "/{id}")
    public boolean delete(@PathVariable String id) {
        transactionsRepository.deleteById(Integer.parseInt(id));
        return true;
    }
}
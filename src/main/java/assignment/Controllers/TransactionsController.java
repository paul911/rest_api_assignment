package assignment.Controllers;


import assignment.Entities.Transaction;
import assignment.Repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionsController {

    private static final String transactionsPath = "/transactions";

    @Autowired
    private TransactionsRepository transactionsRepository;


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
    public Transaction create(@RequestBody Map<String, String> body) {
        String name = body.get("buyer");
        String value = body.get("value");
        String description = body.get("description");
        return transactionsRepository.save(new Transaction(name, value, description));
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
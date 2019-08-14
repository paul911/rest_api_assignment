package assignment.controllers;

import assignment.DTOs.TransactionDTO;
import assignment.entities.Transaction;
import assignment.services.BuyerServices;
import assignment.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private TransactionServices transactions;
    @Autowired
    private BuyerServices buyers;

    @GetMapping
    public List<Transaction> index() {
        return transactions.getAllTransactions();
    }

    @GetMapping(params = "id")
    public TransactionDTO show(@RequestParam int id) {
        Transaction foundTransaction = transactions.getTransactionById(id);
        return transactions.getTransactionInfo(foundTransaction);
    }

    @GetMapping(params = "date")
    public List<TransactionDTO> findByDate(@RequestParam String date) {
        List<Transaction> foundTransactions = transactions.getTransactionsByPurchaseDate(date);
        return transactions.getTransactionsInfo(foundTransactions);
    }

    @PostMapping
    public TransactionDTO create(@RequestBody @Valid TransactionDTO trans) {
        Transaction createdTransaction = transactions.createNewTransaction(trans);
        return transactions.getTransactionInfo(createdTransaction);
    }

    @PutMapping("/{id}")
    public TransactionDTO update(@PathVariable int id, @RequestBody @Valid TransactionDTO newTransactionInfo) {
        Transaction newTransaction = transactions.updateTransactionWithId(id, newTransactionInfo);

        return transactions.getTransactionInfo(newTransaction);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
//        Integer transactionID = Integer.parseInt(id);
        transactions.deleteTransaction(id);

        return String.format("Transaction %s has been successfully deleted.", id);
    }
}
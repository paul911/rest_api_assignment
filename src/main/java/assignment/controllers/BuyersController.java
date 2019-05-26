package assignment.controllers;


import assignment.entities.Buyer;
import assignment.entities.Transaction;
import assignment.exceptions.*;
import assignment.repositories.BuyersRepository;
import assignment.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@RestController
public class BuyersController {

    private static final String buyersPath = "/buyers";

    @Autowired
    private BuyersRepository buyersRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

    @RequestMapping("/")
    public String home() {
        return buyersPath + "/{id} -> get -> search buyer with {id};\n" +
                buyersPath + "/buyer/{name} -> get -> display buyer with {name};\n" +
                buyersPath + "/buyer/{name}/transactions -> get -> display transactions for buyer with {name};\n" +
                buyersPath + "/search -> post -> returns buyer containing keyword;\n" +
                buyersPath + " -> post -> add a new buyer, required values: name, identification, email;\n" + //todo add address maybe
                buyersPath + " -> put -> change buyer details, available values: name, identification, email, address;\n" + //todo add more
                buyersPath + "/{id} -> delete -> delete buyer with {id}, and all its associated transactions.";
    }

    @GetMapping(buyersPath)
    public List<Buyer> index() {
        return buyersRepository.findAll();
    }

    @GetMapping(buyersPath + "/{id}")
    public Buyer show(@PathVariable String id) throws BuyerNotFoundException {
        int buyerID = Integer.parseInt(id);
        return buyersRepository.findById(buyerID).orElseThrow(() -> new BuyerNotFoundException(buyerID));
    }

    @GetMapping(buyersPath + "/buyer/{name}")
    public Buyer showBuyer(@PathVariable String name) throws BuyerNotFoundException {
        Buyer foundBuyer = buyersRepository.findByName(name);
        if (foundBuyer != null)
            return foundBuyer;
        else throw new BuyerNotFoundException(name);
    }

    @GetMapping(buyersPath + "/buyer/{name}/transactions")
    public List<Transaction> showBuyersTransactions(@PathVariable String name) throws TransactionNotFoundException {
        List<Transaction> transactionsForBuyer;
        if (!(transactionsForBuyer = transactionsRepository.findByBuyerName(name)).isEmpty())
            return transactionsForBuyer;
        else throw new TransactionNotFoundException(name);
    }

    @PostMapping(buyersPath + "/search")
    public List<Buyer> search(@RequestParam String key) {
        List<Buyer> searchResults = buyersRepository.findByNameContainingOrEmailAddressContainingOrIdentificationNumberContainingOrRegisteredDateContaining
                (key, key, key, key);
        if (!searchResults.isEmpty())
            return searchResults;
        else throw new NoResultException(String.format("No results returned for keyword '%s'", key));
    }

    @PostMapping(buyersPath)
    public Buyer create(@RequestBody Map<String, String> body) throws BuyerAlreadyExistsException, InvalidFormatException, FieldRequiredException {
        String fullName = body.get("name").trim();
        if (buyersRepository.findByName(fullName) != null)
            throw new BuyerAlreadyExistsException(fullName);
        String identificationNumber = body.get("identification");
        if (buyersRepository.findByIdentificationNumber(identificationNumber) != null) {
            throw new BuyerAlreadyExistsException();
        }
        String email = body.get("email");
        String address; //todo revise how to create individual / corporate, with/without address

        return buyersRepository.save(new Buyer(fullName, identificationNumber, email));
    }

    @PutMapping(buyersPath + "/{id}")
    public Buyer update(@PathVariable String id, @RequestBody Map<String, String> body) throws BuyerNotFoundException, InvalidFormatException, FieldRequiredException {
        Buyer buyer = buyersRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new BuyerNotFoundException(id));
        if (body.containsKey("name")) {
            buyer.changeName(body.get("name"));
            // If the buyer has transactions, all the transactions must have the name of the buyer updated
            for (Transaction transaction : buyer.getTransactionList()) {
                transaction.changeBuyerName(body.get("name"));
                transactionsRepository.save(transaction);
            }
        }
        if (body.containsKey("identification"))
            buyer.changeBuyerIdentificationNumber(body.get("identification"));
        if (body.containsKey("email"))
            buyer.changeBuyerEmailAddress(body.get("email"));
        if (body.containsKey("address"))
            buyer.changeBuyerAddress(body.get("address"));
        return buyersRepository.save(buyer);
    }

    @DeleteMapping(buyersPath + "/{id}")
    public String delete(@PathVariable String id) throws BuyerNotFoundException {
        Integer buyerID = Integer.parseInt(id);
        Buyer buyerToDelete = buyersRepository.findById(buyerID).orElseThrow(() -> new BuyerNotFoundException(buyerID));
        List<Transaction> buyerTransactions = buyerToDelete.getTransactionList();
        for (Transaction transactionToDelete : buyerTransactions) {
            transactionsRepository.deleteById(transactionToDelete.getOrderNumber());
        }
        String deletedBuyer = buyerToDelete.toString();
        buyersRepository.deleteById(buyerID);
        return deletedBuyer + " has been successfully deleted.";
    }
}
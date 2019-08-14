package assignment.controllers;

import assignment.DTOs.BuyerDTO;
import assignment.DTOs.TransactionDTO;
import assignment.entities.Buyer;
import assignment.entities.Transaction;
import assignment.services.BuyerServices;
import assignment.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/buyers")
@RestController
public class BuyersController {

    @Autowired
    private BuyerServices buyers;
    @Autowired
    private TransactionServices transactions;

//    @RequestMapping("/")
//    public String home() {
//        return "<pre>\n" +
//                buyersPath + " -> get -> see all buyers;\n" +
//                buyersPath + "/{id} -> get -> show buyer with the exact {id};\n" +
//                buyersPath + "/buyer/{name} -> get -> display buyer with the exact {name};\n" +
//                buyersPath + "/buyer/{name}/transactions -> get -> display the list of transactions for buyer with {name};\n" +
//                buyersPath + "/search -> post -> returns list of buyers containing keyword;\n\n" +
//                buyersPath + " -> post -> add a new buyer; required values: name, identification, email;\n" +
//                buyersPath + " -> put -> change buyer details, available values: name, identification, email, address, phone;\n" +
//                buyersPath + "/{id} -> delete -> delete buyer with {id}, and all its associated transactions;\n\n" +
//                "/transactions -> get -> see all transactions made;\n" +
//                "/transactions/{id} -> get -> see transaction with {id}\n" +
//                "/transactions/date/{purchaseDate} -> get -> see list of transactions containing the {purchaseDate};\n" +
//                "/transactions/search/id -> post -> get list of transactions with id containing provided keyword;\n\n" +
//                "/transactions -> post -> create new transaction; required values: name (of buyer having this transaction, value, description;\n" +
//                "/transactions/{id} -> put -> update transaction with provided {id}; can change: value, description;\n" +
//                "/transactions/{id} -> delete -> delete transaction with provided {id}; updates buyer.</pre>";
//    }

    @GetMapping
    public List<BuyerDTO> index() {
        List<Buyer> allBuyers = buyers.getAllBuyers();
        return buyers.getBuyersInfo(allBuyers);
    }

    @GetMapping("/{id}")
    public BuyerDTO show(@PathVariable int id) {
        Buyer foundBuyer = buyers.findBuyerById(id);
        return buyers.getBuyerInfo(foundBuyer);
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionDTO> showBuyersTransactions(@PathVariable int id) {
        List<Transaction> buyerTransactions = buyers.getTransactionsForBuyer(id);
        return transactions.getTransactionsInfo(buyerTransactions);
    }

    @PostMapping("/search")
    public List<BuyerDTO> search(@RequestParam String keyword) {
        List<Buyer> foundBuyers = buyers.findBuyersContainingKeywords(keyword);
        return buyers.getBuyersInfo(foundBuyers);
    }

    @PostMapping
    public BuyerDTO create(@RequestBody @Valid BuyerDTO buyer) {
        Buyer createdBuyer = buyers.createNewBuyer(buyer);
        return buyers.getBuyerInfo(createdBuyer);
    }

    @PutMapping("/{id}")
    public BuyerDTO update(@PathVariable int id, @RequestBody @Valid BuyerDTO newBuyerInfo) {

        Buyer newBuyer = buyers.updateBuyerWithId(id, newBuyerInfo);

        return buyers.getBuyerInfo(newBuyer);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        buyers.deleteAllTransactionsForBuyerWithId(id);

        String buyerName = buyers.findBuyerById(id).getName();

        buyers.deleteBuyerWithId(id);

        return String.format("Buyer %s has been successfully deleted.", buyerName);
    }
}
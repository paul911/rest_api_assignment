package assignment.controllers;


import assignment.entities.Buyer;
import assignment.exceptions.*;
import assignment.repositories.BuyersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BuyersController {

    private static final String buyersPath = "/buyers";

    @Autowired
    private BuyersRepository buyersRepository;

    @RequestMapping("/")
    public String home() {
        return "Hello World!"; //todo
    }

    @GetMapping(BuyersController.buyersPath)
    public List<Buyer> index() {
        return buyersRepository.findAll();
    }

    @GetMapping(BuyersController.buyersPath + "/{id}")
    public Buyer show(@PathVariable String id) {
        return buyersRepository.findById(Integer.parseInt(id)).orElse(null);
    }

    @GetMapping(BuyersController.buyersPath + "/search/{name}")
    public List<Buyer> showBuyer(@PathVariable String name) {
        return buyersRepository.findByNameContaining(name);
    }

    @PostMapping(BuyersController.buyersPath + "/search")
    public List<Buyer> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return buyersRepository.findByNameContaining(searchTerm);
    }

    @PostMapping(BuyersController.buyersPath)
    public Buyer create(@RequestBody Map<String, String> body) throws BuyerAlreadyExistsException, InvalidNameFormatException, InvalidEmailFormatException, InvalidIdentificationNumberException {
        String fullName = body.get("full name").trim();
        if (buyersRepository.findByName(fullName) != null)
            throw new BuyerAlreadyExistsException(fullName);
        String identificationNumber = body.get("id number");
        if (buyersRepository.findByIdentificationNumberContaining(identificationNumber) != null) {
            throw new BuyerAlreadyExistsException();
        }
        String email = body.get("email");
        return buyersRepository.save(new Buyer(fullName, identificationNumber, email));
    }

    @PutMapping(BuyersController.buyersPath + "/{id}")
    public Buyer update(@PathVariable String id, @RequestBody Map<String, String> body) throws BuyerNotFoundException, InvalidNameFormatException, InvalidIdentificationNumberException {
        Buyer buyer = buyersRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new BuyerNotFoundException(id));
        buyer.changeName(body.get("first name") + " " + body.get("last name"));
        buyer.changeBuyerIdentificationNumber(body.get("id number"));
        return buyersRepository.save(buyer);
    }

    @DeleteMapping(BuyersController.buyersPath + "/{id}")
    public boolean delete(@PathVariable String id) {
        buyersRepository.deleteById(Integer.parseInt(id));
        return true;
    }
}
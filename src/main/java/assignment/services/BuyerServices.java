package assignment.services;

import assignment.DTOs.BuyerDTO;
import assignment.entities.Buyer;
import assignment.entities.Transaction;
import assignment.exceptions.BuyerAlreadyExistsException;
import assignment.exceptions.BuyerNotFoundException;
import assignment.exceptions.TransactionNotFoundException;
import assignment.repositories.BuyersRepository;
import assignment.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerServices {

    @Autowired
    private BuyersRepository buyersRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

    public List<Buyer> getAllBuyers() {
        return buyersRepository.findAll();
    }

    public Buyer findBuyerById(int buyerID) {
        return buyersRepository.findById(buyerID).orElseThrow(() -> new BuyerNotFoundException(buyerID));
    }

    public boolean buyerWithIdAlreadyExists(int id) {
        try {
            findBuyerById(id);
            return true;
        } catch (BuyerNotFoundException bnfe) {
            return false;
        }
    }

//    public Buyer findBuyerByName(String buyerName) {
//        Buyer foundBuyer = buyersRepository.findByName(buyerName);
//        if (foundBuyer != null)
//            return foundBuyer;
//        else throw new BuyerNotFoundException(buyerName);
//    }

    public List<Transaction> getTransactionsForBuyer(Integer buyerID) {
        List<Transaction> transactionsForBuyer;
        if (!(transactionsForBuyer = transactionsRepository.findByBuyerId(buyerID)).isEmpty())
            return transactionsForBuyer;
        else throw new TransactionNotFoundException(findBuyerById(buyerID).getName());
    }

    public List<Buyer> findBuyersContainingKeywords(String keyword) {
        List<Buyer> searchResults = buyersRepository.findByNameContainingOrEmailAddressContainingOrIdentificationNumberContainingOrRegisteredDateContaining
                (keyword, keyword, keyword, keyword);
        if (!searchResults.isEmpty())
            return searchResults;
        else throw new NoResultException(String.format("No results returned for keyword '%s'", keyword));
    }

    public Buyer createNewBuyer(BuyerDTO newBuyer) {
        if (buyersRepository.findByIdentificationNumber(newBuyer.getIdentification()) != null) {
            throw new BuyerAlreadyExistsException();
        }
//        String type = body.getOrDefault("type", Buyer.INDIVIDUAL); //todo move this
        return buyersRepository.save(new Buyer(newBuyer));
    }

    public Buyer updateBuyerWithId(int id, BuyerDTO newBuyer) {
        Buyer buyerToUpdate = findBuyerById(id);
        buyerToUpdate.changeName(newBuyer.getName());
        buyerToUpdate.changeBuyerEmailAddress(newBuyer.getEmail());
        buyerToUpdate.changeBuyerIdentificationNumber(newBuyer.getIdentification());
        buyerToUpdate.setBuyerType(newBuyer.getType());
        buyersRepository.save(buyerToUpdate);
        return buyerToUpdate;
    }

    public void deleteBuyerWithId(int id) {
        buyersRepository.deleteById(id);
    }

    public void deleteAllTransactionsForBuyerWithId(int buyerId) {

        List<Transaction> buyerTransactions = getTransactionsForBuyer(buyerId);

        for (Transaction transaction : buyerTransactions) {
            transactionsRepository.deleteById(transaction.getOrderNumber());
        }
    }

    public BuyerDTO getBuyerInfo(Buyer buyer) {
        BuyerDTO newBuyerInfo = new BuyerDTO();
        newBuyerInfo.setName(buyer.getName());
        newBuyerInfo.setIdentification(buyer.getIdentificationNumber());
        newBuyerInfo.setEmail(buyer.getEmailAddress());
        newBuyerInfo.setType(buyer.getType());
        newBuyerInfo.setId(buyer.getId());
        return newBuyerInfo;
    }

    public List<BuyerDTO> getBuyersInfo(List<Buyer> buyers) {
        return buyers.stream()
                .map(this::getBuyerInfo)
                .collect(Collectors.toList());
    }
}

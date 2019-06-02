package assignment.controllers;

import assignment.repositories.BuyersRepository;
import assignment.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Methods {

    @Autowired
    private BuyersRepository buyersRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

}

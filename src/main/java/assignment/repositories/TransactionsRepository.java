package assignment.repositories;

import assignment.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {
    Transaction findByOrderNumber(Integer orderNr);
    List<Transaction> findByOrderNumberContaining(Integer orderNr);
    List<Transaction> findBypurchaseDateContaining(String date);
    List<Transaction> findByBuyerName(String buyerName);
}
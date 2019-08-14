package assignment.repositories;

import assignment.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByPurchaseDateContaining(String date);
    List<Transaction> findByBuyerId(int yerId);
}
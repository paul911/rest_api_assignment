package assignment.repositories;

import assignment.entities.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyersRepository extends JpaRepository<Buyer, Integer> {

    List<Buyer> findByNameContaining(String name);
    Buyer findByName(String name);
    Buyer findByIdentificationNumber(String identificationNumber);
    List<Buyer> findByNameContainingOrEmailAddressContainingOrIdentificationNumberContainingOrRegisteredDateContaining
    (String name, String email, String identificationNumber, String registeredDate);
}

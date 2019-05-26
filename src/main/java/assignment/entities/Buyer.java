package assignment.entities;

import assignment.exceptions.InvalidEmailFormatException;
import assignment.exceptions.InvalidIdentificationNumberException;
import assignment.exceptions.InvalidNameFormatException;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "buyers")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Buyer_Type")
    private String type;
    @Column(name = "Name")
    private String name;
    @Column(name = "Identification_Number")
    private String identificationNumber;
    @Column(name = "Email_Address")
    private String emailAddress;
    @Column(name = "Address")
    private String address; //todo
    @Column(name = "Registered_Date")
    private String registeredDate;
    @Column(name = "Transactions_Executed")
    private int transactionsCount;
    @Column(name = "Transactions_Sum")
    private double transactionsTotalSum;
    @OneToMany(mappedBy = "buyer")
    @Column(name = "Transactions")
    private List<Transaction> transactionList = new ArrayList<>();

    // Constructors
    public Buyer() {
    }

    public Buyer(String fullName, String identificationNumber, String emailAddress) throws InvalidNameFormatException, InvalidEmailFormatException, InvalidIdentificationNumberException {
        this.makeBuyerIndividual(); //todo revise the creation of corporate/individual buyer
        this.changeName(fullName.trim());
        this.changeBuyerIdentificationNumber(identificationNumber.trim());
        this.changeBuyerEmailAddress(emailAddress.trim());
        this.assignTodaysDate();
        this.updateTransactions();
    }

    public Buyer(String fullName, String identificationNumber, String emailAddress, String address) throws InvalidNameFormatException, InvalidEmailFormatException, InvalidIdentificationNumberException {
        this(fullName, identificationNumber, emailAddress);
        this.changeBuyerAddress(address);
    }

    // Get Buyer Info

    public int getId() {
        return id;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public int getTransactionsCount() {
        return transactionsCount;
    }

    public double getTransactionsTotalSum() {
        return this.transactionsTotalSum;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    // Change Buyer info

    public void makeBuyerIndividual() {
        this.type = "Individual";
    }

    public void makeBuyerCorporate() {
        this.type = "Corporate";
    }

    public void changeName(String fullName) throws InvalidNameFormatException {
        String namePattern = "^[A-Z]{1}[a-z]+\\s[A-Z]{1}[a-z]+$";
        if (fullName.matches(namePattern))
            this.name = fullName;
        else
            throw new InvalidNameFormatException(fullName);
    }

    public void changeBuyerAddress(String address) {
        this.address = address;
    }

    public void changeBuyerEmailAddress(String emailAddress) throws InvalidEmailFormatException {
        emailAddress = emailAddress.trim();
        String emailPattern = "^[a-zA-Z\\.\\_]+@[a-zA-Z\\.]+$";
        if (emailAddress.matches(emailPattern))
            this.emailAddress = emailAddress;
        else throw new InvalidEmailFormatException(emailAddress);
    }

    public void changeBuyerIdentificationNumber(String idNr) throws InvalidIdentificationNumberException {
        switch (this.type) {
            case "Individual":
                if (idNr.matches("^\\d{13}$"))
                    this.identificationNumber = idNr;
                else throw new InvalidIdentificationNumberException(idNr);
                break;
            case "Corporate":
                if (idNr.matches("^\\d{9}$"))
                    this.identificationNumber = idNr;
                else throw new InvalidIdentificationNumberException(idNr, "corporate buyer");
                break;
        }
    }

    public void assignTodaysDate() {
        this.registeredDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public void addTransactionValue(double orderCost) {
        this.transactionsTotalSum += orderCost;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
        this.addTransactionValue(transaction.getTransactionValue());
        transaction.assignToBuyer(this);
        this.updateTransactions();
    }

    public void updateTransactions() {
        this.transactionsCount = this.transactionList.size();
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id='" + id +
                "', Name='" + this.name +
                "', Personal Identification Number='" + this.identificationNumber +
                "', Address='" + this.address +
                "', Email Address='" + this.emailAddress +
                "', Registered Date='" + this.registeredDate +
                "', Total Transactions='" + this.transactionList.size() +
                "', Total Transactions Sum='" + this.transactionsTotalSum +
                "'}";
    }
}
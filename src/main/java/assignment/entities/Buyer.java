package assignment.entities;

import assignment.DTOs.BuyerDTO;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static assignment.exceptions.InvalidFormatException.assertValidityOfInput;

@Entity
@Table(name = "buyers")
public class Buyer {

    private static final String INDIVIDUAL = "individual";
    private static final String CORPORATE = "corporate";

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
    @Column(name = "Phone_Number")
    private String phone;
    @Column(name = "Address")
    private String address;
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

    public Buyer(BuyerDTO newbuyer) {
        this.setBuyerType(newbuyer.getType());
        this.changeName(newbuyer.getName().trim());
        this.changeBuyerIdentificationNumber(newbuyer.getIdentification().trim());
        this.changeBuyerEmailAddress(newbuyer.getEmail().trim());
        this.assignTodaysDate();
        this.updateTransactions();
    }

//    public Buyer(String buyerType, String fullName, String identificationNumber, String emailAddress) {
//        this.setBuyerType(buyerType);
//        this.changeName(fullName.trim());
//        this.changeBuyerIdentificationNumber(identificationNumber.trim());
//        this.changeBuyerEmailAddress(emailAddress.trim());
//        this.assignTodaysDate();
//        this.updateTransactions();
//    }

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

    public String getPhone() {
        return this.phone;
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

    public void setBuyerType(String buyerType) {
        buyerType = buyerType.trim().toLowerCase();
        assertValidityOfInput(buyerType, "Buyer Type", 11, "(^individual$)|(^corporate$)",
                "Value must be either 'Individual' or 'Corporate', not case sensitive.");
        switch (buyerType) {
            case INDIVIDUAL:
                this.type = "Individual";
                break;
            case CORPORATE:
                this.type = "Corporate";
                break;
        }
    }

    public void changeName(String fullName) {
        fullName = fullName.trim();
        String namePattern = "^[A-Z]{1}[a-z]+\\s[A-Z]{1}[a-z]+$";
        // Name should be shorter than 30 characters and respect the pattern 'Firstname Lastname'
        assertValidityOfInput(fullName, "Name", 30, namePattern, "Firstname Lastname");
        this.name = fullName;
    }

    public void changeBuyerAddress(String address) {
        address = address.trim();
        String addressPattern = "^[a-zA-Z0-9\\.\\-\\s]+$";
        // Address should respect a format (in this example, letters, digits, dots and dashes are allowed)
        // Address should be shorter than 60 digits (
        assertValidityOfInput(address, "Address", 60, addressPattern, "letters, digits, dots and dashes are allowed");
        this.address = address;
    }

    public void changeBuyerEmailAddress(String emailAddress) {
        emailAddress = emailAddress.trim();
        // Email pattern is only for example purposes
        String emailPattern = "^[a-zA-Z0-9\\.\\_]+@[a-zA-Z0-9\\.]+\\.[a-zA-Z0-9\\.]+$";
        // Email address should have a valid format, and be shorter than 50 characters
        assertValidityOfInput(emailAddress, "Email Address", 50, emailPattern, "exa.mple_123@email.dot.com");
        this.emailAddress = emailAddress;
    }

    public void setPhone(String phone) {
        phone = phone.trim();
        String pattern = "(^\\d{10}$)|(^\\d{13}$)|(^\\+\\d{11}$)";
        // Phone number can follow 3 different patterns: 07XXXXXXXX, 004X7XXXXXXXX or +4X7XXXXXXXX
        assertValidityOfInput(phone, "Phone Number", 14, pattern, "07XXXXXXXX, 004X7XXXXXXXX or +4X7XXXXXXXX, without any spaces.");
        this.phone = phone;
    }

    // Identification number is assigned to the buyer, according the it's type
    // 'Individual' buyer should have a 13 digits UNIQUE string
    // 'Corporate' buyer should have 9 digits UNIQUE string
    public void changeBuyerIdentificationNumber(String idNr) {
        switch (this.type.toLowerCase()) {
            case INDIVIDUAL:
                assertValidityOfInput(idNr, "Individual Identification number", 14, "^\\d{13}$", "13 digits only unique string");
                break;
            case CORPORATE:
                assertValidityOfInput(idNr, "Corporate Identification number", 10, "^\\d{9}$", "9 digits only unique string");
                break;
        }
        this.identificationNumber = idNr;
    }

    private void assignTodaysDate() {
        this.registeredDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    // Transaction is tied to the buyer, and viceversa
    // Total sum and count of transaction values are updated
    public void addTransaction(Transaction transaction) {
        this.transactionList.add(transaction);
        this.addTransactionValue(transaction.getTransactionValue());
        transaction.assignToBuyer(this);
        this.updateTransactions();
    }

    public void removeTransaction(Transaction transaction) {
        this.transactionList.remove(transaction);
        this.removeTransactionValue(transaction.getTransactionValue());
        this.updateTransactions();
    }

    // Once a new transaction is added for a buyer, the total displayed value of the buyer's transaction will be updated
    private void addTransactionValue(double orderCost) {
        this.transactionsTotalSum += orderCost;
    }

    // Once a new transaction is added for a buyer, the total displayed value of the buyer's transaction will be updated
    private void removeTransactionValue(double orderCost) {
        this.transactionsTotalSum -= orderCost;
    }

    // In case one transactions changes it value, the total transactions sum displayed on the buyer must be updated
    public void updateTransactionValue(double previousValue, String newValue) {
        this.transactionsTotalSum -= previousValue;
        this.transactionsTotalSum += Double.parseDouble(newValue);
    }

    // Buyer's transaction count is updated to reflect the list of transactions
    private void updateTransactions() {
        this.transactionsCount = this.transactionList.size();
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id='" + id +
                "', Name='" + this.name +
                "', Personal Identification Number='" + this.identificationNumber +
                "', Email Address='" + this.emailAddress +
                "', Phone Number='" + this.phone +
                "', Address='" + this.address +
                "', Registered Date='" + this.registeredDate +
                "', Total Transactions='" + this.transactionList.size() +
                "', Total Transactions Sum='" + this.transactionsTotalSum +
                "'}";
    }
}
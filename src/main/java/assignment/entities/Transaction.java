package assignment.entities;

import assignment.exceptions.FieldRequiredException;
import assignment.exceptions.InvalidFormatException;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static assignment.exceptions.InvalidFormatException.assertValidityOfInput;

@SequenceGenerator(name = "ordernr", initialValue = 10000000, allocationSize = 1)
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordernr")
    @Column(name = "Order_Number")
    private Integer orderNumber;
    @Column(name = "Billed_To")
    private String buyerName;
    @Column(name = "Value")
    private double transactionValue;
    @Column(name = "Purchase_Date")
    private String purchaseDate;
    @Column(name = "Description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    public Transaction() {
    }

    public Transaction(Buyer buyer, String transactionValue, String description) throws InvalidFormatException, FieldRequiredException {
        this.assignToBuyer(buyer);
        this.assignTodaysDate();
        this.setTransactionValue(transactionValue);
        this.editTransactionDescription(description);
    }

    // Get Transactions info

    public Integer getOrderNumber() {
        return this.orderNumber;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public double getTransactionValue() {
        return this.transactionValue;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getDescription() {
        return this.description;
    }


    // Change Transactions info

    public void changeBuyerName(String name) {
        this.buyerName = name;
    }

    void assignToBuyer(Buyer buyer) {
        this.buyer = buyer;
        this.changeBuyerName(buyer.getName());
    }

    private void assignTodaysDate() {
        this.purchaseDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public void setTransactionValue(String transactionValue) throws InvalidFormatException, FieldRequiredException {
        String pattern = "^\\d+(\\.{1}\\d{1,2})?$";
        assertValidityOfInput(transactionValue, "Transaction Value", 12, pattern, "Max 10 digits, with maximum 2 decimals ( use . as divider)");
        this.transactionValue = Double.parseDouble(transactionValue);
    }

    public void editTransactionDescription(String newDescription) throws InvalidFormatException, FieldRequiredException {
        assertValidityOfInput(newDescription, "Transaction Description", 100, ".+", "Description can only contain a maximum of 100 characters");
        this.description = newDescription;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Order Number='" + +this.orderNumber +
                "', Purchase Date ='" + this.purchaseDate +
                "', Buyer ='" + this.buyerName +
                "', Transaction Value='" + this.transactionValue +
                "', Description='" + this.description +
                "'}";
    }
}
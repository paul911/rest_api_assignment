package assignment.entities;

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
    @JoinColumn(name = "buyer")
    private Buyer buyer;
    private int buyerId;

    public Transaction() {
    }

    public Transaction(Buyer buyer, double transactionValue, String description) {

        this.assignToBuyer(buyer);
        this.assignTodaysDate();
        this.setTransactionValue(transactionValue);
        this.setTransactionDescription(description);
    }

    // Get Transactions info

    public Integer getOrderNumber() {
        return this.orderNumber;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public int getBuyerId() {
        return this.buyerId;
    }

    private void setBuyerId(int id) {
        this.buyerId = id;
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

    private void setBuyerName(String name) {
        this.buyerName = name;
    }

    public void assignToBuyer(Buyer buyer) {
        this.buyer = buyer;
        this.setBuyerName(buyer.getName());
        this.setBuyerId(buyer.getId());
    }

    private void assignTodaysDate() {
        this.purchaseDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public void setTransactionValue(double transactionValue) {
        String value = String.valueOf(transactionValue);
        String pattern = "^\\d+(\\.{1}\\d{1,2})?$";
        assertValidityOfInput(value, "Transaction Value", 12, pattern, "Max 10 digits, with maximum 2 decimals ( use . as divider)");
        this.transactionValue = Double.parseDouble(value);
    }

    public void setTransactionDescription(String newDescription) {
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
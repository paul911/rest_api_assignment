package assignment.Entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    public Transaction() {
    }

    public Transaction(String buyerName, String transactionValue, String description) {
        this.assignToBuyer(buyerName);
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

    public void assignToBuyer(String buyerName) { //todo first check if buyer exists
        this.buyerName = buyerName;
    }

    public void assignTodaysDate() {
        this.purchaseDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    public void setTransactionValue(String transactionValue) {
        this.transactionValue = Double.parseDouble(transactionValue);
    }

    public void editTransactionDescription(String newDescription) {
        this.description = newDescription;
    }

    @Override
    public String toString() { //todo add the buyer name
        return "Transaction{" +
                "Order Number='" + +this.orderNumber +
                "', Purchase Date ='" + this.purchaseDate +
                "', Buyer ='" + this.buyerName +
                "', Transaction Value='" + this.transactionValue +
                "', Description='" + this.description +
                "'}";
    }


}
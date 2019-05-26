package assignment.entities;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    public Transaction() {

    }

    public Transaction(Buyer buyer, String transactionValue, String description) {
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

    public void assignToBuyer(Buyer buyer) {
        this.buyer = buyer;
        this.buyerName = buyer.getName();
    }

    public void assignTodaysDate() {
        this.purchaseDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
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
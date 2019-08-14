package assignment.DTOs;

import javax.validation.constraints.NotNull;

public class TransactionDTO {

    @NotNull
    private int buyer;
    @NotNull
    private double value;
    @NotNull
    private String description;


    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

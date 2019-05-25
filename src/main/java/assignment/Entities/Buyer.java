package assignment.Entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Buyer() {
    }

    // Constructors

    public Buyer(String firstName, String lastName, String identificationNumber, String emailAddress) {
        this.makeBuyerIndividual(); //todo revise the creation of corporate/individual buyer
        this.changeName(firstName, lastName);
        this.changeBuyerIdentificationNumber(identificationNumber);
        this.changeBuyerEmailAddress(emailAddress);
        this.assignTodaysDate();
    }

    public Buyer(String firstName, String lastName, String identificationNumber, String emailAddress, String address) {
        this(firstName, lastName, identificationNumber, emailAddress);
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


    // Change Buyer info

    public void makeBuyerIndividual() {
        this.type = "Individual";
    }

    public void makeBuyerCorporate() {
        this.type = "Corporate";
    }

    public void changeName(String firstName, String lastName) {
        // todo check valid name
        this.name = firstName + " " + lastName;
    }

    public void changeBuyerAddress(String address) {
        this.address = address;
    }

    public void changeBuyerEmailAddress(String emailAddress) {
        //todo check if valid email
        this.emailAddress = emailAddress;
    }

    public void changeBuyerIdentificationNumber(String idNr) {
        //todo check valid
        this.identificationNumber = idNr;
    }

    public void assignTodaysDate() {
        this.registeredDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
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
                "'}";
    }
}
package assignment.exceptions;

public class InvalidIdentificationNumberException extends Exception {
    String identificationNumber;

    public InvalidIdentificationNumberException(String identificationNumber) {
        super("Please provide a valid 13 digit only Identification Number.");
    }

    public InvalidIdentificationNumberException(String... identificationNumber) {
        super("Please provide a valid 9 digit only Company Identification Number.");
    }
}



package com.revature.BankAppSimulator.SystemFiles;

public class InvalidLoginException extends Exception {
    public String getMessage() {
        System.out.println("\nInvalid username and password combination. Please try again.");
        return null;
    }
}

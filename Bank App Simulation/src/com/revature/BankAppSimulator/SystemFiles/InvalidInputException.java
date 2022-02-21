package com.revature.BankAppSimulator.SystemFiles;

public class InvalidInputException extends Exception {
    public String getMessage() {
        System.out.println("Invalid input. Please try again.\n");
        return null;
    }
}

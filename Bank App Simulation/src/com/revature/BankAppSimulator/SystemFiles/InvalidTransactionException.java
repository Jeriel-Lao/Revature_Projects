package com.revature.BankAppSimulator.SystemFiles;

public class InvalidTransactionException extends Exception{
    public String getMessage() {
        System.out.println("Invalid transaction. Please try again.\n");
        return null;
    }
}

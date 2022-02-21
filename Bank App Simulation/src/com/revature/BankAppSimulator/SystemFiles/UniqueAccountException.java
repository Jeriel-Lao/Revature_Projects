package com.revature.BankAppSimulator.SystemFiles;

public class UniqueAccountException extends Exception {
    public String getMessage() {
        System.out.println("That name is already taken. Please try again.\n");
        return null;
    }
}

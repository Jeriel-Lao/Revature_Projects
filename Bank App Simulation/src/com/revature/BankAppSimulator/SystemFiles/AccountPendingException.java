package com.revature.BankAppSimulator.SystemFiles;

public class AccountPendingException extends Exception{
    public String getMessage() {
        System.out.println("Account has not been approved. Please try again later.\n\n\n");
        return null;
    }
}

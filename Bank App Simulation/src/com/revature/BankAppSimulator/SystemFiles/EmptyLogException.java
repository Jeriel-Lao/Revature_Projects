package com.revature.BankAppSimulator.SystemFiles;

import static java.lang.Thread.sleep;

public class EmptyLogException extends Exception {
    public String getMessage() {
        System.out.println("This list is currently empty. Please try again later.\n");
        try {
            sleep(1500);
        } catch (InterruptedException ignored) {}
        return null;
    }
}

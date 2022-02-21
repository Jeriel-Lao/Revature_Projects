package com.revature.BankAppSimulator.Account;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int userId;
    private int acctId;
    private String name;
    private double balance;
    private boolean isPending;
    private static int increment = 1;
    public static List<Integer> log = new ArrayList<>();

    public Account(){

    }
    public Account(int userId, int acctId, String name, double balance, boolean isPending) { //calling an account
        this.userId = userId;
        this.acctId = acctId;
        this.name = name;
        this.balance = balance;
        this.isPending = isPending;
    }
    public Account(int userId, String name, double balance) { //making new accounts
        log.add(0);
        this.userId = userId;
        this.name = name;
        this.balance = balance;
        boolean check = false;
        for(int i : log) {
            if(userId == i) {
                check = true;
                isPending = true;
                increment++;
            }
        }
        this.acctId = increment;
        if(!check) {
            log.add(userId);
            isPending = false;
        }
        log.remove((Integer) 0);
    }


    public int getUserId() {
        return this.userId;
    }

    public int getAcctId() {
        return acctId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}

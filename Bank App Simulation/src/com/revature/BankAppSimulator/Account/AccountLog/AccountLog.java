package com.revature.BankAppSimulator.Account.AccountLog;

public class AccountLog {
    private int userId;
    private int acctId;
    private boolean isCreate;
    private double amount;
    private String date;

    public AccountLog() {

    }
    public AccountLog(int userId, int acctId, boolean isCreate, double amount, String date) {
        this.userId = userId;
        this.acctId = acctId;
        this.isCreate = isCreate;
        this.amount = amount;
        this.date = date;
    }
    public AccountLog(int userId, int acctId, double amount, String date) {
        this.userId = userId;
        this.acctId = acctId;
        this.isCreate = true;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        if (isCreate) {
            return "[" + date + "] User " + userId + " created Account " + acctId + " with a starting balance of " + amount;
        } else {
            return "[" + date + "] Account " + acctId + " was removed while still containing a balance of " + amount;
        }
    }

    public int getUserId() {
        return userId;
    }

    public int getAcctId() {
        return acctId;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

}

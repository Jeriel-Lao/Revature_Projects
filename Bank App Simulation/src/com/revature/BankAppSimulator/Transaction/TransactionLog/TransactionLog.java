package com.revature.BankAppSimulator.Transaction.TransactionLog;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionLog {
    private final int userId;
    private final int acctId;
    private final boolean isValid;
    private double amount;
    private final String date;

    public TransactionLog(int userId, int acctId, boolean isValid, double amount, String date) {
        this.userId = userId;
        this.acctId = acctId;
        this.isValid = isValid;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        if (amount >= 0) {
            return "[" + date + "] User " + userId + " deposited " + BigDecimal.valueOf(amount).setScale(2, RoundingMode.UNNECESSARY) + " into Account " + acctId;
        } else {
            amount *= -1;
            return "[" + date + "] User " + userId + " withdrew " + BigDecimal.valueOf(amount).setScale(2, RoundingMode.UNNECESSARY) + " from Account " + acctId;
        }
    }

    public int getUserId() {
        return userId;
    }

    public int getAcctId() {
        return acctId;
    }

    public boolean isValid() {
        return isValid;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

}

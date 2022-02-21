package com.revature.BankAppSimulator.Transaction;

import com.revature.BankAppSimulator.Account.AccountDao;
import com.revature.BankAppSimulator.Login.LoginDao;
import com.revature.BankAppSimulator.SystemFiles.DaoFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class Transaction {
    private int userId1;
    private int userId2;
    private int acctId1;
    private int acctId2 = 1;
    private boolean isPending;
    private boolean isValid;
    private double balance;
    public Transaction(){

    }
    public Transaction(int userId1, int userId2, int acctId1, int acctId2, boolean isPending, boolean isValid, double balance) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.acctId1 = acctId1;
        this.acctId2 = acctId2;
        this.isPending = isPending;
        this.isValid = isValid;
        this.balance = balance;
    }

    public Transaction(int userId, int acctId, boolean isPending, boolean isValid, double balance) {
        this.userId1 = userId;
        this.userId2 = 0;
        this.acctId1 = acctId;
        this.acctId2 = 0;
        this.isPending = isPending;
        this.isValid = isValid;
        this.balance = balance;

    }
    public String toString(int currentUser) throws SQLException {
        LoginDao loginDao = DaoFactory.getLoginDao();
        if(isPending) {
            if (userId1 == currentUser) {
                return "You sent " + BigDecimal.valueOf(balance).setScale(2, RoundingMode.UNNECESSARY) + " to " + loginDao.getLoginById(userId2).getName();
            } else {
                return loginDao.getLoginById(userId2).getName() + "sent you " + BigDecimal.valueOf(balance).setScale(2, RoundingMode.UNNECESSARY);
            }
        }
        else {
            AccountDao accountDao = DaoFactory.getAccountDao();
            if(balance < 0) {
                return "You withdrew " + BigDecimal.valueOf(0 - balance).setScale(2, RoundingMode.UNNECESSARY) + " from " + accountDao.getAccountById(userId1, acctId1).getName();
            }
            else {
                return "You deposited " + BigDecimal.valueOf(balance).setScale(2, RoundingMode.UNNECESSARY) + " into  " + accountDao.getAccountById(userId1, acctId1).getName();
            }
        }
    }

    public int getUserId1() {
        return this.userId1;
    }

    public int getUserId2() {
        return this.userId2;
    }

    public int getAcctId1() {
        return acctId1;
    }

    public int getAcctId2() {
        return acctId2;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public boolean isValid() {
        return isValid;
    }

    public double getBalance() {
        return balance;
    }

}

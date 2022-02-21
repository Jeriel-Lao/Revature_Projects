package com.revature.BankAppSimulator.Transaction;

import com.revature.BankAppSimulator.Account.Account;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDao {
    void addTransaction(Transaction transaction) throws SQLException;
    void deleteTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getTransactions() throws SQLException;
    List<Transaction> getPendingTransactions(int UserId) throws SQLException;
    List<Transaction> getValidTransactions() throws SQLException;
    List<Transaction> getTransactionsByAccount(int userId, int acctId) throws SQLException;

}

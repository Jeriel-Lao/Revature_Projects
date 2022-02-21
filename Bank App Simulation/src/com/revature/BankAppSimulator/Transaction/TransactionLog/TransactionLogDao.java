package com.revature.BankAppSimulator.Transaction.TransactionLog;

import com.revature.BankAppSimulator.Account.Account;

import java.sql.SQLException;
import java.util.List;

public interface TransactionLogDao {
    void addTransactionLog(TransactionLog transactionLog) throws SQLException;
    void deleteTransactionLog(TransactionLog transactionLog) throws SQLException;
    List<TransactionLog> getTransactionLog() throws SQLException;
}

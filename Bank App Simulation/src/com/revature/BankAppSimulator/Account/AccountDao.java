package com.revature.BankAppSimulator.Account;

import com.revature.BankAppSimulator.Account.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    void addAccount(Account account) throws SQLException;
    void updateAccount(Account account) throws SQLException;
    void deleteAccount(int userId, int acctId) throws SQLException;
    List<Account> getAccountsById(int userId) throws SQLException;
    List<Account> getPendingAccounts() throws SQLException;
    Account getAccountById(int userId, int acctId) throws SQLException;
}


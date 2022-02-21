package com.revature.BankAppSimulator.Account.AccountLog;

import com.revature.BankAppSimulator.Account.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountLogDao {
    void addAccountLog(AccountLog accountLog) throws SQLException;
    List<AccountLog> getAccountLog() throws SQLException;

}

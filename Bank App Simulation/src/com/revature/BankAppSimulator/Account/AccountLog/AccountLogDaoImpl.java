package com.revature.BankAppSimulator.Account.AccountLog;

import com.revature.BankAppSimulator.Account.Account;
import com.revature.BankAppSimulator.SystemFiles.ConnectionFactory;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountLogDaoImpl implements AccountLogDao {

    Connection connection;

    public AccountLogDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    public void addAccountLog(AccountLog accountLog) throws SQLException {
        String sql = "insert into account_log (user_id, acct_id, is_create, amount, date) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, accountLog.getUserId());
        preparedStatement.setInt(2, accountLog.getAcctId());
        preparedStatement.setBoolean(3, accountLog.isCreate());
        preparedStatement.setDouble(4, accountLog.getAmount());
        preparedStatement.setString(5, accountLog.getDate());
        preparedStatement.executeUpdate();
    }

    public List<AccountLog> getAccountLog() throws SQLException {
        List <AccountLog> log = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from account_log";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            log.add(new AccountLog(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getBoolean(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)));
        }
        return log;
    }

}

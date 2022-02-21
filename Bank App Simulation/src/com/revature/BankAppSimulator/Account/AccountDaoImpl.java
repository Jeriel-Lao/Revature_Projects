package com.revature.BankAppSimulator.Account;

import com.revature.BankAppSimulator.SystemFiles.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    Connection connection;

    public AccountDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addAccount(Account account) throws SQLException {
        boolean validInput;
        int accountIndex = 1;
        do {
            try {
                validInput = true;
                String sql = "insert into account (user_id, acct_id, acct_name, balance, is_pending) values (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, account.getUserId());
                preparedStatement.setInt(2, accountIndex);
                preparedStatement.setString(3, account.getName());
                preparedStatement.setDouble(4, account.getBalance());
                preparedStatement.setBoolean(5, account.isPending());
                int count = preparedStatement.executeUpdate();
                if(count == 0 ) {
                    throw new InvalidInputException();
                }
            } catch (SQLIntegrityConstraintViolationException | InvalidInputException e) {
                accountIndex++;
                System.out.println(accountIndex);
                validInput = false;
            }
        }while(!validInput);
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        String sql = "update account set acct_name = ?, balance = ?, is_pending = ? where user_id = ? and acct_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getName());
        preparedStatement.setDouble(2, account.getBalance());
        preparedStatement.setBoolean(3, account.isPending());
        preparedStatement.setInt(4, account.getUserId());
        preparedStatement.setInt(5, account.getAcctId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteAccount(int userId, int acctId) throws SQLException {
        String sql = "delete from account where user_id = ? and acct_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, acctId);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Account> getAccountsById(int userId) throws SQLException {
        List<Account> logins = new ArrayList<>();
        String sql = "select * from account where user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            logins.add(new Account(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getBoolean(5)));
        }
        return logins;
    }

    public List<Account> getPendingAccounts() throws SQLException {
        List<Account> logins = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from account where is_pending = true";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            logins.add(new Account(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getBoolean(5)));
        }
        return logins;
    }

    @Override
    public Account getAccountById(int userId, int acctId) throws SQLException {
        String sql = "select * from account where user_id = ? and acct_id = ? limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, acctId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Account(userId, acctId,
                resultSet.getString("acct_name"),
                resultSet.getDouble("balance"),
                resultSet.getBoolean("is_pending"));
    }
}


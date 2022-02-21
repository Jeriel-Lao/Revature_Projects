package com.revature.BankAppSimulator.Transaction.TransactionLog;

import com.revature.BankAppSimulator.Account.Account;
import com.revature.BankAppSimulator.SystemFiles.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogDaoImpl implements TransactionLogDao{

    Connection connection;

    public TransactionLogDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addTransactionLog(TransactionLog transactionLog) throws SQLException {
        String sql = "insert into transaction_log (user_id, acct_id, is_valid, amount, date) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transactionLog.getUserId());
        preparedStatement.setInt(2, transactionLog.getAcctId());
        preparedStatement.setBoolean(3, transactionLog.isValid());
        preparedStatement.setDouble(4, transactionLog.getAmount());
        preparedStatement.setString(5, transactionLog.getDate());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteTransactionLog(TransactionLog transactionLog) throws SQLException {
        String sql = "delete from transaction_log (user_id, acct_id, is_valid, balance, date) values (?, ?, ?, ?, ?) limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transactionLog.getUserId());
        preparedStatement.setInt(2, transactionLog.getAcctId());
        preparedStatement.setBoolean(3, transactionLog.isValid());
        preparedStatement.setDouble(4, transactionLog.getAmount());
        preparedStatement.setString(5, transactionLog.getDate());

    }

    @Override
    public List<TransactionLog> getTransactionLog() throws SQLException {
        List<TransactionLog> transactions = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from transaction_log";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            transactions.add(new TransactionLog(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getBoolean(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)));
        }
        return transactions;
    }
}

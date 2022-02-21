package com.revature.BankAppSimulator.Transaction;

import com.revature.BankAppSimulator.SystemFiles.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao{

    Connection connection;

    public TransactionDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "insert into transaction (user_id1, user_id2, acct_id1, acct_id2, is_pending, is_valid, balance) values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transaction.getUserId1());
        preparedStatement.setInt(2, transaction.getUserId2());
        preparedStatement.setInt(3, transaction.getAcctId1());
        preparedStatement.setInt(4, transaction.getAcctId2());
        preparedStatement.setBoolean(5, transaction.isPending());
        preparedStatement.setBoolean(6, transaction.isValid());
        preparedStatement.setDouble(7, transaction.getBalance());
        preparedStatement.executeUpdate();
    }

    public void deleteTransaction(Transaction transaction) throws SQLException {
        String sql = "delete from transaction where user_id1 = ? and user_id2 = ? and acct_id1 = ? and balance = ? and is_pending = true limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transaction.getUserId1());
        preparedStatement.setInt(2, transaction.getUserId2());
        preparedStatement.setInt(3, transaction.getAcctId1());
        preparedStatement.setDouble(4, transaction.getBalance());
        preparedStatement.executeUpdate();
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        String sql = "update transaction set is_pending = false where user_id1 = ? and user_id2 = ? and acct_id1 = ? and acct_id2 = ? and is_pending = true and balance = ? limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transaction.getUserId1());
        preparedStatement.setInt(2, transaction.getUserId2());
        preparedStatement.setInt(3, transaction.getAcctId1());
        preparedStatement.setInt(4, transaction.getAcctId2());
        preparedStatement.setDouble(5, transaction.getBalance());
        preparedStatement.executeUpdate();
    }

    public List<Transaction> getTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from transaction";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            transactions.add(new Transaction(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getBoolean(5),
                    resultSet.getBoolean(6),
                    resultSet.getDouble(7)));
        }
        return transactions;
    }

    public List<Transaction> getPendingTransactions(int userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transaction where (user_id1 = ? or user_id2 = ?) and is_pending = true";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                    transactions.add(new Transaction(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4), true,
                            resultSet.getBoolean(6),
                            resultSet.getDouble(7)));
            }
        return transactions;
    }

    public List<Transaction> getValidTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from transaction where is_valid = true";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next() && resultSet.getBoolean(5)){
            transactions.add(new Transaction(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getBoolean(5),
                    resultSet.getBoolean(6),
                    resultSet.getDouble(7)));
        }
        return transactions;
    }
    public List<Transaction> getTransactionsByAccount(int userId, int acctId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "select * from transaction where (user_id1 = ? and acct_id1 = ?) or (user_id2 = ? and acct_id2 = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, acctId);
        preparedStatement.setInt(3, userId);
        preparedStatement.setInt(4, acctId);
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        while(resultSet.next() && resultSet.getBoolean(5)){
            transactions.add(new Transaction(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getBoolean(5),
                    resultSet.getBoolean(6),
                    resultSet.getDouble(7)));
        }
        return transactions;
    }
}

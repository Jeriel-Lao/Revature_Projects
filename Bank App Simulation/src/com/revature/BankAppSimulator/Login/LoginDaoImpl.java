package com.revature.BankAppSimulator.Login;

import com.revature.BankAppSimulator.SystemFiles.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDaoImpl implements com.revature.BankAppSimulator.Login.LoginDao {

    Connection connection;

    public LoginDaoImpl(){
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addLogin(Login login) throws SQLException {
        String sql = "insert into login (user_name, user_password, is_customer) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login.getName());
        preparedStatement.setString(2, login.getPassword());
        preparedStatement.setBoolean(3, login.getCustomer());
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateLogin(Login login) throws SQLException {
        String sql = "update employee set user_name = ?, user_password = ?, is_customer = ?, where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login.getName());
        preparedStatement.setString(2, login.getPassword());
        preparedStatement.setBoolean(3, login.getCustomer());
        preparedStatement.setInt(4, login.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteLogin(int id) throws SQLException {
        String sql = "delete from employee where user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Login> getLogins() throws SQLException {
        List<Login> logins = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from login";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            logins.add(new Login(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4)));
        }
        return logins;
    }

    public List<Login> getCustomers() throws SQLException {
        List<Login> logins = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sql = "select * from login";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next() && resultSet.getBoolean(4))
            logins.add(new Login(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4)));
        return logins;
    }

    @Override
    public Login getLoginById(int id) throws SQLException {
        String sql = "select * from login where user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Login(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4));
    }
}


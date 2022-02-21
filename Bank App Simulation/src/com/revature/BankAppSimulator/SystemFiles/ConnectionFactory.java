package com.revature.BankAppSimulator.SystemFiles;

import java.sql.*;
import java.util.ResourceBundle;

public class ConnectionFactory {
    private static Connection connection = null;

    private ConnectionFactory(){
    }

    public static Connection getConnection(){
        if(connection == null){
            ResourceBundle resourceBundle = ResourceBundle.getBundle("com/revature/BankAppSimulator/SystemFiles/dbConfig");
            String url = resourceBundle.getString("url");
            String username = resourceBundle.getString("username");
            String password = resourceBundle.getString("password");
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }
}

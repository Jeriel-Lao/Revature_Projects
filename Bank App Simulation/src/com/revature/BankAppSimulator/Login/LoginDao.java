package com.revature.BankAppSimulator.Login;

import java.sql.SQLException;
import java.util.List;

public interface LoginDao {
    void addLogin(Login login) throws SQLException;
    void updateLogin(Login login) throws SQLException;
    void deleteLogin(int id) throws SQLException;
    List<Login> getLogins() throws SQLException;
    List<Login> getCustomers() throws SQLException;
    Login getLoginById(int id) throws SQLException;
}


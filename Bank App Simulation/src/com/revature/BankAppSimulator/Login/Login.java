package com.revature.BankAppSimulator.Login;

import com.revature.BankAppSimulator.Account.*;

import java.util.List;

public class Login {
    private int id;
    private String name;
    private String password;
    private boolean customer;
    private static int increment = 1;

    public Login(){

    }
    public Login(int id, String name, String password, boolean customer) { //reference database
        this.id = id;
        this.name = name;
        this.password = password;
        this.customer = customer;
    }
    public Login(String name, String password, boolean customer) { //make new login
        this.id = increment;
        this.name = name;
        this.password = password;
        this.customer = customer;
        increment++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getCustomer() {
        return customer;
    }

    public void setCusomter(boolean customer) {
        this.customer = customer;
    }
}

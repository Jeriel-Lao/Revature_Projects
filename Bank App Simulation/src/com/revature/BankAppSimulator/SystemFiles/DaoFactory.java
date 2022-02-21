package com.revature.BankAppSimulator.SystemFiles;

import com.revature.BankAppSimulator.Account.*;
import com.revature.BankAppSimulator.Account.AccountLog.*;
import com.revature.BankAppSimulator.Login.LoginDao;
import com.revature.BankAppSimulator.Transaction.*;
import com.revature.BankAppSimulator.Transaction.TransactionLog.*;

public class DaoFactory {

    private static AccountDao dao1;
    private static LoginDao dao2;
    private static TransactionDao dao3;
    private static AccountLogDao dao4;
    private static TransactionLogDao dao5;


    private DaoFactory(){

    }

    public static AccountDao getAccountDao(){
        if(dao1==null){
            dao1 = new AccountDaoImpl();
        }
        return dao1;
    }
    public static LoginDao getLoginDao(){
        if(dao2==null){
            dao2 = new com.revature.BankAppSimulator.Login.LoginDaoImpl();
        }
        return dao2;
    }
    public static TransactionDao getTransactionDao(){
        if(dao3==null){
            dao3 = new TransactionDaoImpl();
        }
        return dao3;
    }
    public static AccountLogDao getAccountLogDao(){
        if(dao4==null){
            dao4 = new AccountLogDaoImpl();
        }
        return dao4;
    }
    public static TransactionLogDao getTransactionLogDao(){
        if(dao5==null){
            dao5 = new TransactionLogDaoImpl();
        }
        return dao5;
    }
}

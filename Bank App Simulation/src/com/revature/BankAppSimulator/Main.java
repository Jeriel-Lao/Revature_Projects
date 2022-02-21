package com.revature.BankAppSimulator;

import com.revature.BankAppSimulator.Account.*;
import com.revature.BankAppSimulator.Account.AccountLog.*;
import com.revature.BankAppSimulator.Login.*;
import com.revature.BankAppSimulator.SystemFiles.*;
import com.revature.BankAppSimulator.Transaction.*;
import com.revature.BankAppSimulator.Transaction.TransactionLog.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.revature.BankAppSimulator.SystemFiles.PrintConsole.*;
import static java.lang.Thread.sleep;

class Actions {
    static boolean systemOn = true;
    static boolean loggedIn = false;
    static boolean inAccount = false;
    static Login login1;
    static Login login2 = null;
    static Account account1;
    static Account account2;
    static Scanner scan = new Scanner(System.in);
    public static void createAccount(LoginDao loginDao, AccountDao accountDao, Login login, String name, double balance) throws SQLException {
        loginDao.addLogin(login);
        Account account = new Account(login.getId(), name, balance);
        accountDao.addAccount(account);
    }
    public static boolean login(LoginDao loginDao, AccountDao accountDao) throws SQLException, InterruptedException {
        boolean validInput;
        int field = 3;
        double balance = 0.0;
        do {
            System.out.println("""
                    Welcome to Bank Simulator 1.0! What would you like to do?
                    \t1. Login to an existing account
                    \t2. Register for a new account
                    \t3. Quit Simulation""");
            try {
                System.out.print("\nEnter selection here: ");
                validInput = true;
                field = scan.nextInt();
                if (!(field == 1 || field == 2 || field == 3)) {
                    throw new InvalidInputException();
                }
            } catch (InvalidInputException e) {
                e.getMessage();
                validInput = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.\n");
                scan.nextLine(); //clears the scanner stack
                validInput = false;
            }
            wipeScreen();
        } while (!validInput);
        //option select
        String username = "", password = "", accountName;
        switch (field) {
            case 2:
                try {
                    System.out.print("Enter Username: ");
                    username = scan.next();
                    List<Login> currentAccounts = loginDao.getLogins();
                    for (Login check : currentAccounts) {
                        if(username.equalsIgnoreCase(check.getName())) {
                            validInput = false;
                            break;
                        }
                    }
                    if (!validInput) {
                        throw new UniqueAccountException();
                    }
                } catch (UniqueAccountException e) {
                    e.getMessage();
                }
                if(validInput) {
                    try {
                        System.out.print("Enter Password: ");
                        password = scan.next();
                        System.out.println("Are you sure? Type 'y' for yes or 'n' for no: ");
                        String check = scan.next();
                        if(check.equalsIgnoreCase("n")) {
                            throw new PasswordConfirmException();
                        }
                    } catch (PasswordConfirmException e) {
                        e.getMessage();
                    }
                    do {
                        try {
                            System.out.print("Select what amount you would like to deposit into your first account: ");
                            balance = scan.nextDouble();
                            if (balance < 0) {
                                throw new InvalidInputException();
                            }
                        } catch (InvalidInputException e) {
                            e.getMessage();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please try again.\n");
                            scan.nextLine(); //clears the scanner stack
                            validInput = false;
                        }
                    } while (!validInput);
                    System.out.print("Set up a name for your first account (or default for default): ");
                    accountName = scan.next();
                    login1 = new Login(username, password, true);
                    createAccount(loginDao, accountDao, login1, accountName, balance);
                    wipeScreen();
                }
                return false;
            case 1:
                try {
                    System.out.print("Enter Username: ");
                    username = scan.next();
                    System.out.print("Enter Password: ");
                    password = scan.next();
                    List<Login> validLogins = loginDao.getLogins();
                    for(Login login : validLogins) {
                        if(username.equalsIgnoreCase(login.getName()) && password.equalsIgnoreCase(login.getPassword())) {
                                login1 = new Login(login.getId(), username, password, login.getCustomer());
                                wipeScreen();
                                return true;
                        }
                    }
                    throw new InvalidLoginException();
                } catch (InvalidLoginException e) {
                    e.getMessage();
                    sleep(1500);
                    wipeScreen();
                    return false;
                }
            case 3:
                do {
                    try {
                        validInput = false;
                        System.out.print("Are you sure? Type 'y' for yes or 'n' for no: ");
                        String check = scan.next();
                        if (check.equalsIgnoreCase("y")) {
                            systemOn = false;
                            validInput = true;
                        } else if (check.equalsIgnoreCase("n")) {
                            validInput = true;
                            wipeScreen();
                        }
                        else {
                            throw new InvalidInputException();
                        }
                    } catch(InvalidInputException e) {
                        e.getMessage();
                    }
                } while (!validInput);
            default:
                return false;
        }
    }
    public static void AccountMenu(TransactionDao transactionDao, AccountDao accountDao, TransactionLogDao transactionLogDao, AccountLogDao accountLogDao, LoginDao loginDao) throws SQLException, InterruptedException {
        String name;
        boolean validInput;
        int field = 1;
        do {
            if (!Actions.login1.getCustomer()) {
                System.out.println("""
                        Greetings valued employee! What would you like to do now?
                        \t1. View existing accounts
                        \t2. Register for a new account
                        \t3. Approve or Reject new accounts
                        \t4. View Transaction Log
                        \t5. Log Out""");
            }
            else {
                System.out.println("""
                        Greetings valued customer! What would you like to do now?
                        \t1. View existing accounts
                        \t2. Register for a new account
                        \t3. Log Out""");
            }
            try {
                System.out.print("\nEnter selection here: ");
                validInput = true;
                field = scan.nextInt();
                if (!((!Actions.login1.getCustomer() && field >=1 && field <= 5 ) || (field >= 1 && field <= 3))) {
                    throw new InvalidInputException();
                }
            } catch (InvalidInputException e) {
                e.getMessage();
                validInput = false;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.\n");

                scan.next(); //clears the scanner stack
                field = 3;
                validInput = false;
            }
            sleep(100);
        } while (!validInput);
        List<Account> userAccounts = accountDao.getAccountsById(login1.getId());
        int[] accountIndex = new int[userAccounts.size()];
        switch(field) {
            case 1:
                wipeScreen();
                field = userAccounts.size() + 1;
                int count = 0;
                while (field != count) {
                    count = 0;
                    for (Account account : userAccounts) {
                        accountIndex[count] = account.getAcctId();
                        System.out.println((++count) + ". " + account.getName());
                    }
                    System.out.println(++count + ". Return to main menu.");
                    System.out.println("\nWhich account would you like to access?");
                    do {
                        try {
                            System.out.print("\nEnter selection here: ");
                            validInput = true;
                            field = scan.nextInt();
                            if (field < 0 || field > userAccounts.size() + 1) {
                                throw new InvalidInputException();
                            }
                            wipeScreen();
                        } catch (InvalidInputException e) {
                            e.getMessage();
                            validInput = false;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input. Please try again.");
                            scan.next();
                        }
                    } while (!validInput);
                    if (field < count) {
                        account1 = accountDao.getAccountById(login1.getId(), accountIndex[field-1]);
                        AccountOptions(transactionDao, accountDao, transactionLogDao, loginDao);
                    }
                }
                break;
            case 2:
                java.util.Date dt;
                var sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date;
                System.out.print("What would you like to name this new account: ");
                name = scan.next();
                double amount = 0;
                try {
                    for (Account account : userAccounts) {
                        if (Objects.equals(name, account.getName())) {
                            throw new UniqueAccountException();
                        }
                    }
                    System.out.print("How much would you like to deposit into this account to start: ");
                    amount = scan.nextDouble();
                    if (amount < 0) {
                        amount = 0;
                        throw new InvalidInputException();
                    }
                } catch (UniqueAccountException e) {
                    e.getMessage();
                } catch (InvalidInputException e1 ) {
                    e1.getMessage();
                } catch (InputMismatchException e2) {
                    System.out.println("Invalid input. Please try again later.");
                    scan.next();
                }
                if(amount > 0) {
                    dt = new java.util.Date();
                    date = sdf.format(dt);
                    Account account = new Account(login1.getId(), name, amount);
                    accountDao.addAccount(account);
                    accountLogDao.addAccountLog(new AccountLog(login1.getId(),account.getAcctId(), amount, date));
                } else {
                    System.out.println("Account not created. Returning to previous menu.");
                }
                wipeScreen();
                break;
            case 3:
                if(Actions.login1.getCustomer()) {
                    loggedIn = false;
                    break;
                }
                else {
                    List<Account> pendingAccounts = accountDao.getPendingAccounts();
                    for(Account account : pendingAccounts) {
                        System.out.print(account.getName() + " with an initial balance of " + BigDecimal.valueOf(account.getBalance()).setScale(2, RoundingMode.UNNECESSARY ) + ". Do you approve of this account?" +
                                "\nEnter selection here (Type 'y' for yes or 'n' for no): ");
                        String check = scan.next();
                        if(check.equalsIgnoreCase("y"))
                        {
                            account.setPending(false);
                            accountDao.updateAccount(account);
                        } else if(check.equalsIgnoreCase("n")) {
                            accountDao.deleteAccount(account.getUserId(), account.getAcctId());
                        }
                        wipeScreen();
                    }
                    if(pendingAccounts.size() == 0) {
                        System.out.println("No accounts to evaluate at this time.\n\n\n");
                        sleep(3000);
                    }
                }
                break;
            case 4:
                do {
                    System.out.println("What would you like to view?\n" + """
                            \t1. All transactions
                            \t2. All account actions
                            \t3. All actions
                            \t4. Return to previous menu""");
                    try {
                        System.out.print("\nEnter selection here: ");
                        validInput = true;
                        field = scan.nextInt();
                        if (!(field == 1 || field == 2 || field == 3 || field == 4)) {
                            throw new InvalidInputException();
                        }
                    } catch (InvalidInputException e) {
                        e.getMessage();
                        validInput = false;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please try again.\n");
                        scan.nextLine(); //clears the scanner stack
                        validInput = false;
                    }
                }while(!validInput);
                try {
                    switch (field) {
                        case 1 -> {
                            wipeScreen();
                            List<TransactionLog> list = transactionLogDao.getTransactionLog();
                            for (TransactionLog transactionLog : list) {
                                System.out.println(transactionLog.toString());
                            }
                            sleep(2000);
                        }
                        case 2 -> {
                            wipeScreen();
                            List<AccountLog> history = accountLogDao.getAccountLog();
                            for (AccountLog accountLog : history) {
                                System.out.println(accountLog.toString());
                            }
                            sleep(2000);
                        }
                        case 3 -> LogDisplay(); //pet project
                        default -> wipeScreen();
                    }
                } catch (NullPointerException e) {
                    System.out.println("That log is currently unavailable. Please try again later.");
                    sleep(1500);
                }
                break;
            case 5:
                loggedIn = false;
        }
        wipeScreen();
    }
    public static void AccountOptions(TransactionDao transactionDao, AccountDao accountDao, TransactionLogDao transactionLogDao, LoginDao loginDao) throws SQLException, InterruptedException {
        boolean validInput;
        int field = 1;
        inAccount = true;
        do {
            System.out.println("What would like to do with " + account1.getName() + "?\n" + """
                    \t1. View account information
                    \t2. Make a transaction
                    \t3. View Pending Transfers
                    \t4. Return to accounts""");
            do {
                try {
                    System.out.print("\nEnter selection here: ");
                    validInput = true;
                    field = scan.nextInt();
                    if (field < 1 || field > 4) {
                        throw new InvalidInputException();
                    }
                } catch (InvalidInputException e) {
                    e.getMessage();
                    validInput = false;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please try again.");
                    scan.next();
                    validInput = false;
                }
            } while (!validInput);
            switch (field) {
                case 1 -> {
                    String output = (account1.isPending()) ? "NO" : "YES";
                    System.out.println("     Account Name : " + account1.getName() +
                            "\n  Current Balance : " + BigDecimal.valueOf(account1.getBalance()).setScale(2, RoundingMode.UNNECESSARY) +
                            "\nAccount Activated?: " + output);
                    sleep(1000);
                    wipeScreen();
                }
                case 2 -> Transaction(transactionDao, accountDao, transactionLogDao, loginDao);
                case 3 -> {
                    wipeScreen();
                    var sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date;
                    List<Transaction> pendingAccounts = transactionDao.getPendingTransactions(login1.getId());
                    System.out.println("Which would you like to view?\n" + """
                            \t1. Sent Pending Transactions
                            \t2. Incoming Pending Transactions
                            \t3. Return to previous menu""");
                    do {
                        try {
                            System.out.print("\nEnter selection here: ");
                            validInput = true;
                            field = scan.nextInt();
                            if (field < 1 || field > 3) {
                                throw new InvalidInputException();
                            }
                        } catch (InvalidInputException e) {
                            e.getMessage();
                            validInput = false;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please try again.");
                            scan.next();
                            validInput = false;
                        }
                    } while (!validInput);
                    if (pendingAccounts.size() > 0) {
                        if(field == 1) {
                            List<Transaction> sentTransactions = pendingAccounts.stream().filter(s -> login1.getId() == s.getUserId1()).toList();
                            for (Transaction s : sentTransactions) {
                                try {
                                    System.out.println(s.toString(login1.getId()));
                                } catch (SQLException e) {
                                    System.out.println("-- Sorry. There was an error trying to process this transaction. Please try again later. --");
                                }
                            }
                            sleep(2000);
                            wipeScreen();
                        } else if (field == 2) {
                            List<Transaction> incomingTransactions = pendingAccounts.stream().filter(s -> login1.getId() == s.getUserId2()).toList();
                            for (Transaction transaction : incomingTransactions) {
                                System.out.print("A transfer of " + transaction.getBalance() + " from " + loginDao.getLoginById(transaction.getUserId1()).getName() + ". Do you approve of this transfer?" +
                                        "\nEnter selection here (Type 'y' for yes or 'n' for no): ");
                                String check = scan.next();
                                java.util.Date dt = new java.util.Date();
                                date = sdf.format(dt);
                                if (check.equalsIgnoreCase("y")) {
                                    double amount = -transaction.getBalance();
                                    account2 = accountDao.getAccountById(transaction.getUserId1(), transaction.getAcctId1());
                                    account2.setBalance(account2.getBalance() + amount);
                                    accountDao.updateAccount(account2);
                                    transactionLogDao.addTransactionLog(new TransactionLog(account2.getUserId(), account2.getAcctId(), transaction.isValid(), amount, date));
                                    account1.setBalance(account1.getBalance() + transaction.getBalance());
                                    accountDao.updateAccount(account1);
                                    transactionLogDao.addTransactionLog(new TransactionLog(account1.getUserId(), account1.getAcctId(), transaction.isValid(), transaction.getBalance(), date));
                                    wipeScreen();
                                } else if (check.equalsIgnoreCase("n")) {
                                    transactionDao.deleteTransaction(transaction);
                                    wipeScreen();
                                }
                            }
                        } else {
                            wipeScreen();
                        }
                    } else {
                        System.out.println("No current pending transactions.\n\n\n");
                        sleep(1500);
                        wipeScreen();
                    }
                }
                case 4 ->  {
                    inAccount = false;
                    wipeScreen();
                }
            }
        } while(inAccount);
    }
    public static void Transaction(TransactionDao transactionDao, AccountDao accountDao, TransactionLogDao transactionLogDao, LoginDao loginDao) throws SQLException, InterruptedException {
        var sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date;
        boolean validTransaction = true;
        boolean validInput;
        int field = 1;
        double amount = 0.0;
        wipeScreen();
        System.out.println("""
                What would you like to do?
                \t1. Deposit into this account
                \t2. Withdraw from this account
                \t3. Transfer to another account
                \t4. Return to Previous Menu""");
        do {
            try {
                System.out.print("\nEnter selection here: ");
                validInput = true;
                field = scan.nextInt();
                if(field < 1 || field > 4) {
                    throw new InvalidInputException();
                }
            }catch (InputMismatchException | InvalidInputException e) {
                System.out.println("Invalid input. Please try again.");
                scan.next();
                validInput = false;
            }
        } while(!validInput);
        wipeScreen();
        java.util.Date dt;
        String newName;
        switch(field) {
            case 1:
                    System.out.println("How much would you like to deposit?");
                    try {
                        System.out.print("\nEnter amount here: ");
                        amount = scan.nextDouble();
                        validTransaction = !(amount < 0) && ((amount % 0.01) == 0);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please try again.");
                        scan.nextLine();
                    }
            case 2:
                if(field == 2 && !account1.isPending()) {
                    System.out.print("How much would you like to withdraw?");
                    try {
                        System.out.print("\nEnter amount here: ");
                        amount = scan.nextDouble() * -1;
                        if (amount < 0 || amount > account1.getBalance() || (amount % 0.01 != 0)) {
                            validTransaction = false;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please try again.");
                        scan.nextLine();
                    }
                }
            case 3:
                if(field == 3 && !account1.isPending()) {
                    boolean accountLinked = false;
                    do {
                        System.out.print("""
                                Who do you want to transfer to?

                                Enter their username here (or type -1 to cancel):\s""");
                        newName = scan.next();
                        List<Login> userAccounts = loginDao.getLogins();
                        for (Login login : userAccounts) {
                            if (newName.equalsIgnoreCase(login.getName())) {
                                login2 = new Login(login.getId(), login.getName(), login.getPassword(), login.getCustomer());
                                accountLinked = true;
                                break;
                            }
                        }
                        if (newName.equalsIgnoreCase("-1")) {
                            break;
                        }
                        if (!accountLinked) {
                            System.out.println("Invalid input. Please try again.");
                            wipeScreen();
                        }
                    } while (!accountLinked);
                    /*if (login1.equals(login2)) {
                        List<Account> myAccounts = accountDao.getAccountsById(login1.getId());
                        boolean validInput;
                        int count = 0, compare;
                        field = 1;

                        for (Account account : myAccounts) {
                            if(!account1.equals(account)) {
                                System.out.println((++count) + ". " + account.getName());
                            } else {
                                compare = count;
                            }
                        }
                        System.out.println(++count + ". Return to transaction menu.");
                        System.out.println("\nWhich account do you want to transfer to?");
                        do {
                            try {
                                System.out.print("\nEnter selection here: ");
                                validInput = true;
                                field = scan.nextInt();
                                if (field < 0 || field > userAccounts.size() + 1) {
                                    throw new InvalidInputException();
                                }
                            } catch (InvalidInputException e) {
                                e.getMessage();
                                validInput = false;
                            }
                        } while(!validInput);
                        if(field > )
                        account2 = accounts.get()
                    } else*/
                    if(!newName.equalsIgnoreCase("-1")) {
                        account2 = accountDao.getAccountById(login2.getId(), accountDao.getAccountsById(login2.getId()).get(0).getAcctId());
                        System.out.println("How much do you want to transfer to them?");
                        try {
                            System.out.print("\nEnter amount here (or type 0 to cancel): ");
                            amount = scan.nextDouble();
                            validTransaction = !(amount < 0) && !(amount > account1.getBalance()) && (amount % 0.01 == 0);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please try again later.");
                            scan.nextLine();
                        }
                    }
                }
                dt = new java.util.Date();
                date = sdf.format(dt);
                try {
                    if (account1.isPending()) {
                        throw new AccountPendingException();
                    }
                    if (login2 == null && amount != 0.0) {
                        account1.setBalance(account1.getBalance() + amount);
                        transactionDao.addTransaction(new Transaction(login1.getId(), account1.getAcctId(), false, validTransaction, amount));
                        accountDao.updateAccount(new Account(login1.getId(), account1.getAcctId(), account1.getName(), account1.getBalance(), account1.isPending()));
                        transactionLogDao.addTransactionLog(new TransactionLog(login1.getId(), account1.getAcctId(), validTransaction, amount, date));
                    } else if (amount != 0.0) {
                        transactionDao.addTransaction(new Transaction(login1.getId(), login2.getId(), account1.getAcctId(), account2.getAcctId(), true, true, amount));
                        account2 = null;
                        login2 = null;
                    }
                } catch (AccountPendingException e) {
                    e.getMessage();
                    sleep(1500);
                }
                wipeScreen();
        }
    }
    public static void LogDisplay() throws SQLException, InterruptedException {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from account_log union select * from transaction_log order by date";
        ResultSet resultSet = statement.executeQuery(sql);
        wipeScreen();
        while(resultSet.next()) {
            System.out.println("["+resultSet.getString(5)+"] User #" + resultSet.getInt(1) + " changed account #" + resultSet.getInt(2));
        }
        sleep(2000);
    }
}
public class Main {
    public static void main(String[] args) throws SQLException, InputMismatchException, InterruptedException {
        LoginDao loginDao = DaoFactory.getLoginDao();
        AccountDao accountDao = DaoFactory.getAccountDao();
        TransactionDao transactionDao = DaoFactory.getTransactionDao();
        AccountLogDao accountLogDao = DaoFactory.getAccountLogDao();
        TransactionLogDao transactionLogDao = DaoFactory.getTransactionLogDao();
        Connection connection = ConnectionFactory.getConnection();
        wipeDatabase(connection);

        Login login1 = new Login("username", "password", false);
        Actions.createAccount(loginDao, accountDao, login1, "Default", 0.0);

        Login login2 = new Login("guest", "password", true);
        Actions.createAccount(loginDao, accountDao, login2, "Default", 0.0);

        Actions.loggedIn = Actions.login(loginDao, accountDao);
        do {
            while(Actions.loggedIn) {
                Actions.AccountMenu(transactionDao, accountDao, transactionLogDao, accountLogDao, loginDao);
            }
            if(Actions.systemOn) Actions.loggedIn = Actions.login(loginDao, accountDao);
        } while(Actions.systemOn);
        wipeScreen();
        System.out.println("Goodbye...\n\n\n");
        sleep(1500);
        wipeScreen();
    }
}

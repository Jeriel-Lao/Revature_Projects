package com.revature.BankAppSimulator.SystemFiles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrintConsole {
    public static void wipeScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    public static void wipeDatabase(Connection connection) throws SQLException, InterruptedException {
        Statement statement = connection.createStatement();
        String sql = "call simulation_wipe()";
        statement.executeQuery(sql);
        System.out.println("Clearing database simulation...");
        Thread.sleep(1000);
        System.out.println("\nDone!");
        Thread.sleep(1000);
        wipeScreen();
    }
}

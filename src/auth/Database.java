package auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {
    private static final String dbConnectionURL = "jdbc:sqlserver://150.156.202.112:1433;databaseName=TutorialDB";

    public static Connection getDatabaseConnection() throws SQLException, FileNotFoundException {
        File creds = new File("creds.dat");
        Scanner sc = new Scanner(creds);
        String user = sc.nextLine();
        String password = sc.nextLine();
        sc.close();

        System.out.print("Connecting to database...");
        return DriverManager.getConnection(dbConnectionURL, user, password);
    }
}
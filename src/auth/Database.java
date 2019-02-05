package auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Scanner;

public class Database {
    private static final String dbConnectionURL = "jdbc:sqlserver://150.156.202.112:1433;databaseName=Inventory";

    public static Connection getDatabaseConnection() throws SQLException, IOException {
        File creds = new File("creds.dat");
        FileInputStream fis = new FileInputStream(creds);
//        Scanner sc = new Scanner(creds);

        byte[] encryptedData = new byte[fis.available()];

        for (int i = 0; fis.available() > 0; i++){
            encryptedData[i] = (byte) fis.read();
        }
        fis.close();

//        String encryptedPassword = sc.nextLine();
//        sc.close();

        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        String data = new String(decodedData, StandardCharsets.UTF_8);

        int nameSt = 4;
        int nameEnd = data.indexOf('&');
        int pwordSt = data.lastIndexOf('=');
        String user = data.substring(nameSt, nameEnd);
        String password = data.substring(pwordSt);
        String output = "user=" + user + "&pword=" + password;

        System.out.println(output);

        return getDatabaseConnection(user, password);
    }

    private static Connection getDatabaseConnection(String username, String password) throws SQLException {
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(dbConnectionURL, username, password);
    }
}
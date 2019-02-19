package auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

public class Database {
    private static final String dbConnectionURL = "jdbc:sqlserver://150.156.202.112:1433;databaseName=DogNETInventory";

    public static Connection getDatabaseConnection() throws SQLException, IOException {
        HashMap<String, String> creds = getCredentials();

        return getDatabaseConnection(creds.get("user"), creds.get("password"));
    }

    private static Connection getDatabaseConnection(String username, String password) throws SQLException {
        System.out.println("Connecting to database...");
        return DriverManager.getConnection(dbConnectionURL, username, password);
    }

    private static HashMap<String, String> getCredentials(){
        System.out.println("Getting credentials...");

        HashMap<String, String> creds = new HashMap<>();

        File sourceFile = new File("creds0.dat");
        Scanner sc = null;
        try {
            sc = new Scanner(sourceFile);
        } catch (FileNotFoundException e) {
            try {
                if (sourceFile.createNewFile()){
                    System.out.println("Login: ");
                    CredEncryption.writeCredsFile(sourceFile);
                    sc = new Scanner(sourceFile);
                } else {
                    System.out.println("Could not create new file.");
                    System.exit(0);
                }
            } catch (IOException e1) {
                System.out.println("File creation error.");
                e1.printStackTrace();
                System.exit(0);
            }
        }

        String encryptedData  = sc.nextLine();


        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        String data = new String(decodedData, StandardCharsets.UTF_8);


        int nameSt = 4;
        int nameEnd = data.indexOf('&');
        int pwordSt = data.lastIndexOf('=');
        String user = data.substring(nameSt + 1, nameEnd);
        String password = data.substring(pwordSt + 1);

        creds.put("user", user);
        creds.put("password", password);
        return creds;
    }
}
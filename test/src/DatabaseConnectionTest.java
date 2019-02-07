import auth.Database;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

/**
 * Test class; connects to MSSQL (T-SQL) server on sandbox server.
 * Functionality intended for use on server.
 */
public class DatabaseConnectionTest {
    public static void main(String[] args) {

        // Create a variable for the connection string.
        String dbConnectionURL = "jdbc:sqlserver://150.156.202.112:1433;databaseName=DogNETInventory";//NOT used here!
        int port = 2000;
//        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        try {
//            Class.forName(driver);
            Connection con = Database.getDatabaseConnection();
            System.out.println("Connected.\n");


            File sqlSrc = new File("commands.sql");
            Scanner sc = new Scanner(sqlSrc);
            System.out.println("Commands: ");
            FileWriter jsonOutput = new FileWriter("out.json");
            PrintWriter pw = new PrintWriter(jsonOutput);
            while (sc.hasNext()){
                Statement stmt = con.createStatement();

                String sql = sc.nextLine();
                System.out.println(sql);
                try {
                    if (stmt.execute(sql)) {
                        ResultSet rs = stmt.executeQuery(sql);
                        ResultSetMetaData rsmd = rs.getMetaData();

                        while (rs.next()) {

                            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//                                System.out.print(rsmd.getColumnName(i)+ ": " + rs.getString(i));
                                System.out.println(rs.getString(i));
                                pw.println(rs.getString(i));
                            }
//                System.out.println(rs.getString("Name") + " \t" + rs.getString("Location"));
                        }
                        rs.close();
                    } else {
                        System.out.println("Command produced no output.");
                    }
                } catch (SQLServerException e){
                    e.printStackTrace();
                }
                System.out.println("\nFinished\n");
            }

            pw.close();

        }
        // Handle any errors that may have occurred.
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

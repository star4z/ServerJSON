package auth;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;

public class CredEncryption {
    public static void main(String[] args) throws IOException {
        writeCredsFile(new File("creds0.dat"));
    }

    static void writeCredsFile(File file) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("(Entries cannot contain '=' or '&)");
        System.out.println("user: ");
        String user = sc.nextLine();
        System.out.println("pword: ");
        String pword = sc.nextLine();

        String output = "user=" + user + "&pword=" + pword;

        byte[] bytes = Base64.getEncoder().encode(output.getBytes());

        FileOutputStream writer = new FileOutputStream(file);
        writer.write(bytes);
    }
}

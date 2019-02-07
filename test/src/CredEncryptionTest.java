import auth.Database;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class CredEncryptionTest {
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, String> map = Database.getCredentials();
        System.out.println(map);
    }
}

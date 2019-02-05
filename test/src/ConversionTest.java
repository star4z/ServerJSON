import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Test class for converting between JSON and SQL
 */
public class ConversionTest {
    public static void main(String[] args) {
        File input = new File("out.json");
        try {
            Scanner sc = new Scanner(input);
            while (sc.hasNext()){
                String s = sc.nextLine();

                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = (JSONObject) array.get(i);
                    System.out.println(generateSQL(object));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String INSERT_STR = "INSERT INTO %s (%s) VALUES %s";
    private static String UPDATE_STR = "UPDATE %s SET %s=%s WHERE %s=%s"; //Usually "where UID=n"

    private static String databaseName = "dbo.Employees";

    //PUT style response
    private static String generateSQL(JSONObject object){
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(databaseName).append(" (");
        for (String key :
                object.keySet()) {
            sb.append(key);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        for (String key :
                object.keySet()) {
            Object o = object.get(key);
            if (o instanceof String){
                sb.append("\'");
                sb.append(o);
                sb.append("\'");
            } else {
                sb.append(o);
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        return sb.toString();
    }
}

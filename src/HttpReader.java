import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpReader {
    public static void read(HttpURLConnection httpCon) throws IOException {
        read(httpCon.getInputStream());
    }

    public static void read(InputStream is) throws IOException {
        System.out.println(toString(is));
    }

    public static String toString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result  = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}

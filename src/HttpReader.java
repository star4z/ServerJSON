import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

class HttpReader {
    static void readInput(HttpURLConnection httpCon) throws IOException {
        readInput(httpCon.getInputStream());
    }

    private static void readInput(InputStream is) throws IOException {
        System.out.println(toString(is));
    }

    static String toString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result  = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}

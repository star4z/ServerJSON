
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Console-based client for Server
 */
public class Client {
    //TODO: protect ILLEGAL_CHARACTERS from being inputted by user
    public static char[] ILLEGAL_CHARACTERS = new char[]{'{', '}', '[', ']', '/', '\\', ':', '#', ',', '?', '&', '=',
            '<', '>', '(', ')', '*', '^', '!', '~', '-', '|', ';', '%'}; //characters reserved by JSON and SQL standards

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8000/test");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        testRequest(url, "PUT");
        testRequest(url, "GET");
    }

    private static void testRequest(URL url, String requestMethod) throws IOException {
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

        switch (requestMethod) {
            case "GET":
                testGet(httpCon);
                break;
            case "PUT":
                testPut(httpCon);
                break;
            case "POST":
                testPost(httpCon);
                break;
            case "DELETE":
//              testDelete(httpCon);
                break;
            default:
                return;
        }

        HttpReader.readInput(httpCon);
    }

    private static void testGet(HttpURLConnection httpCon) throws IOException {
        httpCon.setRequestMethod("GET");
    }

    private static void testPut(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.setRequestMethod("PUT");

        httpURLConnection.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
        out.writeBytes("{\"acquired\":\"\",\"barcode\":-1,\"brand\":\"\",\"id\":-1,\"name\":\"Put test\",\"qr\":-1,\"room\":\"\",\"serial\":-1,\"type\":\"\"}");
        out.flush();
        out.close();


        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
    }

    private static void testPost(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.setRequestMethod("POST");

        httpURLConnection.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
        out.writeBytes("{\"acquired\":\"" + formatDate(System.currentTimeMillis()) +"\",\"barcode\":-1,\"brand\":\"\",\"id\":-1,\"name\":\"Push test\",\"qr\":-1,\"room\":\"\",\"serial\":-1,\"type\":\"\"}");
        out.flush();
        out.close();

        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
    }

    private static String formatDate(long timeInMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format(timeInMillis);
    }


}

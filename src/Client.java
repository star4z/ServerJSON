
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 * Console-based client for Server. Intended for testing purposes.
 */
public class Client {
    //TODO: protect ILLEGAL_CHARACTERS from being inputted by user
    public static char[] ILLEGAL_CHARACTERS = new char[]{'{', '}', '[', ']', '/', '\\', ':', '#', ',', '?', '&', '=',
            '<', '>', '(', ')', '*', '^', '!', '~', '-', '|', ';', '%'}; //characters reserved by JSON and SQL standards

    public static void main(String[] args) throws IOException {
        System.out.println("Testing localhost(0) or 150.156.202.112(1)?");
        Scanner sc = new Scanner(System.in);
        URL url;
        switch (sc.nextInt()) {
            case 0:
                url = new URL("http://localhost:8000/inventory");
                break;
            case 1:
                url = new URL("http://150.156.202.112:8000/inventory");
                break;
            default:
                url = new URL("http://150.156.202.112:8000/inventory");
                break;
        }

//        testRequest(url, "POST");
        testRequest(url, "PUT");
//        testRequest(url, "DELETE");
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
                testDelete(httpCon);
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
        createTestJson(httpURLConnection, "\",\"barcode\":-1,\"brand\":\"\",\"id\":-1,\"name\":\"Put test\",\"qr\":-1,\"room\":\"\",\"serial\":-1,\"type\":\"\"}");
    }

    private static void testPost(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.setRequestMethod("POST");

        createTestJson(httpURLConnection, "\",\"barcode\":-1,\"brand\":\"\",\"id\":-1,\"name\":\"Push test\",\"qr\":-1,\"room\":\"\",\"serial\":-1,\"type\":\"\"}");
    }

    private static void testDelete(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.setRequestMethod("DELETE");
        createTestJson(httpURLConnection, "\",\"barcode\":-1,\"brand\":\"\",\"id\":0,\"name\":\"Delete test\",\"qr\":-1,\"room\":\"\",\"serial\":-1,\"type\":\"\"}");
    }

    private static String formatDate(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format(timeInMillis);
    }


    private static void createTestJson(HttpURLConnection httpURLConnection, String s) throws IOException {
        httpURLConnection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
        out.writeBytes("{\"acquired\":\"" + formatDate(System.currentTimeMillis()) + s);
        out.flush();
        out.close();

        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
    }


}

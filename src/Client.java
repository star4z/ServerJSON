
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Console-based client for server
 */
public class Client {
    //TODO: protect ILLEGAL_CHARACTERS from being inputted by user
    public static char[] ILLEGAL_CHARACTERS = new char[]{'{', '}', '[', ']', '/', '\\', ':', '#', ',', '?', '&', '=',
            '<', '>', '(', ')', '*', '^', '!', '~', '-', '|', ';', '%'}; //characters reserved by JSON and SQL standards

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8000/test");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("GET");
        httpCon.setDoOutput(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "Ben");

        DataOutputStream out = new DataOutputStream(httpCon.getOutputStream());
        out.writeBytes(getParamsString(parameters));
        out.flush();
        out.close();

        httpCon.setConnectTimeout(5000);
        httpCon.setReadTimeout(5000);

        int status = httpCon.getResponseCode();
        HttpReader.readInput(httpCon);

        System.out.println(httpCon.getRequestMethod());

//        OutputStreamWriter out = new OutputStreamWriter(
//                httpCon.getOutputStream());
//        out.write("output stream");
//        out.close();

    }

    private static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}



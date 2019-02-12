import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Handles HTTP GET, PUT, POST, and DELETE requests from Server
 */
class JsonRequestHandler {
    private final String databaseName = "dbo.Inventory";

    private final String sqlAutoJson = "FOR JSON AUTO;\n";

    private final String fieldEquals = "%s = %s";
    private final String and = " AND ";

    private String requestMethod;
    private String body;

    JsonRequestHandler(HttpExchange httpExchange) throws IOException {
        this(httpExchange.getRequestMethod(), HttpReader.toString(httpExchange.getRequestBody()));
    }

    private JsonRequestHandler(String requestMethod, String body) {
        this.requestMethod = requestMethod;
        this.body = body;


        System.out.println("Received request " + requestMethod + ":\n" + body);
    }

    String getSql() {
        switch (requestMethod) {
            case "GET":
                return handleGet(body);
            case "PUT":
                return handlePut(body);
            case "POST":
                return handlePost(body);
            case "DELETE":
                return handleDelete(body);
            default:
                return null;
        }
    }

    private String handleGet(String body) {
        String sqlString = "SELECT * FROM " + databaseName + " FOR JSON AUTO;\n";

        if (body.isEmpty()) {
            return sqlString;
        } else {
            int queryStartPos = body.indexOf('?');
            String queries = body.substring(queryStartPos + 1);
            String[] keyValPairs = queries.split("&");

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < keyValPairs.length; i++) {
                String[] duple = keyValPairs[i].split("=");
                sb.append(String.format(fieldEquals, duple[0], duple[1]));
                if (i + 1 < keyValPairs.length) {
                    sb.append(and);
                }
            }

            sb.append(sqlAutoJson);

            String fillInGet = "SELECT * FROM " + databaseName + " WHERE %s FOR JSON AUTO;";
            return String.format(fillInGet, sb.toString());
        }
    }

    private String handlePut(String body) {
        String ifNotExists = "IF NOT EXISTS (SELECT * FROM " + databaseName + " WHERE ";
        String insert = "INSERT INTO " + databaseName ;
        String values = " VALUES ";

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(body);
        } catch (JSONException e){
            //If only one object is sent, converts it to an array
            jsonArray = new JSONArray('[' + body + ']');
        }


        HashSet<String> keyset = new HashSet<>(((JSONObject) jsonArray.get(0)).keySet());
        ArrayList<String> outputStrings = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        for (Object object: jsonArray){
            JSONObject jsonObject = (JSONObject) object;

            ids.add(jsonObject.getInt("id"));
            outputStrings.add(listValues(jsonObject));
        }

        StringBuilder resultBuilder = new StringBuilder(ifNotExists);

        for (int id: ids){
            resultBuilder.append("id = ");
            resultBuilder.append(id);
            resultBuilder.append(" OR ");
        }

        resultBuilder.replace(resultBuilder.length() - 4, resultBuilder.length(), "");
        resultBuilder.append(")");

        resultBuilder.append(insert);
        resultBuilder.append(" ");
        resultBuilder.append(listKeys(keyset));

        resultBuilder.append(values);

        for (String s: outputStrings){
            resultBuilder.append(s);
        }

        resultBuilder.replace(resultBuilder.length() - 2, resultBuilder.length(), "");

        return resultBuilder.toString();
    }

    private String listKeys(HashSet<String> keySet){
        StringBuilder s = new StringBuilder("(");
        for (String key: keySet){
            s.append(key);
            s.append(", ");
        }
        s.replace(s.length() - 2, s.length(), "");
        s.append(")");
        return s.toString();
    }

    private String listValues(JSONObject jsonObject){
        StringBuilder stringBuilder = new StringBuilder("(");

        for (String key: jsonObject.keySet()){
            Object p = jsonObject.get(key);

            if (p instanceof String){
                stringBuilder.append("'");
                stringBuilder.append(p);
                stringBuilder.append("', ");
            } else {
                stringBuilder.append(p);
                stringBuilder.append(", ");
            }
        }

        stringBuilder.replace(stringBuilder.length() -2, stringBuilder.length(), "");
        stringBuilder.append("), ");
        return stringBuilder.toString();
    }

    private String handlePost(String body) {
        //TODO: should not check if it exists
        return handlePut(body);
    }

    private String handleDelete(String body) {
        String result = null;
        //TODO: fill out method

        return result;
    }
}

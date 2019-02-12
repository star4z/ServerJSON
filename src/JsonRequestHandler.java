import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;
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
        String result = null;
        //TODO: fill out method

        String ifNotExists = "IF NOT EXISTS (SELECT * FROM " + databaseName + " WHERE ";
        String insert = "INSERT INTO ";
        String values = " VALUES ";

        JSONArray jsonArray = new JSONArray(body);


        HashSet<String> keyset = new HashSet<>(((JSONObject) jsonArray.get(0)).keySet());

        ArrayList<String> outputStrings = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Object object: jsonArray){
            JSONObject jsonObject = (JSONObject) object;

            StringBuilder s = new StringBuilder("(");

            for (String key:keyset){
                if (key.equals("id")) {
                    ids.add(jsonObject.getInt(key));
                }

                Object p = jsonObject.get(key);
                try {
                    int i = ((Integer) p);
                    s.append(i);

                } catch (ClassCastException e1) {
                    try {
                        double d = ((double) p);
                        s.append(d);
                    } catch (ClassCastException e2){
                        try {
                            String s1 = (String) p;
                            s.append("'");
                            s.append(s1);
                            s.append("'");
                        } catch (ClassCastException e3) {

                        }
                    }
                }
                s.append(", ");
            }
        }

        return result;
    }

    private String handlePost(String body) {
        return handlePut(body);
    }

    private String handleDelete(String body) {
        String result = null;
        //TODO: fill out method

        return result;
    }
}

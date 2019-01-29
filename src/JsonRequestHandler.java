import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Handles HTTP GET, PUT, POST, and DELETE requests from Server
 */
class JsonRequestHandler {
    private final String databaseName = "dbo.Employees";

    private final String sqlAutoJson = "FOR JSON AUTO;\n";

    private final String fillInGet = "SELECT * FROM " + databaseName + " WHERE %s FOR JSON AUTO;";
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

    String handleGet(String body) {

        String sqlString = "SELECT * FROM dbo.Employees FOR JSON AUTO;\n";
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

            return String.format(fillInGet, sb.toString());
        }
    }

    String handlePut(String body) {
        String result = null;
        //TODO: fill out method
        return result;
    }

    String handlePost(String body) {
        String result = null;
        //TODO: fill out method

        return result;
    }

    String handleDelete(String body) {
        String result = null;
        //TODO: fill out method

        return result;
    }
}

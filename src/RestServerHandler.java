import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Handles server responses.
 */
class RestServerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Query was not valid."; //Invalid request method response

        JsonRequestHandler jsonHandler = new JsonRequestHandler(httpExchange);

        String sqlString = jsonHandler.getSql();


        if (sqlString == null) {
            System.out.println("Bad request received.");
        } else {
            System.out.println("Sending this SQL output: \n" + sqlString);
            SqlHandler sqlHandler = new SqlHandler();
            response = sqlHandler.handleSQL(sqlString);
        }

        System.out.println("Request was handled.");

        assert response != null;

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }



}
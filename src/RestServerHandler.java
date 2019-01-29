import auth.Database;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

/**
 * Handles server responses.
 */
class RestServerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "This is the default response"; //Invalid request method response


        JsonRequestHandler jsonHandler = new JsonRequestHandler(httpExchange);

        String sqlString = jsonHandler.getSql();

        if (sqlString == null) {
            System.out.println("Bad request received.");
        } else {
            System.out.println("Sending this SQL output: \n" + sqlString);
            response = handleSQL(sqlString);
        }


        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    String handleSQL(String SQLstring) {
        try {
            Connection connection = Database.getDatabaseConnection();
            System.out.println("Connected.\n");

            ResultSet resultSet = sendSQL(connection, SQLstring);

            return sqlToJson(resultSet);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    ResultSet sendSQL(Connection connection, String sqlitis) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sqlitis);
        return statement.getResultSet();
    }

    /**
     * Parses a ResultSet in order to obtain a JSON string
     * Relies on T-SQL's AUTO JSON functionality
     *
     * @param rs Response from a sql request
     * @return String
     * @throws SQLException
     */
    String sqlToJson(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        StringBuilder sb = new StringBuilder();

        while (rs.next()) {
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                sb.append(rs.getString(i));
            }
        }

        return sb.toString();
    }

}
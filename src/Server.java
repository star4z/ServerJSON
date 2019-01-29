import auth.Database;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.sql.*;

public class Server {

    //Thought: use GSON (Google's JSON parser) since using Android?

    private static final String dbConnectionURL = "jdbc:sqlserver://150.156.202.112:1433;databaseName=TutorialDB";
    private static String hostname = "localhost"; //"150.156.202.112";
    private static int port = 2000;
    private static ServerSocket server;

    private static final int SOCKET_PORT = 8000;

    /**
     * starts the server process
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(SOCKET_PORT), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Started server.");
    }

    /**
     * Handles server responses.
     *
     */
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "This is the default response"; //Invalid request method response

            String body = HttpReader.toString(httpExchange.getRequestBody());



            JsonRequestHandler jsonHandler = new JsonRequestHandler(httpExchange);

            String sqlString = jsonHandler.getSql();

            if (sqlString == null){
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


        String handleSQL(String SQLstring){
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
}
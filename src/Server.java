import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
//import java.net.ServerSocket;

/**
 * Starts server, which calls handler upon receipt of http request.
 *
 * GET returns all items as a JSON
 * PUT inserts the item.
 * POST inserts the item if it doesn't exist, otherwise updates it.
 * DELETE deletes the item with the given id.
 *
 * NOTE: PUT and POST will create a new ID for any item with an id of -1.
 */
public class Server {

    //Thought: use GSON (Google's JSON parser) since using Android?

//    private static final String dbConnectionURL = "jdbc:sqlserver://150.156.202.112:1433;databaseName=Inventory";
//    private static String hostname = "localhost"; //"150.156.202.112";
//    private static int port = 2000;
//    private static ServerSocket server;

    private static final int SOCKET_PORT = 8000;

    /**
     * starts the server process
     *
     * @param args unused input
     * @throws IOException Bad reads and connections may happen.
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(SOCKET_PORT), 0);
        server.createContext("/inventory", new RestServerHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Started server.");
    }
}
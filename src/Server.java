import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

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
     * @param args unused input
     * @throws IOException Bad reads and connections may happen.
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(SOCKET_PORT), 0);
        server.createContext("/test", new RestServerHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Started server.");
    }
}
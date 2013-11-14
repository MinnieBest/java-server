import java.net.*;
import java.io.*;

public class Server {
    public ServerSocket serverSocket;
    public String directory;
    public int port;

    public Server(int port, String directory) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.directory = directory;
    }

    public void start() {
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandler(socket, directory)).start();
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String [] args) throws IOException {
        Arguments arguments = new Arguments(args);
        Server server = new Server(arguments.port(), arguments.directory());
        server.start();
    }
}

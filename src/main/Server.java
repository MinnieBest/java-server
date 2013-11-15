import java.net.*;
import java.io.*;

public class Server {

    public int port;
    public ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new SocketHandler(socket)).start();
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

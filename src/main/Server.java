import java.net.*;
import java.io.*;

public class Server {

    public int port;
    public ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = makeServerSocket();
    }

    public void start() {
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(makeSocketHandler(socket)).start();
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public ServerSocket makeServerSocket() throws IOException {
        return new ServerSocket(port);
    }

    public SocketHandler makeSocketHandler(Socket socket) throws IOException {
        return new SocketHandler(socket);
    }
}

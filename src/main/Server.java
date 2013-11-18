import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public int port;
    public ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = makeServerSocket();
    }

    public void start() {
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        ExecutorService executor = Executors.newFixedThreadPool(8);
        while(!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                executor.execute(newThread(socket));
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

    public Runnable newThread(Socket socket) throws IOException {
        return new Thread(makeSocketHandler(socket));
    }
}

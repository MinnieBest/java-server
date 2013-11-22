import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public int port;
    public Callable app;
    public ServerSocket serverSocket;

    public Server(int port, Callable app) throws IOException {
        this.port = port;
        this.app = app;
        this.serverSocket = makeServerSocket();
    }

    public void start() {
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
        return new SocketHandler(socket, app);
    }

    public Runnable newThread(Socket socket) throws IOException {
        return new Thread(makeSocketHandler(socket));
    }
}

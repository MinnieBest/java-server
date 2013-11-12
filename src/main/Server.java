import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new RequestHandler(socket)).start();
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String [] args) throws IOException {
        int port = 5000;
        if (args.length > 0) {
            port = Integer.parseInt(new OptionParser(args).getFlagValue("-p"));
        }
        Server server = new Server(port);
        server.start();
    }
}

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws IOException {
        Arguments arguments = new Arguments(args);
        RequestHandler app = new RequestHandler(arguments.directory());
        SocketHandler.app = new RequestLogger(app);
        Server server = new Server(arguments.port());
        server.start();
    }
}

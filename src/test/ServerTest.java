import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.io.*;
import java.net.*;

@RunWith(JUnit4.class)
public class ServerTest {

    @Test
    public void initsWithAPortAndDirectory() throws IOException {
        Server server = new Server(5000, "/test");
        server.serverSocket.close();
        assertEquals(server.port, 5000);
        assertEquals(server.directory, "/test");
    }

    @Test
    public void createsServerSocket() throws IOException {
        Server server = new Server(5000, "/test");
        server.serverSocket.close();
        assertEquals(server.serverSocket.getClass(), ServerSocket.class);
    }
}

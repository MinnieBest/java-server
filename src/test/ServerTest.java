import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.io.*;
import java.net.*;
import static org.mockito.Mockito.*;
import kevin.server.*;

@RunWith(JUnit4.class)
public class ServerTest {

    @Test
    public void initsWithAPort() throws IOException {
        Server server = new Server(5000, mock(Callable.class));
        server.serverSocket.close();
        assertEquals(server.port, 5000);
    }

    @Test
    public void initsWithApp() throws IOException {
        Server server = new Server(5000, mock(Callable.class));
        server.serverSocket.close();
        assertNotNull(server.app);
    }

    @Test
    public void createsServerSocket() throws IOException {
        Server server = new Server(5000, mock(Callable.class));
        server.serverSocket.close();
        assertEquals(server.serverSocket.getClass(), ServerSocket.class);
    }

    @Test
    public void createsSocketHander() throws IOException {
        Server server = new Server(5000, mock(Callable.class));
        server.serverSocket.close();
        assertNotNull(server.makeSocketHandler(new Socket()));
    }

    @Test
    public void acceptsOnSocket() throws IOException {
        Server spyServer = spy(new Server(5000, mock(Callable.class)));
        spyServer.serverSocket.close();
        ServerSocket mockServerSocket = mock(ServerSocket.class);
        spyServer.serverSocket = mockServerSocket;
        Socket mockSocket = mock(Socket.class);
        Thread mockThread = mock(Thread.class);
        when(mockServerSocket.accept()).thenReturn(mockSocket);
        when(mockServerSocket.isClosed()).thenReturn(false, true);
        when(spyServer.makeServerSocket()).thenReturn(mockServerSocket);
        when(spyServer.newThread(mockSocket)).thenReturn(mockThread);

        spyServer.start();
        verify(mockServerSocket).accept();
        verify(mockThread).run();
    }
}

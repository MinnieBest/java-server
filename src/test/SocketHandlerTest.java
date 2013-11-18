import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.net.*;
import java.io.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class SocketHandlerTest {

    private Socket mockSocket = mock(Socket.class);
    private SocketHandler handler = new SocketHandler(mockSocket);
    private Callable mockApp = mock(Callable.class);
    private Response response = mock(Response.class);
    private InputStream input = new ByteArrayInputStream("GET / HTTP/1.1\n".getBytes());
    private OutputStream output = mock(ByteArrayOutputStream.class);

    @Before
    public void initialize() throws IOException {
        when(mockSocket.getInputStream()).thenReturn(input);
        when(mockSocket.getOutputStream()).thenReturn(output);

        SocketHandler.app = mockApp;

        when(mockApp.call(any(Request.class))).thenReturn(response);

        when(response.output()).thenReturn("Testing".getBytes());

        final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @Test
    public void initsWithASocket() {
        assertNotNull(handler.socket);
    }

    @Test
    public void getsInputStream() throws IOException {
        handler.run();
        verify(mockSocket).getInputStream();
    }

    @Test
    public void getsOutputStrean() throws IOException {
        handler.run();
        verify(mockSocket).getOutputStream();
    }

    @Test
    public void createsRequest() throws IOException {
        Request request = handler.makeRequest(new ByteArrayInputStream("GET / HTTP/1.1\n".getBytes()));
        assertEquals("GET", request.method);
    }

    @Test
    public void callsApp() {
        handler.run();
        verify(mockApp).call(any(Request.class));
    }

    @Test
    public void writesOutput() throws IOException {
        handler.run();
        verify(output).write("Testing".getBytes());
    }

    @Test
    public void flushesOutput() throws IOException {
        handler.run();
        verify(output).flush();
    }

    @Test
    public void closesOutput() throws IOException {
        handler.run();
        verify(output).close();
    }

    @Test
    public void closesSocket() throws IOException {
        handler.run();
        verify(mockSocket).close();
    }

    @Test
    public void parsesInputStream() throws IOException {
        InputStream input = new ByteArrayInputStream("Testing the input parser".getBytes());
        assertEquals("Testing the input parser", SocketHandler.inputString(input));
    }
}


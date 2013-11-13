import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.net.*;
import java.io.*;

@RunWith(JUnit4.class)
public class RequestHandlerTest {

    @Test
    public void initsWithASocket() {
        RequestHandler handler = new RequestHandler(new Socket());
        assertEquals(handler.socket.getClass(), Socket.class);
    }

    @Test
    public void parsesInputStream() throws IOException {
        InputStream input = new ByteArrayInputStream("Testing the input parser".getBytes());
        String output = RequestHandler.getStringFromInputStream(input);
        assertEquals("Testing the input parser", output);
    }
}


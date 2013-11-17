import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class FileControllerTest {

    public Request fullRequest = new Request("GET /test.txt HTTP/1.1");
    public Request partialRequest = new Request("GET /test.txt HTTP/1.1\r\nRange: bytes0-4\r\n");
    public FileController controller = new FileController("resources");

    @Test
    public void returnsFullFile() {
        assertEquals(200, controller.send(fullRequest).status);
    }

    @Test
    public void returnsPartiaFile() {
        assertEquals(206, controller.send(partialRequest).status);
    }
}

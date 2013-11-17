import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class LogsControllerTest {

    public Request unauthorizedRequest = new Request("GET /logs HTTP/1.1\r\nAuthorization: Basic wrong:password");
    public Request authorizedRequest = new Request("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\n");
    public LogsController controller = new LogsController(new ArrayList<String>());

    @Test
    public void returnsUnauthorized() {
        assertEquals(401, controller.send(unauthorizedRequest).status);
    }

    @Test
    public void returnsLogs() {
        assertEquals(200, controller.send(authorizedRequest).status);
    }
}

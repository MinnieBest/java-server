import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ControllerTest {

    public Request simpleGet = new Request("GET / HTTP/1.1");
    public Request options = new Request("OPTIONS / HTTP/1.1");
    public Controller controller = new Controller();

    @Test
    public void returnsNotAllowed() {
        assertEquals(405, controller.send(simpleGet).status);
    }

    @Test
    public void returnsOptions() {
        assertEquals(200, controller.send(options).status);
    }
}

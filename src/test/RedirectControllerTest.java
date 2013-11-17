import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RedirectControllerTest {

    public Request redirect = new Request("GET /redirect HTTP/1.1");
    public RedirectController controller = new RedirectController();

    @Test
    public void returnsRedirect() {
        assertEquals(301, controller.send(redirect).status);
    }
}

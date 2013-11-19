import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class RedirectControllerTest {

    public Request request = mock(Request.class);
    public RedirectController controller = new RedirectController();

    @Test
    public void returnsRedirect() {
        request.route = "/redirect";
        request.method = "GET";
        assertEquals(301, controller.send(request).status);
    }
}

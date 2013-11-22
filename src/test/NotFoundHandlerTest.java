import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class NotFoundHandlerTest {

    public Request notFoundRequest = mock(Request.class);

    private NotFoundHandler handler = new NotFoundHandler();

    @Before
    public void setup() {
        notFoundRequest.route = "/notHere";
        notFoundRequest.method = "GET";
    }

    @Test
    public void returnsNotFoundRespons() {
        assertEquals(404, handler.call(notFoundRequest).status);
    }
}

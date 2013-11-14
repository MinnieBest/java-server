import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RequestHandlerTest {

    public Request simpleRequest = new Request("GET / HTTP/1.1", "/public");
    public Request optionsRequest = new Request("OPTIONS / HTTP/1.1", "/public");
    public Request optionsRequestRoute = new Request("GET /method_options HTTP/1.1", "/public");

    @Test
    public void returnsResponseObject() {
        RequestHandler handler = new RequestHandler(simpleRequest);
        assertEquals(handler.call().getClass(), Response.class);
    }

    @Test
    public void returnsOptionsForOptionsRequest() {

    }
}

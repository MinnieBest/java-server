import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

@RunWith(JUnit4.class)
public class RequestHandlerTest {

    public Request simpleRequest = new Request("GET / HTTP/1.1", "/public");
    public Request optionsRequest = new Request("OPTIONS / HTTP/1.1", "/public");
    public Request optionsRequestRoute = new Request("GET /method_options HTTP/1.1", "/public");
    public Request redirectRequest = new Request("GET /redirect HTTP/1.1", "/public");

    @Test
    public void returnsResponseObject() {
        RequestHandler handler = new RequestHandler(simpleRequest);
        assertEquals(true, handler.call() instanceof Response);
    }

    @Test
    public void returnsOptionsForOptionsRequest() {
        RequestHandler handler = new RequestHandler(optionsRequest);
        assertThat(handler.call().headers.get(0), containsString("GET,HEAD,POST,OPTIONS,PUT"));
    }

    @Test
    public void returnsOptionsForOptionsRoute() {
        RequestHandler handler = new RequestHandler(optionsRequestRoute);
        assertThat(handler.call().headers.get(0), containsString("GET,HEAD,POST,OPTIONS,PUT"));
    }
}

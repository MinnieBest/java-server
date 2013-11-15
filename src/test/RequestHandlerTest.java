import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

@RunWith(JUnit4.class)
public class RequestHandlerTest {

    public Request simpleRequest = new Request("GET / HTTP/1.1");
    public Request optionsRequest = new Request("OPTIONS / HTTP/1.1");
    public Request optionsRequestRoute = new Request("GET /method_options HTTP/1.1");
    public Request redirectRequest = new Request("GET /redirect HTTP/1.1");

    private RequestHandler handler = new RequestHandler("/public");

    @Test
    public void initsWithDirectory(){
        assertEquals("/public", handler.baseDirectory);
    }

    @Test
    public void returnsResponseObject() {
        assertEquals(true, handler.call(simpleRequest) instanceof Response);
    }

    @Test
    public void returnsOptionsForOptionsRequest() {
        assertThat(handler.call(optionsRequest).headers.get(0), containsString("GET,HEAD,POST,OPTIONS,PUT"));
    }

    @Test
    public void returnsOptionsForOptionsRoute() {
        assertThat(handler.call(optionsRequestRoute).headers.get(0), containsString("GET,HEAD,POST,OPTIONS,PUT"));
    }
}

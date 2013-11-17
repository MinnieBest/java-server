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
    public Request fileRequest = new Request("GET /test.txt HTTP/1.1");
    public Request directoryRequest = new Request("GET /test HTTP/1.1");
    public Request notFoundRequest = new Request("GET /notHere HTTP/1.1");
    public Request noMethodRequest = new Request("PUT /test.txt HTTP/1.1");

    private RequestHandler handler = new RequestHandler("resources");

    @Test
    public void initsWithDirectory() {
        assertEquals("resources", handler.baseDirectory);
    }

    @Test
    public void createsNewForm() {
        assertNotNull(handler.form);
    }

    @Test
    public void createsLogs() {
        assertNotNull(handler.logs);
    }

    @Test
    public void addsToLogs() {
        handler.call(simpleRequest);
        assertEquals("GET / HTTP/1.1", handler.logs.get(0));
    }

    @Test
    public void filtersFileRoutes() {
        handler.call(fileRequest);
        assertEquals("file", handler.filterRoute());
    }

    @Test
    public void filtersDirectoryRoutes() {
        handler.call(directoryRequest);
        assertEquals("directory", handler.filterRoute());
    }

    @Test
    public void returnsResponseObject() {
        assertEquals(true, handler.call(simpleRequest) instanceof Response);
    }

    @Test
    public void returnsFileResponse() {
        assertEquals(true, handler.call(fileRequest) instanceof FileResponse);
    }

    @Test
    public void returnsDirectoryRespons() {
        assertEquals(true, handler.call(directoryRequest) instanceof TextResponse);
    }

    @Test
    public void returnsNotFoundRespons() {
        assertEquals(404, handler.call(notFoundRequest).status);
    }

    @Test
    public void returnsRedirect() {
        assertEquals(301, handler.call(redirectRequest).status);
    }

    @Test
    public void returnsMethodNotAllowed() {
        assertEquals(405, handler.call(noMethodRequest).status);
    }

    @Test
    public void returnsOptionsForOptionsRequest() {
        assertThat(handler.call(optionsRequest).headers.get("Allow"), containsString("GET,HEAD,POST,OPTIONS,PUT"));
    }

    @Test
    public void returnsOptionsForOptionsRoute() {
        assertThat(handler.call(optionsRequestRoute).headers.get("Allow"), containsString("GET,HEAD,POST,OPTIONS,PUT"));
    }
}

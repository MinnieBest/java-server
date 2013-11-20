import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class RequestHandlerTest {

    public Request simpleRequest = mock(Request.class);
    public Request optionsRequest = mock(Request.class);
    public Request optionsRequestRoute = mock(Request.class);
    public Request redirectRequest = mock(Request.class);
    public Request fileRequest = mock(Request.class);
    public Request directoryRequest = mock(Request.class);
    public Request notFoundRequest = mock(Request.class);
    public Request noMethodRequest = mock(Request.class);

    private RequestHandler handler = new RequestHandler("resources");

    @Before
    public void setup() {
        simpleRequest.route = "/";
        simpleRequest.method = "GET";

        optionsRequest.route = "/";
        optionsRequest.method = "OPTIONS";

        optionsRequestRoute.route = "/method_options";
        optionsRequestRoute.method = "GET";

        redirectRequest.route = "/redirect";
        redirectRequest.method = "GET";

        fileRequest.route = "/test.txt";
        fileRequest.method = "GET";
        fileRequest.range = new HashMap<String, Integer>();

        directoryRequest.route = "/test";
        directoryRequest.method = "GET";

        notFoundRequest.route = "/notHere";
        notFoundRequest.method = "GET";

        noMethodRequest.route = "/test.txt";
        noMethodRequest.method = "PUT";
    }

    @Test
    public void initsWithDirectory() {
        assertEquals("resources", handler.baseDirectory);
    }

    @Test
    public void createsRoutesMap() {
        assertEquals(true, handler.routes.get("/redirect") instanceof RedirectController);
    }

    @Test
    public void routesFile() {
        assertEquals(true, handler.routes.get("file") instanceof FileController);
    }

    @Test
    public void routesDirectory() {
        assertEquals(true, handler.routes.get("directory") instanceof DirectoryController);
    }

    @Test
    public void createsLogs() {
        assertNotNull(handler.logs);
    }

    @Test
    public void addsToLogs() {
        handler.call(simpleRequest);
        assertEquals(1, handler.logs.size());
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
        assertEquals(true, handler.call(fileRequest) instanceof Response);
    }

    @Test
    public void returnsDirectoryResponse() {
        assertEquals(true, handler.call(directoryRequest) instanceof Response);
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

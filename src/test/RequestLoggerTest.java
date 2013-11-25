import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.logging.Logger;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class RequestLoggerTest {

    private Callable app = mock(Callable.class);
    private Request request = mock(Request.class);
    private Response response = mock(Response.class);
    private Logger mockLogger = mock(Logger.class);
    private RequestLogger logger = new RequestLogger(app, "resources");

    @Before
    public void setup() {
        request.route = ("/");
        request.method = "GET";
        request.log = "Test";
    }

    @Test
    public void initsWithApp() {
        assertNotNull(logger.app);
    }

    @Test
    public void respondsToCall() {
        when(app.call(request)).thenReturn(response);
        assertEquals(response, logger.call(request));
    }

    @Test
    public void passesRequestToApp() {
        logger.call(request);
        verify(app).call(request);
    }

    @Test
    public void logsRequest() {
        request.log = "Test log";
        RequestLogger.logger = mockLogger;
        logger.call(request);
        verify(mockLogger).info("Test log");
    }

    @Test
    public void returnsUnauthorized() {
        request.route = "/logs";
        request.authorization = new HashMap<String, String>();
        request.authorization.put("Basic", "wrong:password");
        assertEquals(401, logger.call(request).status);
    }

    @Test
    public void returnsLogs() {
        request.route = "/logs";
        request.authorization = new HashMap<String, String>();
        request.authorization.put("Basic", "YWRtaW46aHVudGVyMg==\n");
        assertEquals(200, logger.call(request).status);
    }
}

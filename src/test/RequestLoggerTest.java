import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class RequestLoggerTest {

    private Callable app = mock(RequestHandler.class);
    private Request request = mock(Request.class);
    private Response response = mock(Response.class);
    private Logger mockLogger = mock(Logger.class);
    private RequestLogger logger = new RequestLogger(app);

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
}

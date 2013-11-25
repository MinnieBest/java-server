import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import kevin.server.*;
import kevin.directory_app.*;

@RunWith(JUnit4.class)
public class OptionsHandlerTest {

    public Request request = mock(Request.class);
    public Callable app = mock(Callable.class);
    public OptionsHandler handler = new OptionsHandler(app);

    @Before
    public void setup() {
        request.route = "/method_options";
    }

    @Test
    public void returnsOptions() {
        request.method = "GET";
        assertEquals(200, handler.call(request).status);
    }

    @Test
    public void returnsOptionsOptions() {
        request.method = "OPTIONS";
        assertEquals(200, handler.call(request).status);
    }

    @Test
    public void returnsOptionsPost() {
        request.method = "POST";
        assertEquals(200, handler.call(request).status);
    }

    @Test
    public void returnsOptionsPut() {
        request.method = "PUT";
        assertEquals(200, handler.call(request).status);
    }

    @Test
    public void returnsOptionsHead() {
        request.method = "HEAD";
        assertEquals(200, handler.call(request).status);
    }
}

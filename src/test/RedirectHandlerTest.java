import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import kevin.server.*;
import kevin.directory_app.*;

@RunWith(JUnit4.class)
public class RedirectHandlerTest {

    public Request request = mock(Request.class);
    public Callable app = mock(Callable.class);
    public RedirectHandler handler = new RedirectHandler(app);

    @Test
    public void returnsRedirect() {
        request.route = "/redirect";
        request.method = "GET";
        assertEquals(301, handler.call(request).status);
    }
}

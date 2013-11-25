import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import kevin.server.*;
import kevin.directory_app.*;

@RunWith(JUnit4.class)
public class FormHandlerTest {

    public Request request = mock(Request.class);
    public Callable app = mock(Callable.class);
    public FormHandler handler = new FormHandler(app);

    @Before
    public void setup() {
        request.route = "/form";
        request.params = new HashMap<String, String>();
    }

    @Test
    public void returnsNotAllowed() {
        request.method = "PATCH";
        assertEquals(405, handler.call(request).status);
    }

    @Test
    public void createsForm() {
        assertNotNull(handler.form);
    }

    @Test
    public void getsForm() {
        request.method = "GET";
        assertEquals(200, handler.call(request).status);
    }

    @Test
    public void postsForm() {
        request.method = "POST";
        request.params.put("data", "testing");
        assertEquals(200, handler.call(request).status);
        assertEquals("testing", handler.form.get("data"));
    }

    @Test
    public void deletesForm() {
        request.method = "POST";
        request.params.put("data", "testing");
        handler.call(request);
        assertEquals("testing", handler.form.get("data"));
        request.method = "DELETE";
        assertEquals(200, handler.call(request).status);
        assertEquals(null, handler.form.get("data"));
    }
}

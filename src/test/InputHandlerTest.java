import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class InputHandlerTest {

    public Request request = mock(Request.class);
    public Callable app = mock(Callable.class);
    public InputHandler handler = new InputHandler(app);

    @Before
    public void setup() {
        request.route = "/input";
        request.params = new HashMap<String, String>();
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
    public void displaysData() {
        handler.form.put("input1", "Testing");
        assertThat(handler.paramDisplay("input1"), containsString("Testing"));
    }

    @Test
    public void postsForm() {
        request.method = "POST";
        request.params.put("input1", "testing");
        assertEquals(302, handler.call(request).status);
        assertEquals("testing", handler.form.get("input1"));
    }

    @Test
    public void escapesString() {
        request.method = "POST";
        request.params.put("input1", "<script>alert('here');</script>");
        handler.call(request);
        assertEquals("&lt;script&gt;alert('here');&lt;/script&gt;", handler.form.get("input1"));
    }

    @Test
    public void deletesForm() {
        request.method = "POST";
        request.params.put("input1", "testing");
        handler.call(request);
        assertEquals("testing", handler.form.get("input1"));
        request.method = "DELETE";
        assertEquals(302, handler.call(request).status);
        assertEquals(null, handler.form.get("input1"));
    }
}

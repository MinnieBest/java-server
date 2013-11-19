import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class InputControllerTest {

    public Request request = mock(Request.class);
    public InputController controller = new InputController();

    @Before
    public void setup() {
        request.route = "/input";
        request.params = new HashMap<String, String>();
    }

    @Test
    public void createsForm() {
        assertNotNull(controller.form);
    }

    @Test
    public void getsForm() {
        request.method = "GET";
        assertEquals(200, controller.send(request).status);
    }

    @Test
    public void displaysData() {
        controller.form.put("input1", "Testing");
        assertThat(controller.paramDisplay("input1"), containsString("Testing"));
    }

    @Test
    public void postsForm() {
        request.method = "POST";
        request.params.put("input1", "testing");
        assertEquals(302, controller.send(request).status);
        assertEquals("testing", controller.form.get("input1"));
    }

    @Test
    public void escapesString() {
        request.method = "POST";
        request.params.put("input1", "<script>alert('here');</script>");
        controller.send(request);
        assertEquals("&lt;script&gt;alert('here');&lt;/script&gt;", controller.form.get("input1"));
    }

    @Test
    public void deletesForm() {
        request.method = "POST";
        request.params.put("input1", "testing");
        controller.send(request);
        assertEquals("testing", controller.form.get("input1"));
        request.method = "DELETE";
        assertEquals(302, controller.send(request).status);
        assertEquals(null, controller.form.get("input1"));
    }
}

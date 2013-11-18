import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class FormControllerTest {

    public Request request = mock(Request.class);
    public FormController controller = new FormController();

    @Before
    public void setup() {
        request.route = "/form";
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
    public void postsForm() {
        request.method = "POST";
        request.params.put("data", "testing");
        assertEquals(200, controller.send(request).status);
        assertEquals("testing", controller.form.get("data"));
    }

    @Test
    public void deletesForm() {
        request.method = "POST";
        request.params.put("data", "testing");
        controller.send(request);
        assertEquals("testing", controller.form.get("data"));
        request.method = "DELETE";
        assertEquals(200, controller.send(request).status);
        assertEquals(null, controller.form.get("data"));
    }
}

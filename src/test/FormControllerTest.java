import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class FormControllerTest {

    public Request getRequest = new Request("GET /form HTTP/1.1");
    public Request postRequest = new Request("POST /form HTTP/1.1\r\n\r\ndata=test");
    public FormController controller = new FormController();

    @Test
    public void createsForm() {
        assertNotNull(controller.form);
    }

    @Test
    public void getsForm() {
        assertEquals(200, controller.send(getRequest).status);
    }

    @Test
    public void postsForm() {
        assertEquals(200, controller.send(postRequest).status);
    }
}

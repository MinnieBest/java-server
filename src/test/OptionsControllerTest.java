import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class OptionsControllerTest {

    public Request request = mock(Request.class);

    public OptionsController controller = new OptionsController();

    @Before
    public void setup() {
        request.route = "/method_options";
    }

    @Test
    public void returnsOptions() {
        request.method = "GET";
        assertEquals(200, controller.send(request).status);
    }

    @Test
    public void returnsOptionsPost() {
        request.method = "POST";
        assertEquals(200, controller.send(request).status);
    }

    @Test
    public void returnsOptionsPut() {
        request.method = "PUT";
        assertEquals(200, controller.send(request).status);
    }
}

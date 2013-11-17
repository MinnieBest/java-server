import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class OptionsControllerTest {

    public Request getRequest = new Request("GET /method_options HTTP/1.1");
    public Request postRequest = new Request("POST /method_options HTTP/1.1");
    public Request putRequest = new Request("PUT /method_options HTTP/1.1");
    public OptionsController controller = new OptionsController();

    @Test
    public void returnsOptions() {
        assertEquals(200, controller.send(getRequest).status);
    }

    @Test
    public void returnsOptionsPost() {
        assertEquals(200, controller.send(postRequest).status);
    }

    @Test
    public void returnsOptionsPutt() {
        assertEquals(200, controller.send(putRequest).status);
    }
}

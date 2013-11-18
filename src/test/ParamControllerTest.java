import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ParamControllerTest {

    public Request request = mock(Request.class);
    public ParamController controller = new ParamController();

    @Test
    public void returnsParams() {
        request.route = "/parameters";
        request.method = "GET";
        request.params = new HashMap<String, String>();
        request.params.put("data", "test");
        assertEquals(200, controller.send(request).status);
    }
}

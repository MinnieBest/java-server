import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ParamControllerTest {

    public Request request = new Request("GET /parameters?data=test HTTP/1.1");
    public ParamController controller = new ParamController();

    @Test
    public void returnsParams() {
        assertEquals(200, controller.send(request).status);
    }
}

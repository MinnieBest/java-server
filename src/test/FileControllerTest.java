import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class FileControllerTest {

    public Request request = mock(Request.class);
    public FileController controller = new FileController();

    @Test
    public void returnsFullFile() {
        request.method = "GET";
        request.route = "/test.txt";
        request.range = new HashMap<String, Integer>();
        request.baseDirectory = "resources";
        assertEquals(200, controller.send(request).status);
    }

    @Test
    public void returnsPartiaFile() {
        request.method = "GET";
        request.route = "/test.txt";
        HashMap<String, Integer> rangeMap = new HashMap<String, Integer>();
        request.baseDirectory = "resources";
        rangeMap.put("Length", 10);
        rangeMap.put("Start", 0);
        rangeMap.put("Stop", 9);
        request.range = rangeMap;
        assertEquals(206, controller.send(request).status);
    }
}

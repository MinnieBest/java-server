import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class FileHandlerTest {

    public Request request = mock(Request.class);
    public Callable app = mock(Callable.class);
    public FileHandler handler = new FileHandler(app);

    @Test
    public void returnsFullFile() {
        request.method = "GET";
        request.route = "/test.txt";
        request.range = new HashMap<String, Integer>();
        request.baseDirectory = "resources";
        assertEquals(200, handler.call(request).status);
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
        assertEquals(206, handler.call(request).status);
    }
}

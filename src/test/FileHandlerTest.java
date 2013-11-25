import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.HashMap;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import kevin.server.*;
import kevin.directory_app.*;

@RunWith(JUnit4.class)
public class FileHandlerTest {

    public Request request = mock(Request.class);
    public Callable app = mock(Callable.class);
    public FileHandler handler = new FileHandler(app);

    @Before
    public void setup() {
        request.route = "/test.txt";
        request.baseDirectory = "resources";
        request.method = "GET";
    }

    @Test
    public void returnsFullFile() {
        request.range = new HashMap<String, Integer>();
        request.baseDirectory = "resources";
        assertEquals(200, handler.call(request).status);
    }

    @Test
    public void returnsPartiaFile() {
        HashMap<String, Integer> rangeMap = new HashMap<String, Integer>();
        rangeMap.put("Length", 10);
        rangeMap.put("Start", 0);
        rangeMap.put("Stop", 9);
        request.range = rangeMap;
        assertEquals(206, handler.call(request).status);
    }

    @Test
    public void returnsNotAllowed() {
        request.method = "POST";
        assertEquals(405, handler.call(request).status);
    }

    @Test
    public void callsNextApp() {
        when(app.call(request)).thenReturn(new Response(200));
        request.route = "/parameters";
        handler.call(request);
        verify(app).call(request);
    }
}

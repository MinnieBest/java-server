import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class DirectoryControllerTest {

    public Request request = mock(Request.class);

    public DirectoryController controller = new DirectoryController();

    @Before
    public void setup() {
        request.method = "GET";
        request.route = "/test";
        request.baseDirectory = "resources";
    }

    @Test
    public void returnsSucces() {
        assertEquals(200, controller.send(request).status);
    }

    @Test
    public void returnsContent() {
        controller.send(request);
        assertThat(controller.buildDirectoryContents(), containsString(">test.txt</a></li>"));
    }
}

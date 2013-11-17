import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

@RunWith(JUnit4.class)
public class DirectoryControllerTest {

    public Request goodRequest = new Request("GET /test HTTP/1.1");

    public DirectoryController controller = new DirectoryController("resources");

    @Test
    public void returnsSucces() {
        assertEquals(200, controller.send(goodRequest).status);
    }

    @Test
    public void returnsContent() {
        controller.send(goodRequest);
        assertThat(controller.buildDirectoryContents(), containsString(">test.txt</a></li>"));
    }
}

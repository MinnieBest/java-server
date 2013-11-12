import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ServerTest {

    @Test
    public void thisAlwaysPasses() {
        Server tester = new Server();
        assertEquals("5000", tester.main(new String[] {"5000"}));
    }
}

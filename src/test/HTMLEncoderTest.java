import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class HTMLEncoderTest {

    @Test
    public void encodesAmpersand() {
        assertEquals("&amp;Test", HTMLEncoder.encode("&Test"));
    }

    @Test
    public void encodesSlash() {
        assertEquals("&quot;Test", HTMLEncoder.encode("\\Test"));
    }

    @Test
    public void encodesLT() {
        assertEquals("Test&lt;", HTMLEncoder.encode("Test<"));
    }

    @Test
    public void encodesGT() {
        assertEquals("Test&gt;", HTMLEncoder.encode("Test>"));
    }

    @Test
    public void encodesHTML() {
        assertEquals("&lt;h1&gt;Test&lt;/h1&gt;", HTMLEncoder.encode("<h1>Test</h1>"));
    }

    @Test
    public void encodesJS() {
        assertEquals("&lt;script&gt;alert('here');&lt;/script&gt;", HTMLEncoder.encode("<script>alert('here');</script>"));
    }

}

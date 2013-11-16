import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(JUnit4.class)
public class ResponseTest {

    private Response response = new Response(200);

    @Before
    public void init() {
        response.addHeader("Accept", "application/json");
    }

    @Test
    public void initsWithStatus() {
        assertEquals(response.status, 200);
    }

    @Test
    public void acceptsHeaders() {
        assertEquals("application/json", response.headers.get("Accept"));
    }

    @Test
    public void buildsTheStatus() {
        assertEquals("200 OK", response.buildStatus());
    }

    @Test
    public void buildsTheHeaders() {
        assertEquals("Accept: application/json\n", response.buildHeaders());
    }

    @Test
    public void buildsFullResponse() {
        assertEquals("HTTP/1.1 200 OK\nAccept: application/json\n\n", response.responseString());
    }

    @Test
    public void returnsByteOutput() {
        assertEquals(byte[].class, response.output().getClass());
    }
}

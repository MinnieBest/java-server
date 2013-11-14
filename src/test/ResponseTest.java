import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(JUnit4.class)
public class ResponseTest {

    private Response response = new Response(200, new ArrayList<String>(Arrays.asList("Accept: application/json")));

    @Test
    public void initsWithStatusAndHeaders() {
        assertEquals(response.status, 200);
        assertEquals("Accept: application/json", response.headers.get(0));
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

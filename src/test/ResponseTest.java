import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

@RunWith(JUnit4.class)
public class ResponseTest {

    private Response response = new Response(200);

    @Before
    public void init() {
        response.addBody(new TextBody(""));
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
        assertThat(response.buildHeaders(), containsString("Accept: application/json\n"));
    }

    @Test
    public void buildsFullResponse() {
        assertThat(response.responseString(), containsString("HTTP/1.1 200 OK\nContent-Length: 0\nContent-Type: text/html\nConnection: close\nAccept: application/json\nServer: myserver"));
    }

    @Test
    public void returnsByteOutput() {
        assertEquals(byte[].class, response.output().getClass());
    }
}

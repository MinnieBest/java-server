import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(JUnit4.class)
public class TextResponseTest {

    private TextResponse response = new TextResponse(404,
        new ArrayList<String>(Arrays.asList("Connection: keep-alive", "Content-Length: 10")), "<html><h1>Not Found</h1></html>");

    @Test
    public void initsWithStatusHeadersBody() {
        assertEquals(404, response.status);
        assertEquals("Content-Length: 10", response.headers.get(1));
        assertEquals("<html><h1>Not Found</h1></html>", response.body);
    }

    @Test
    public void buildsFullResponse() {
        assertEquals("HTTP/1.1 404 Not Found\nConnection: keep-alive\nContent-Length: 10\n\n<html><h1>Not Found</h1></html>", response.responseString());
    }
}

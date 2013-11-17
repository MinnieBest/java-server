import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TextResponseTest {

    private TextResponse response = new TextResponse(404, "<html><h1>Not Found</h1></html>");

    @Test
    public void initsWithStatus() {
        assertEquals(404, response.status);
    }

    @Test
    public void initsWithBody() {
        assertEquals("<html><h1>Not Found</h1></html>", response.body);
    }

    @Test
    public void buildsFullResponse() {
        response.addHeader("Connection", "keep-alive");
        response.addHeader("Content-Length", "10");
        assertEquals("HTTP/1.1 404 Not Found\nContent-Length: 10\nConnection: keep-alive\n\n<html><h1>Not Found</h1></html>", response.responseString());
    }
}

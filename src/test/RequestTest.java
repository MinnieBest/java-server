import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.net.*;
import java.io.*;

@RunWith(JUnit4.class)
public class RequestTest {

    private String testString = "GET /testing?name=Kevin&color=blue HTTP/1.1\n" +
                                "Host: localhost:5000\n" +
                                "Connection: keep-alive\n" +
                                "Cache-Control: max-age=0\n" +
                                "Accept: text/html,application/xml;q=0.9,image/webp\n"+
                                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5)\n" +
                                "Accept-Encoding: gzip,deflate,sdch Accept-Language: en-US,en;q=0.8\n" +
                                "Cookie: textwrapon=false; wysiwyg=textarea\n" +
                                "MyAuthorization: 12345password\n\n" +
                                "MyParam=Testing";

    private String optionsString = "OPTIONS / HTTP/1.1\n\n";

    private Request request = new Request(testString);

    @Test
    public void parsesMethod() {
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void parsesOptionsMethod() {
        Request request = new Request(optionsString);
        assertEquals("OPTIONS", request.getMethod());
    }

    @Test
    public void parsesRoute() {
        assertEquals("/testing", request.getRoute());
    }

    @Test
    public void parsesQueryString() {
        assertEquals("Kevin", request.params.get("name"));
        assertEquals("blue", request.params.get("color"));
    }

    @Test
    public void parsesParams() {
        assertEquals("Testing", request.params.get("MyParam"));
    }

    @Test
    public void parsesHttpVersion() {
        assertEquals("HTTP/1.1", request.getHttpv());
    }

    @Test
    public void parsesHost() {
        assertEquals("localhost:5000", request.getHost());
    }

    @Test
    public void parsesConnection() {
        assertEquals("keep-alive", request.getConnection());
    }

    @Test
    public void parsesCacheControl() {
        assertEquals("max-age=0", request.getCacheControl());
    }

    @Test
    public void parsesAccept() {
        String[] accept = new String[] {"text/html", "application/xml;q=0.9", "image/webp"};
        assertEquals(accept, request.getAccept());
    }

    @Test
    public void parsesUserAgent() {
        assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5)", request.getUserAgent());
    }

    @Test
    public void parsesCookie() {
        assertEquals(new String[] {"textwrapon=false", "wysiwyg=textarea"}, request.getCookie());
    }

    @Test
    public void parsesHeaders() {
        assertEquals("12345password", request.headers.get("MyAuthorization"));
    }
}

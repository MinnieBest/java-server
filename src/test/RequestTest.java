import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.net.*;
import java.io.*;

@RunWith(JUnit4.class)
public class RequestTest {

    private String testString = "GET /testing?name=Kevin&color=blue HTTP/1.1\r\n" +
                                "Host: localhost:5000\r\n" +
                                "Connection: keep-alive\r\n" +
                                "Cache-Control: max-age=0\r\n" +
                                "Content-Length: 26\r\n" +
                                "Accept: text/html,application/xml;q=0.9,image/webp\r\n"+
                                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5)\r\n" +
                                "Accept-Encoding: gzip,deflate,sdch Accept-Language: en-US,en;q=0.8\r\n" +
                                "Cookie: textwrapon=false; wysiwyg=textarea\r\n" +
                                "Authorization: Basic password\r\n" +
                                "MyAuthorization: 12345password\r\n\r\n";

    private String optionsString = "OPTIONS / HTTP/1.1\n\n";

    private String testBody = "data=test&other_data=kevin";

    private InputStream inputStream = new ByteArrayInputStream((testString + testBody).getBytes());
    private InputStream optionStream = new ByteArrayInputStream(optionsString.getBytes());

    private Request request = new Request(inputStream);
    private Request optionsRequest = new Request(optionStream);

    @Test
    public void parsesMethod() {
        assertEquals("GET", request.method);
    }

    @Test
    public void parsesOptionsMethod() {
        assertEquals("OPTIONS", optionsRequest.method);
    }

    @Test
    public void parsesRoute() {
        assertEquals("/testing", request.route);
    }

    @Test
    public void parsesContentLength() {
        assertEquals("26", request.headers.get("Content-Length"));
    }

    @Test public void parsesAuth() {
        assertEquals("password", request.authorization.get("Basic"));
    }

    @Test
    public void parsesQueryString() {
        assertEquals("Kevin", request.params.get("name"));
        assertEquals("blue", request.params.get("color"));
    }

    @Test
    public void parsesParams() {
        assertEquals("test", request.params.get("data"));
        assertEquals("kevin", request.params.get("other_data"));
    }

    @Test
    public void parsesHeaders() {
        assertEquals("12345password", request.headers.get("MyAuthorization"));
    }
}

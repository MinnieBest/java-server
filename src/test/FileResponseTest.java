import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

@RunWith(JUnit4.class)
public class FileResponseTest {

    FileResponse response = new FileResponse(200, new ArrayList<String>(Arrays.asList("Connection: keep-alive")),
        "test.txt");
    FileResponse zipResponse = new FileResponse(200, new ArrayList<String>(), "test.text.zip");
    FileResponse docResponse = new FileResponse(200, new ArrayList<String>(), "Testing.doc");

    @Test
    public void initsWithStatusHeadersPath() {
        assertEquals(200, response.status);
        assertEquals("Connection: keep-alive", response.headers.get(0));
        assertEquals("test.txt", response.filepath);
    }

    @Test
    public void instantiatesFile() {
        assertEquals(File.class, response.file.getClass());
    }

    @Test
    public void addsContentLengthHeader() {
        assertEquals("Content-Length: 27", response.headers.get(1));
    }

    @Test
    public void getsCorrectMimeType() {
        assertEquals("text/plain", response.getMimeType());
        assertEquals("application/zip", zipResponse.getMimeType());
        assertEquals("application/msword", docResponse.getMimeType());
    }

    @Test
    public void addsContentTypeHeader() {
        assertEquals("Content-Type: text/plain", response.headers.get(2));
    }

    @Test
    public void addsAttachmentHeader() {
        assertEquals("Content-Disposition: attachment", response.headers.get(3));
    }

    @Test
    public void readsFile() throws IOException {
        assertEquals(byte[].class, response.readFile().getClass());
        assertEquals(new File("test.txt").length(), response.readFile().length);
    }

    @Test
    public void returnsCombinedByteArray() throws IOException {
        int outputLength = response.responseString().getBytes().length + response.readFile().length;
        assertEquals(outputLength, response.output().length);
    }
}

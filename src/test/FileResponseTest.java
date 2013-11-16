import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

@RunWith(JUnit4.class)
public class FileResponseTest {

    FileResponse response = new FileResponse(200, "test.txt");
    FileResponse zipResponse = new FileResponse(200, "test.text.zip");
    FileResponse docResponse = new FileResponse(200, "Testing.doc");

    @Test
    public void initsWithStatus() {
        assertEquals(200, response.status);
    }

    @Test
    public void initsWithPath() {
        assertEquals("test.txt", response.filepath);
    }

    @Test
    public void instantiatesFile() {
        assertEquals(File.class, response.file.getClass());
    }

    @Test
    public void addsContentLengthHeader() {
        assertEquals("27", response.headers.get("Content-Length"));
    }

    @Test
    public void getsCorrectMimeType() {
        assertEquals("text/plain", response.getMimeType());
        assertEquals("application/zip", zipResponse.getMimeType());
        assertEquals("application/msword", docResponse.getMimeType());
    }

    @Test
    public void addsContentTypeHeader() {
        assertEquals("text/plain", response.headers.get("Content-Type"));
    }

    @Test
    public void addsAttachmentHeader() {
        assertEquals("attachment", response.headers.get("Content-Disposition"));
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

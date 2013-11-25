import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import java.io.*;
import kevin.server.*;

@RunWith(JUnit4.class)
public class FileBodyTest {

    FileBody body = new FileBody("resources/test.txt");
    FileBody zipBody = new FileBody("resources/test.text.zip");
    FileBody docBody = new FileBody("resources/Testing.doc");

    @Test
    public void initsWithPath() {
        assertEquals("resources/test.txt", body.filePath);
    }

    @Test
    public void instantiatesFile() {
        assertEquals(File.class, body.file.getClass());
    }

    @Test
    public void getsContentLength() {
        assertEquals(27, body.contentLength());
    }

    @Test
    public void getsCorrectMimeType() {
        assertEquals("text/plain", body.contentType());
        assertEquals("application/zip", zipBody.contentType());
        assertEquals("application/msword", docBody.contentType());
    }

    @Test
    public void readsFile() throws IOException {
        assertEquals(byte[].class, body.readFile().getClass());
        assertEquals(new File("resources/test.txt").length(), body.readFile().length);
    }

    @Test
    public void readsPartialFile() throws IOException {
        body.setRange(0, 5);
        assertEquals(5, body.readFile().length);
    }

    @Test
    public void returnsByteArray() throws IOException {
        assertEquals(new File("resources/test.txt").length(), body.output().length);
    }
}

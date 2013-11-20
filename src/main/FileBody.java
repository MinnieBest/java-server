import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import javax.activation.MimetypesFileTypeMap;

public class FileBody implements HTTPBody {

    public String filePath;
    public File file;
    public int start;
    public int stop;

    public FileBody(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
        this.start = 0;
        this.stop = (int) file.length();
    }

    public byte[] output() {
        byte[] output = null;
        try {
            output = readFile();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String contentType() {
        String mimeType = "text/plain";
        try {
            MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap("META-INF:mime.types");
            mimeType = mimeMap.getContentType(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    public long contentLength() {
        return stop;
    }

    public void setRange(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    public byte[] readFile() throws IOException {
        Path path = Paths.get(filePath);
        return Arrays.copyOfRange(Files.readAllBytes(path), start, stop);
    }
}

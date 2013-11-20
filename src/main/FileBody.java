import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import javax.activation.MimetypesFileTypeMap;

public class FileBody implements HTTPBody {

    public String filePath;
    public File file;

    public FileBody(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
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
        return file.length();
    }

    public byte[] readFile() throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}

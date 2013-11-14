import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import javax.activation.MimetypesFileTypeMap;

public class FileResponse extends Response {

    public String filepath;
    public File file;

    public FileResponse(int status, ArrayList<String> headers, String filepath) {
        super(status, headers);
        this.filepath = filepath;
        this.file = new File(filepath);
        addheaders();
    }

    public byte[] output() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(responseString().getBytes());
            outputStream.write(readFile());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public void addheaders() {
        headers.add("Content-Length: " + file.length());
        headers.add("Content-Type: " + getMimeType());
        headers.add("Content-Disposition: attachment");
    }

    public String getMimeType() {
        String mimeType = "text/plain";
        try {
            MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap("META-INF:mime.types");
            mimeType = mimeMap.getContentType(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    public byte[] readFile() throws IOException {
        Path path = Paths.get(filepath);
        return Files.readAllBytes(path);
    }
}

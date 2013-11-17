import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;
import javax.activation.MimetypesFileTypeMap;

public class FileResponse extends Response {

    public String filepath;
    public File file;
    public int start;
    public int stop;

    public FileResponse(int status, String filepath) {
        super(status);
        this.filepath = filepath;
        this.file = new File(filepath);
        addFileheaders();
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

    public void addFileheaders() {
        addHeader("Content-Length", String.valueOf(file.length()));
        addHeader("Content-Type", getMimeType());
        addHeader("Content-Disposition", "attachment");
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
        if (status == 206) {
            addHeader("Content-Range", "bytes " + start + "-" + stop + "/" + file.length());
            return Arrays.copyOfRange(Files.readAllBytes(path), start, stop);
        }
        else {
            return Files.readAllBytes(path);
        }
    }

    public void setRange(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }
}

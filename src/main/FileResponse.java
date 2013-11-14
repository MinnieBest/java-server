import java.io.File;

public class FileResponse extends Response {

    public File file;

    public FileResponse(int status, String[] headers, File file) {
        super(status, headers);
        this.file = file;
    }
}

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FileResponse extends Response {

    public File file;

    public FileResponse(int status, ArrayList<String> headers, File file) {
        super(status, headers);
        this.file = file;
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

public class DirectoryController extends Controller {

    public String baseDirectory;

    public DirectoryController(String directory) {
        this.baseDirectory = directory;
    }

    public Response get() {
        return new TextResponse(200, buildDirectoryContents());
    }

    public String buildDirectoryContents() {
        File directory = new File(baseDirectory + request.route);
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(directory.list()));
        StringBuilder builder = new StringBuilder();
        builder.append("<html><h1>" + baseDirectory + request.route + "</h1><ul>");
            for(String name : names) {
                if (request.route.equals("/")) {
                    builder.append("<li><a href='" + name + "'>" + name + "</a></li>");
                }
                else {
                    builder.append("<li><a href='" + request.route + "/" + name + "'>" + name + "</a></li>");
                }
            }
        builder.append("</ul></html>");
        return builder.toString();
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

public class DirectoryController extends Controller {

    public Response get() {
        Response response = new Response(200);
        response.addBody(new TextBody(buildDirectoryContents()));
        return response;
    }

    public String buildDirectoryContents() {
        File directory = new File(request.baseDirectory + request.route);
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(directory.list()));
        StringBuilder builder = new StringBuilder();
        builder.append("<html><h1>" + request.baseDirectory + request.route + "</h1><ul>");
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

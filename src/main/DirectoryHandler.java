package kevin.directory_app;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import kevin.server.*;

public class DirectoryHandler implements Callable {

    public Request request;
    public Callable app;

    public DirectoryHandler(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        this.request = request;
        if (isDirectory(request.baseDirectory + request.route)) {
            return getResponse();
        } else {
            return app.call(request);
        }
    }

    public Response getResponse() {
        if (request.method.equals("GET")) {
            return get();
        } else {
            return methodNotAllowed();
        }
    }

    public Response methodNotAllowed() {
        return new Response(405);
    }

    public Boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

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

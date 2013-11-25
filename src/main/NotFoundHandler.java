package kevin.directory_app;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import kevin.server.*;

public class NotFoundHandler implements Callable {

    public Response call(Request request) {
        return notFound();
    }

    public Response notFound() {
        Response response = new Response(404);
        response.addBody(new TextBody("<html><h1>404, Not Found</h1></html>"));
        return response;
    }
}

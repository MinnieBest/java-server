import java.io.File;

public class FileHandler implements Callable {

    public Request request;
    public Callable app;

    public FileHandler(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        this.request = request;
        if (isFile(request.baseDirectory + request.route)) {
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

    public Boolean isFile(String path) {
        return new File(path).exists();
    }

    public Response get() {
        Response response = new Response(200);
        if (request.range.isEmpty()) {
            response.addBody(new FileBody(request.baseDirectory + request.route));
        }
        else {
            response = partialResponse();
        }
        return response;
    }

    public Response partialResponse() {
        Response response = new Response(206);
        int start = request.range.get("Start");
        int stop = request.range.get("Stop");
        FileBody body = new FileBody(request.baseDirectory + request.route);
        body.setRange(start, stop);
        response.addBody(body);
        response.addHeader("Content-Range", "bytes " + start + "-" + stop + "/" + body.file.length());
        return response;
    }
}

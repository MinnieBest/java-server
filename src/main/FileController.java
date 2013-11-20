public class FileController extends Controller {

    public String baseDirectory;

    public FileController(String directory) {
        this.baseDirectory = directory;
    }

    public Response get() {
        Response response = new Response(200);
        if (request.range.isEmpty()) {
            response.addBody(new FileBody(baseDirectory + request.route));
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
        FileBody body = new FileBody(baseDirectory + request.route);
        body.setRange(start, stop);
        response.addBody(body);
        response.addHeader("Content-Range", "bytes " + start + "-" + stop + "/" + body.file.length());
        return response;
    }
}

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
            response.addBody(new FileBody(baseDirectory + request.route));
            response.addHeader("Content-Length", request.range.get("Length").toString());
        }
        return response;
    }
}

public class FileController extends Controller {

    public String baseDirectory;

    public FileController(String directory) {
        this.baseDirectory = directory;
    }

    public Response get() {
        if (request.headers.get("Range") == null) {
            return new FileResponse(200, baseDirectory + request.route);
        }
        else {
            Response response = new FileResponse(206, baseDirectory + request.route);
            response.addHeader("Content-Length", "5");
            return response;
        }
    }
}

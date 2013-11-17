public class FileController extends Controller {

    public String baseDirectory;

    public FileController(String directory) {
        this.baseDirectory = directory;
    }

    public Response get() {
        if (request.range.isEmpty()) {
            return new FileResponse(200, baseDirectory + request.route);
        }
        else {
            FileResponse response = new FileResponse(206, baseDirectory + request.route);
            response.addHeader("Content-Length", request.range.get("Length").toString());
            response.setRange(request.range.get("Start"), request.range.get("Stop"));
            return response;
        }
    }
}

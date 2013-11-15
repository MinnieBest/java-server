import java.util.ArrayList;
import java.util.Arrays;

public class RequestHandler implements Callable {

    public String baseDirectory;

    public RequestHandler(String directory) {
        this.baseDirectory = directory;
    }

    public Response call(Request request) {
        return new FileResponse(200, getHeaders(request), "test.txt");
    }

    public ArrayList<String> getHeaders(Request request) {
        if ("OPTIONS".equals(request.method) || "/method_options".equals(request.route)) {
            return new ArrayList<String>(Arrays.asList("Allow: GET,HEAD,POST,OPTIONS,PUT"));
        }
        else {
            return new ArrayList<String>(Arrays.asList("Connection: keep-alive"));
        }
    }
}

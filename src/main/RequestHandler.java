import java.util.ArrayList;
import java.util.Arrays;

public class RequestHandler implements Callable {

    public String baseDirectory;
    private Request request;

    public RequestHandler(String directory) {
        this.baseDirectory = directory;
    }

    public Response call(Request request) {
        this.request = request;
        return new FileResponse(200, getHeaders(), "test.txt");
    }

    public int getCode() {
        return 200;
    }

    public ArrayList<String> getHeaders() {
        if ("OPTIONS".equals(request.method) || "/method_options".equals(request.route)) {
            return new ArrayList<String>(Arrays.asList("Allow: GET,HEAD,POST,OPTIONS,PUT"));
        }
        else {
            return new ArrayList<String>(Arrays.asList("Connection: keep-alive"));
        }
    }
}

import java.util.ArrayList;
import java.util.Arrays;

public class RequestHandler {

    public Request request;

    public RequestHandler(Request request) {
        this.request = request;
    }

    public Response call() {
        int code = getCode();
        ArrayList<String> headers = getHeaders();
        return new Response(code, headers);
    }

    public int getCode() {
        return 200;
    }

    public ArrayList<String> getHeaders() {
        if ("OPTIONS".equals(request.method) || "/method_options".equals(request.route)) {
            return new ArrayList<String>(Arrays.asList("Allow: GET,HEAD,POST,OPTIONS,PUT"));
        }
        else {
            return new ArrayList<String>(Arrays.asList(""));
        }
    }
}

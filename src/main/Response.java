import java.util.Map;
import java.util.HashMap;

public class Response {

    private static final Map<Integer, String> STATUS_CODES = new HashMap<Integer, String>();
        static {
            STATUS_CODES.put(200, "OK");
            STATUS_CODES.put(404, "Not Found");
            STATUS_CODES.put(401, "Unauthorized");
            STATUS_CODES.put(405, "Method Not Allowed");
        }

    public int status;
    public String headers;
    public String body;

    public Response(int status, String headers, String body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public byte[] output() {
        return responseString().getBytes();
    }

    public String responseString() {
        String httpv = "HTTP/1.1";
        String fullCode = status + " " + STATUS_CODES.get(status);
        return httpv + " " +
               fullCode + "\n" +
               headers + "\n\n" +
               body;
    }
}

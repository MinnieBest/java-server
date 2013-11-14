import java.util.Map;
import java.util.HashMap;

public class Response {

    private static final Map<Integer, String> STATUS_CODES = new HashMap<Integer, String>();
        static {
            STATUS_CODES.put(200, "OK");
            STATUS_CODES.put(206, "Partial Content");
            STATUS_CODES.put(302, "Found");
            STATUS_CODES.put(401, "Unauthorized");
            STATUS_CODES.put(404, "Not Found");
            STATUS_CODES.put(405, "Method Not Allowed");
        }

    public int status;
    public String[] headers;

    public Response(int status, String[] headers) {
        this.status = status;
        this.headers = headers;
    }

    public byte[] output() {
        return responseString().getBytes();
    }

    public String responseString() {
        String httpv = "HTTP/1.1";
        String statusString = buildStatus();
        String headerString = buildHeaders();
        return httpv + " " + statusString + "\n" +
               headerString + "\n";
    }

    public String buildStatus() {
        return status + " " + STATUS_CODES.get(status);
    }

    public String buildHeaders() {
        StringBuilder builder = new StringBuilder();
        for(String header : headers) {
            builder.append(header);
            builder.append("\n");
        }
        return builder.toString();
    }

}

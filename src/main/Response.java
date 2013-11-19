import java.util.HashMap;

public class Response {

    public static final HashMap<Integer, String> STATUS_CODES = new HashMap<Integer, String>();
        static {
            STATUS_CODES.put(200, "OK");
            STATUS_CODES.put(206, "Partial Content");
            STATUS_CODES.put(302, "Found");
            STATUS_CODES.put(401, "Unauthorized");
            STATUS_CODES.put(404, "Not Found");
            STATUS_CODES.put(405, "Method Not Allowed");
        }

    public int status;
    public HashMap<String, String> headers;

    public Response(int status) {
        this.status = status;
        this.headers = new HashMap<String, String>();
    }

    public void addHeader(String header, String content) {
        headers.put(header, content);
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
        for(String header : headers.keySet()) {
            builder.append(header + ": " + headers.get(header));
            builder.append("\n");
        }
        return builder.toString();
    }

}

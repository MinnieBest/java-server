import java.util.HashMap;
import java.io.*;

public class Response {

    public static String SERVER_NAME = "myserver";

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
    public HTTPBody body;

    public Response(int status) {
        this.status = status;
        this.headers = new HashMap<String, String>();
    }

    public void addBody(HTTPBody body) {
        this.body = body;
    }

    public void addHeader(String header, String content) {
        headers.put(header, content);
    }

    public byte[] output() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(responseString().getBytes());
            if (body != null) {
                outputStream.write(body.output());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
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
        addHeader("Server", SERVER_NAME);
        addHeader("Connection", "close");
        if (body != null) {
            addHeader("Content-Type", body.contentType());
            addHeader("Content-Length", String.valueOf(body.contentLength()));
        }
        StringBuilder builder = new StringBuilder();
        for(String header : headers.keySet()) {
            builder.append(header + ": " + headers.get(header));
            builder.append("\n");
        }
        return builder.toString();
    }

}

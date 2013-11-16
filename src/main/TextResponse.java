public class TextResponse extends Response {

    public String body;

    public TextResponse(int status, String body) {
        super(status);
        this.body = body;
    }

    public String responseString() {
        String httpv = "HTTP/1.1";
        String statusString = buildStatus();
        String headerString = buildHeaders();
        return httpv + " " + statusString + "\n" +
               headerString + "\n" +
               body;
    }
}

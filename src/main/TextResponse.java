public class TextResponse extends Response {

    public int status;
    public String[] headers;
    public String body;

    public TextResponse(int status, String[] headers, String body) {
        super(status, headers);
        this.body = body;
    }
}

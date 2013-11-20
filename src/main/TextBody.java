public class TextBody implements HTTPBody {

    public String text;

    public TextBody(String text) {
        this.text = text;
    }

    public byte[] output() {
        return text.getBytes();
    }

    public String contentType() {
        return "text/html";
    }

    public long contentLength() {
        return text.length();
    }
}

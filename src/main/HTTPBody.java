package kevin.server;

public interface HTTPBody {
    byte[] output();
    String contentType();
    long contentLength();
}

import java.util.logging.Logger;

public class RequestLogger implements Callable {

    public static Logger logger = Logger.getLogger(RequestLogger.class.getName());

    public Callable app;

    public RequestLogger(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        log(request);
        return app.call(request);
    }

    public void log(Request request) {
        String message = request.log;
        logger.info(message);
    }
}

import java.util.logging.Logger;
import java.util.LinkedList;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;

public class RequestLogger implements Callable {

    public static Logger logger = Logger.getLogger(RequestLogger.class.getName());

    public static final String AUTH = "admin:hunter2";

    public Callable app;
    public Request request;
    public String directory;
    public LinkedList<String> logs;

    public RequestLogger(Callable app, String directory) {
        this.app = app;
        this.directory = directory;
        this.logs = new LinkedList<String>();
    }

    public Response call(Request request) {
        this.request = request;
        request.baseDirectory = directory;
        log(request);
        if (request.route.equals("/logs")) {
            return getResponse();
        } else {
            return app.call(request);
        }
    }

    public Response getResponse() {
        if (request.method.equals("GET")) {
            return get();
        } else {
            return methodNotAllowed();
        }
    }

    public Response methodNotAllowed() {
        return new Response(405);
    }

    public void log(Request request) {
        String message = request.log;
        if (logs.size() == 5) {
            logs.removeFirst();
        }
        logs.addLast(message);
        logger.info(message);
    }

    public Response get() {
        Response response = null;
        if (authenticate()) {
            response = new Response(200);
            response.addBody(new TextBody(logs.toString()));
        }
        else {
            response = new Response(401);
            response.addBody(new TextBody("Authentication required"));
        }
        return response;
    }

    public Boolean authenticate() {
        String auth = request.authorization.get("Basic");
        String credentials = null;
        if (auth != null) {
            try {
               credentials = new String(DatatypeConverter.parseBase64Binary(auth), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return AUTH.equals(credentials);
    }
}

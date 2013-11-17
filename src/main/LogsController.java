import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;

public class LogsController extends Controller {

    public static final String AUTH = "admin:hunter2";

    public ArrayList<String> logs;

    public LogsController(ArrayList<String> logs) {
        this.logs = logs;
    }

    public Response get() {
        String auth = request.authorization.get("Basic");
        String credentials = null;
        if (auth != null) {
            try {
               credentials = new String(DatatypeConverter.parseBase64Binary(auth), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (AUTH.equals(credentials)) {
            return new TextResponse(200, logs.toString());
        }
        else {
            return new TextResponse(401, "Authentication required");
        }
    }
}

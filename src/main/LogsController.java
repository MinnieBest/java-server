import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;

public class LogsController extends Controller {

    public static final String AUTH = "admin:hunter2";

    public Response get() {
        Response response = null;
        if (authenticate()) {
            response = new Response(200);
            response.addBody(new TextBody(request.logs.toString()));
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

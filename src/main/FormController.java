import java.util.HashMap;

public class FormController extends Controller {

    public HashMap<String, String> form;

    public FormController() {
        this.form = new HashMap<String, String>();
    }

    public Response get() {
        String body = "";
        for(String key : form.keySet()) {
            String data = key + " = " + form.get(key);
            body = body + data;
        }
        return new TextResponse(200, body);
    }

    public Response post() {
        HashMap<String, String> params = request.params;
        String value = params.get("data");
        form.put("data", value);
        return new Response(200);
    }

    public Response put() {
        return post();
    }
}

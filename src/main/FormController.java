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
        Response response = new Response(200);
        response.addBody(new TextBody(body));
        return response;
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

    public Response delete() {
        form.remove("data");
        return new Response(200);
    }
}

import java.util.HashMap;

public class FormHandler implements Callable {

    public Request request;
    public Callable app;
    public HashMap<String, String> form;

    public FormHandler(Callable app) {
        this.app = app;
        this.form = new HashMap<String, String>();
    }

    public Response call(Request request) {
        this.request = request;
        if (request.route.equals("/form")) {
            return getResponse();
        } else {
            return app.call(request);
        }
    }

    public Response getResponse() {
        Response response = methodNotAllowed();
        switch (request.method) {
            case "GET": response = get();
                break;
            case "POST": response = post();
                break;
            case "PUT": response = put();
                break;
            case "DELETE": response = delete();
                break;
        }
        return response;
    }

    public Response methodNotAllowed() {
        return new Response(405);
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

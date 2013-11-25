package kevin.directory_app;

import java.util.HashMap;
import kevin.server.*;

public class InputHandler implements Callable {

    public static String FORM = "<html><h1>Use the form!!!</h1>" +
                                "<form action='/input' method='POST'>" +
                                "<input name='input1' type='text' placeholder='Input 1'><br>" +
                                "<input name='input2' type='text' placeholder='Input 2'><br>" +
                                "<input name='input3' type='text' placeholder='Input 3'><br>" +
                                "<input type='submit' value='Submit'>" +
                                "</form>";

    public HashMap<String, String> form;
    public HashMap<String, String> params;
    public Request request;
    public Callable app;

    public InputHandler(Callable app) {
        this.app = app;
        this.form = new HashMap<String, String>();
    }

    public Response call(Request request) {
        this.request = request;
        if (request.route.equals("/input")) {
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

    public String paramDisplay(String key) {
        return "<h2>" + key + ": " + form.get(key) + "</h2>";
    }

    public Response get() {
        StringBuilder body = new StringBuilder();
        body.append(FORM);
        body.append(paramDisplay("input1"));
        body.append(paramDisplay("input2"));
        body.append(paramDisplay("input3"));
        body.append("</html>");
        Response response = new Response(200);
        response.addBody(new TextBody(response.toString()));
        return response;
    }

    public void saveParam(String key) {
        String value = params.get(key);
        if (value != null) {
            form.put(key, HTMLEncoder.encode(value));
        }
    }

    public Response post() {
        this.params = request.params;
        saveParam("input1");
        saveParam("input2");
        saveParam("input3");
        Response response = new Response(302);
        response.addHeader("Location", "/input");
        return response;
    }

    public Response put() {
        return post();
    }

    public Response delete() {
        form.clear();
        Response response = new Response(302);
        response.addHeader("Location", "/input");
        return response;
    }
}

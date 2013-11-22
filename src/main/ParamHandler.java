public class ParamHandler implements Callable {

    public Request request;
    public Callable app;

    public ParamHandler(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        this.request = request;
        if (request.route.equals("/parameters")) {
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

    public Response get() {
        String body = "";
        for(String key : request.params.keySet()) {
            String value = request.params.get(key);
            String data = key + " = " + value + " ";
            body = body + data;
        }
        Response response = new Response(200);
        response.addBody(new TextBody(body));
        return response;
    }
}

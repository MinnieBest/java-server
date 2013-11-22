public class RedirectHandler implements Callable {

    public Request request;
    public Callable app;

    public RedirectHandler(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        this.request = request;
        if (request.route.equals("/redirect")) {
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
        Response response = new Response(301);
        response.addBody(new TextBody("<html><head><meta http-equiv='refresh' content='0 ; url=/'></head></html>"));
        String location = "http://" + request.host + "/";
        response.addHeader("Location", location);
        return response;
    }
}

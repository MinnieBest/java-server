public class OptionsHandler implements Callable {

    public Request request;
    public Callable app;

    public OptionsHandler(Callable app) {
        this.app = app;
    }

    public Response call(Request request) {
        this.request = request;
        if (request.route.equals("/method_options") || request.method == "OPTIONS") {
            return optionsResponse();
        } else {
            return app.call(request);
        }
    }

    public Response optionsResponse() {
        Response response = new Response(200);
        response.addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return response;
    }
}

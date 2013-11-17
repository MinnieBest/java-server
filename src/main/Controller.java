public class Controller {

    public Request request;

    public Response send(Request request) {
        this.request = request;
        Response response = methodNotAllowedResponse();
        switch (request.method) {
            case "GET": response = get();
                break;
            case "POST": response = post();
                break;
            case "PUT": response = put();
                break;
            case "OPTIONS": response = options();
                break;
            case "HEAD": response = head();
                break;
            case "DELETE": response = delete();
                break;
        }
        return response;
    }

    public Response get() {
        return methodNotAllowedResponse();
    }

    public Response post() {
        return methodNotAllowedResponse();
    }

    public Response put() {
        return methodNotAllowedResponse();
    }

    public Response head() {
        return methodNotAllowedResponse();
    }

    public Response delete() {
        return methodNotAllowedResponse();
    }

    public Response options() {
        Response response = new Response(200);
        response.addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return response;
    }

    public Response methodNotAllowedResponse() {
        return new Response(405);
    }
}

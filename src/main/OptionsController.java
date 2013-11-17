public class OptionsController extends Controller {

    public Response get() {
        Response response = new Response(200);
        response.addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return response;
    }

    public Response post() {
        return get();
    }

    public Response put() {
        return get();
    }

    public Response head() {
        return get();
    }
}

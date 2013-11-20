public class RedirectController extends Controller {

    public Response get() {
        Response response = new Response(301);
        response.addBody(new TextBody("<html><head><meta http-equiv='refresh' content='0 ; url=/'></head></html>"));
        String location = "http://" + request.host + "/";
        response.addHeader("Location", location);
        return response;
    }
}

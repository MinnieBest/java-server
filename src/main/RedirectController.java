public class RedirectController extends Controller {

    public Response get() {
        Response response = new TextResponse(301, "<html><head><meta http-equiv='refresh' content='0 ; url=/'></head></html>");
        String location = "http://" + request.host + "/";
        response.addHeader("Location", location);
        return response;
    }
}

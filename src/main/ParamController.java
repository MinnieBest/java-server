public class ParamController extends Controller {

    public Response get() {
        String body = "";
        for(String key : request.params.keySet()) {
            String value = request.params.get(key);
            String data = key + " = " + value + " ";
            body = body + data;
        }
        return new TextResponse(200, body);
    }
}

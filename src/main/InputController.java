import java.util.HashMap;

public class InputController extends Controller {

    public static String FORM = "<html><h1>Use the form!!!</h1>" +
                                "<form action='/input' method='POST'>" +
                                "<input name='input1' type='text' placeholder='Input 1'><br>" +
                                "<input name='input2' type='text' placeholder='Input 2'><br>" +
                                "<input name='input3' type='text' placeholder='Input 3'><br>" +
                                "<input type='submit' value='Submit'>" +
                                "</form>";

    public HashMap<String, String> form;

    public InputController() {
        this.form = new HashMap<String, String>();
    }

    public Response get() {
        StringBuilder body = new StringBuilder();
        body.append(FORM);
        body.append("<h2>" + "input1" + ": " + form.get("input1") + "</h2>");
        body.append("<h2>" + "input2" + ": " + form.get("input2") + "</h2>");
        body.append("<h2>" + "input3" + ": " + form.get("input3") + "</h2>");
        body.append("</html>");
        return new TextResponse(200, body.toString());
    }

    public Response post() {
        HashMap<String, String> params = request.params;
        form.put("input1", params.get("input1"));
        form.put("input2", params.get("input2"));
        form.put("input3", params.get("input3"));
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

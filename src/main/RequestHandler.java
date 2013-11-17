import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;

public class RequestHandler implements Callable {

    public static final String AUTH = "admin:hunter2";

    public String baseDirectory;
    public Request request;
    public HashMap<String, String> form;
    public ArrayList<String> logs;

    public RequestHandler(String directory) {
        this.baseDirectory = directory;
        this.form = new HashMap<String, String>();
        this.logs = new ArrayList<String>(5);
    }

    public Response call(Request request) {
        this.request = request;
        addLog();
        Response response = routeRequest();
        return response;
    }

    public Response routeRequest() {
        Response response = methodNotAllowedResponse();
        switch (request.method) {
            case "GET": response = getResponse();
                break;
            case "POST": response = postResponse();
                break;
            case "PUT": response = putResponse();
                break;
            case "OPTIONS": response = optionsResponse();
                break;
            case "HEAD": response = getResponse();
                break;
        }
        return response;
    }

    public Response getResponse() {
        Response response = notFoundResponse();
        switch (filterRoute()) {
            case "file": response = fileResponse();
                break;
            case "directory": response = directoryResponse();
                break;
            case "/method_options": response = optionsResponse();
                break;
            case "/redirect": response = redirectResponse();
                break;
            case "/form": response = readForm();
                break;
            case "/parameters": response = parameterResponse();
                break;
            case "/logs": response = logsResponse();
                break;
        }
        return response;
    }

    public Response postResponse() {
        Response response = notFoundResponse();
        switch (filterRoute()) {
            case "file": response = methodNotAllowedResponse();
                break;
            case "directory": response = methodNotAllowedResponse();
                break;
            case "/method_options": response = optionsResponse();
                break;
            case "/form": response = setForm();
                break;
        }
        return response;
    }

    public Response putResponse() {
        Response response = notFoundResponse();
        switch (filterRoute()) {
            case "file": response = methodNotAllowedResponse();
                break;
            case "directory": response = methodNotAllowedResponse();
                break;
            case "/method_options": response = optionsResponse();
                break;
            case "/form": response = setForm();
                break;
        }
        return response;
    }

    public String filterRoute() {
        String route = request.route;
        if (isDirectory(baseDirectory + route)) {
            return "directory";
        }
        else if (isFile(baseDirectory + route)) {
            return "file";
        }
        else {
            return route;
        }
    }

    public Boolean isFile(String path) {
        return new File(path).exists();
    }

    public Boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }

    public Response fileResponse() {
        if (request.headers.get("Range") == null) {
            return new FileResponse(200, baseDirectory + request.route);
        }
        else {
            Response response = new FileResponse(206, baseDirectory + request.route);
            response.addHeader("Content-Length", "5");
            return response;
        }
    }

    public Response directoryResponse() {
        return new TextResponse(200, directoryPresenter());
    }

    public Response optionsResponse() {
        Response response = new Response(200);
        response.addHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        return response;
    }

    public Response methodNotAllowedResponse() {
        return new Response(405);
    }

    public Response redirectResponse() {
        Response response = new TextResponse(301, "<html><head><meta http-equiv='refresh' content='0 ; url=/'></head></html>");
        String location = "http://" + request.host + "/";
        response.addHeader("Location", location);
        return response;
    }

    public Response notFoundResponse() {
        return new TextResponse(404, "<html><h1>404, Not Found</h1></html>");
    }

    public Response readForm() {
        String body = "";
        for(String key : form.keySet()) {
            String data = key + " = " + form.get(key);
            body = body + data;
        }
        return new TextResponse(200, body);
    }

    public Response setForm() {
        HashMap<String, String> params = request.params;
        String value = params.get("data");
        form.put("data", value);
        return new Response(200);
    }

    public Response parameterResponse() {
        String body = "";
        for(String key : request.params.keySet()) {
            String value = request.params.get(key);
            String data = key + " = " + value + " ";
            body = body + data;
        }
        return new TextResponse(200, body);
    }

    public String directoryPresenter() {
        File directory = new File(baseDirectory + request.route);
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(directory.list()));
        StringBuilder builder = new StringBuilder();
        builder.append("<html><h1>" + baseDirectory + request.route + "</h1><ul>");
            for(String name : names) {
                if (request.route.equals("/")) {
                    builder.append("<li><a href='" + name + "'>" + name + "</a></li>");
                }
                else {
                    builder.append("<li><a href='" + request.route + "/" + name + "'>" + name + "</a></li>");
                }
            }
        builder.append("</ul></html>");
        return builder.toString();
    }

    public void addLog() {
        String entry = request.inputString;
        if (logs.size() == 5) {
            logs.remove(0);
        }
        logs.add(entry);
    }

    public Response logsResponse() {
        String auth = request.authorization.get("Basic");
        String credentials = null;
        if (auth != null) {
            try {
               credentials = new String(DatatypeConverter.parseBase64Binary(auth), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (AUTH.equals(credentials)) {
            return new TextResponse(200, logs.toString());
        }
        else {
            return new TextResponse(401, "Authentication required");
        }
    }
}

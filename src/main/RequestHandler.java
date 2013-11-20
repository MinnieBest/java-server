import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;

public class RequestHandler implements Callable {

    public String baseDirectory;
    public HashMap<String, Controller> routes;
    public Request request;
    public LinkedList<String> logs;

    public RequestHandler(String directory) {
        this.baseDirectory = directory;
        this.logs = new LinkedList<String>();
        this.routes = new HashMap<String, Controller>();
        drawRoutes();
    }

    public void drawRoutes() {
        routes.put("/method_options", new OptionsController());
        routes.put("/redirect", new RedirectController());
        routes.put("/input", new InputController());
        routes.put("/form", new FormController());
        routes.put("/parameters", new ParamController());
        routes.put("/logs", new LogsController(logs));
        routes.put("file", new FileController(baseDirectory));
        routes.put("directory", new DirectoryController(baseDirectory));
    }

    public Response call(Request request) {
        this.request = request;
        addLog();
        Controller controller = getController();
        if (controller == null) {
            Response response = new Response(404);
            response.addBody(new TextBody("<html><h1>404, Not Found</h1></html>"));
            return response;
        }
        else {
            return controller.send(request);
        }
    }

    public void addLog() {
        String entry = request.log;
        if (logs.size() == 5) {
            logs.removeFirst();
        }
        logs.addLast(entry);
    }

    public Controller getController() {
        String route = filterRoute();
        return routes.get(route);
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
}

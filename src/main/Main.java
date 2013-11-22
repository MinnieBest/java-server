import java.io.IOException;

public class Main {

    public static void main(String [] args) throws IOException {
        Arguments arguments = new Arguments(args);
        NotFoundHandler notFoundHandler = new NotFoundHandler();
        FileHandler fileHandler = new FileHandler(notFoundHandler);
        DirectoryHandler directoryHandler = new DirectoryHandler(fileHandler);
        OptionsHandler optionsHandler = new OptionsHandler(directoryHandler);
        RedirectHandler redirectHandler = new RedirectHandler(optionsHandler);
        ParamHandler paramHandler = new ParamHandler(redirectHandler);
        InputHandler inputHandler = new InputHandler(paramHandler);
        FormHandler formHandler = new FormHandler(inputHandler);
        RequestLogger logger = new RequestLogger(formHandler, arguments.directory());
        Server server = new Server(arguments.port(), logger);
        server.start();
    }
}

package kevin.directory_app;

public class HTMLEncoder {

    public static String encode(String input) {
        String output = input;
        output = output.replace("&", "&amp;");
        output = output.replace("\\", "&quot;");
        output = output.replace("<", "&lt;");
        output = output.replace(">", "&gt;");
        return output;
    }
}

public class HTMLEncoder {

    public static String encode(String input) {
        if (input != null) {
            String output = input;
            output = output.replace("&", "&amp;");
            output = output.replace("\\", "&quot;");
            output = output.replace("<", "&lt;");
            output = output.replace(">", "&gt;");
            return output;
        }
        return "";
    }
}

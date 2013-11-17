import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;
import java.net.URLDecoder;

public class Request {

    public String inputString;
    public String method;
    public String route;
    public HashMap<String, String> authorization;
    public String httpv;
    public String host;
    public String connection;
    public String cacheControl;
    public String[] accept;
    public String userAgent;
    public String[] cookie;
    public HashMap<String, String> headers;
    public HashMap<String, String> params;

    public Request(String input) {
        this.inputString = input;
        this.method = getMethod();
        this.route = getRoute();
        this.authorization = getAuth();
        this.httpv = getHttpv();
        this.host = getHost();
        this.connection = getConnection();
        this.cacheControl = getCacheControl();
        this.accept = getAccept();
        this.userAgent = getUserAgent();
        this.cookie = getCookie();
        this.headers = getHeaders();
        this.params = getParams();
    }

    public String searchInput(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else {
            return "";
        }
    }

    public String getMethod() {
        return searchInput("(^GET|POST|PUT|OPTIONS)");
    }

    public String getRoute() {
        String route = searchInput("(\\/[^\\s\\?]*)");
        return URLDecoder.decode(route);
    }

    public HashMap<String, String> getAuth() {
        HashMap<String, String> protocol = new HashMap<String, String>();
        String auth = searchInput("((?<=\\nAuthorization: )([^\\r]+))");
        if (!auth.equals("")) {
            String[] pair = auth.split(" ");
            protocol.put(pair[0], pair[1]);
        }
        return protocol;
    }

    public String getHttpv() {
        return searchInput("(HTTP\\S+)");
    }

    public String getHost() {
        return searchInput("((?<=Host: )([^\\r]+))").trim();
    }

    public String getConnection() {
        return searchInput("((?<=Connection: )([^\\r]+))");
    }

    public String getCacheControl() {
        return searchInput("((?<=Cache-Control: )([^\\r]+))");
    }

    public String[] getAccept() {
        return searchInput("((?<=Accept: )([^\\r]+))").split(",");
    }

    public String getUserAgent() {
        return searchInput("((?<=User-Agent: )([^\\r]+))");
    }

    public String[] getCookie() {
        return searchInput("((?<=Cookie: )([^\\r]+))").split("; ");
    }

    public HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        String[] splitInput = inputString.split("\r\n");
        for (String header : splitInput) {
            String[] headerPair = header.split("(:)( ?)");
            headers.put(headerPair[0], headerPair[headerPair.length - 1]);
        }
        return headers;
    }

    public HashMap<String, String> getParams() {
        if (getQueryString().isEmpty()) {
            return getFormParams();
        }
        else {
            HashMap<String, String> params = getFormParams();
            params.putAll(getQueryString());
            return params;
        }
    }

    public HashMap<String, String> getFormParams() {
        HashMap<String, String> params = new HashMap<String, String>();
        String[] splitInput = inputString.split("\r\n\r\n");
        if (splitInput.length == 2) {
            String[] rawParams = splitInput[1].split(" ");
            for (String param : rawParams) {
                String[] paramPair = param.split("=");
                params.put(paramPair[0], paramPair[paramPair.length - 1]);
            }
        }
        return params;
    }

    public HashMap<String, String> getQueryString() {
        HashMap<String, String> queryString = new HashMap<String, String>();
        String[] queries = searchInput("(?<=\\?)(\\S+)").split("&");
        for (String query : queries) {
            String[] queryPair = query.split("=");
            String key = URLDecoder.decode(queryPair[0]);
            String value = URLDecoder.decode(queryPair[queryPair.length - 1]);
            queryString.put(key, value);
        }
        return queryString;
    }
}

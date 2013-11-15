import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class Request {

    public String inputString;
    public String method;
    public String route;
    public String httpv;
    public String host;
    public String connection;
    public String cacheControl;
    public String[] accept;
    public String userAgent;
    public String[] cookie;
    public Map headers;
    public Map params;

    public Request(String input) {
        this.inputString = input;
        this.method = getMethod();
        this.route = getRoute();
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
        return searchInput("(\\/[^\\s\\?]*)");
    }

    public String getHttpv() {
        return searchInput("(HTTP\\S+)");
    }

    public String getHost() {
        return searchInput("((?<=Host: )([^\\n]+))");
    }

    public String getConnection() {
        return searchInput("((?<=Connection: )([^\\n]+))");
    }

    public String getCacheControl() {
        return searchInput("((?<=Cache-Control: )([^\\n]+))");
    }

    public String[] getAccept() {
        return searchInput("((?<=Accept: )([^\\n]+))").split(",");
    }

    public String getUserAgent() {
        return searchInput("((?<=User-Agent: )([^\\n]+))");
    }

    public String[] getCookie() {
        return searchInput("((?<=Cookie: )([^\\n]+))").split("; ");
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        String[] splitInput = inputString.split("\n");
        for (String header : splitInput) {
            String[] headerPair = header.split("(:)( ?)");
            headers.put(headerPair[0], headerPair[headerPair.length - 1]);
        }
        return headers;
    }

    public Map<String, String> getParams() {
        if (getQueryString().isEmpty()) {
            return getFormParams();
        }
        else {
            Map<String, String> params = getFormParams();
            params.putAll(getQueryString());
            return params;
        }
    }

    public Map<String, String> getFormParams() {
        Map<String, String> params = new HashMap<String, String>();
        String[] splitInput = inputString.split("\n\n");
        if (splitInput.length == 2) {
            String[] rawParams = splitInput[1].split(" ");
            for (String param : rawParams) {
                String[] paramPair = param.split("=");
                params.put(paramPair[0], paramPair[paramPair.length - 1]);
            }
        }
        return params;
    }

    public Map<String, String> getQueryString() {
        Map<String, String> queryString = new HashMap<String, String>();
        String[] queries = searchInput("(?<=\\?)(\\S+)").split("&");
        for (String query : queries) {
            String[] queryPair = query.split("=");
            queryString.put(queryPair[0], queryPair[queryPair.length - 1]);
        }
        return queryString;
    }
}

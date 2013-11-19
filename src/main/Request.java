import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.*;
import java.net.URLDecoder;

public class Request {

    public BufferedReader reader;
    public String headerString;
    public HashMap<String, String> headers;
    public String body;
    public String method;
    public String route;
    public String host;
    public HashMap<String, String> authorization;
    public HashMap<String, Integer> range;
    public HashMap<String, String> params;
    public String log;

    public Request(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
        this.headerString = getHeaderString();
        this.headers = getHeaders();
        this.body = getBody();
        this.method = getMethod();
        this.route = getRoute();
        this.host = headers.get("Host");
        this.authorization = getAuth();
        this.range = getRange();
        this.params = getParams();
        this.log = headerString + body;
    }

    public String getHeaderString() {
        StringBuilder headers = new StringBuilder();
        try {
            String line = reader.readLine();
            while(line != null && !line.equals("")) {
                headers.append(line);
                headers.append("\r\n");
                line = reader.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return headers.toString();
    }

    public HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        String[] rawHeaders = headerString.split("\r\n");
        for (String header : rawHeaders) {
            String[] headerPair = header.split("(: )");
            headers.put(headerPair[0], headerPair[headerPair.length - 1]);
        }
        return headers;
    }

    public String getBody() {
        StringBuilder body = new StringBuilder();
        String length = headers.get("Content-Length");
        if (length != null) {
            while (body.length() < Integer.parseInt(length)) {
                try {
                    body.append((char) reader.read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body.toString();
    }

    public String searchHeaders(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(headerString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else {
            return "";
        }
    }

    public String getMethod() {
        return searchHeaders("(^GET|POST|PUT|OPTIONS|DELETE)");
    }

    public String getRoute() {
        String route = searchHeaders("(\\/[^\\s\\?]*)");
        return URLDecoder.decode(route);
    }

    public HashMap<String, String> getAuth() {
        HashMap<String, String> protocol = new HashMap<String, String>();
        String auth = headers.get("Authorization");
        if (auth != null) {
            String[] pair = auth.split(" ");
            protocol.put(pair[0], pair[1]);
        }
        return protocol;
    }

    public HashMap<String, Integer> getRange() {
        HashMap<String, Integer> rangeMap = new HashMap<String, Integer>();
        String[] range = searchHeaders("((?<=\\nRange: bytes=)([^\\r]+))").split("-");
        if (range.length == 2) {
            rangeMap.put("Start", Integer.parseInt(range[0]));
            rangeMap.put("Stop", Integer.parseInt(range[1]));
            int length = rangeMap.get("Stop") - rangeMap.get("Start") + 1;
            rangeMap.put("Length", length);
        }
        return rangeMap;
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
        String[] rawParams = body.split("&");
        for (String param : rawParams) {
            String[] paramPair = param.split("=");
            params.put(paramPair[0], paramPair[paramPair.length - 1]);
        }
        return params;
    }

    public HashMap<String, String> getQueryString() {
        HashMap<String, String> queryString = new HashMap<String, String>();
        String[] queries = searchHeaders("(?<=\\?)(\\S+)").split("&");
        for (String query : queries) {
            String[] queryPair = query.split("=");
            String key = URLDecoder.decode(queryPair[0]);
            String value = URLDecoder.decode(queryPair[queryPair.length - 1]);
            queryString.put(key, value);
        }
        return queryString;
    }
}

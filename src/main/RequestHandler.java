import java.net.*;
import java.io.*;

public class RequestHandler implements Runnable {

    public Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            String request = getStringFromInputStream(input);
            System.out.print(request);
            // Pass request to app, returns response array
            output.write(("HTTP/1.1 200 OK\n\n<html><body><h1>Hello World</h1></body></html>").getBytes());
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringFromInputStream(InputStream input) throws IOException {
        StringBuffer output = new StringBuffer();
        byte[] bytes = new byte[4096];
        output.append(new String(bytes, 0, input.read(bytes)));
        return output.toString();
    }
}

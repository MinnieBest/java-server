import java.net.*;
import java.io.*;

public class SocketHandler implements Runnable {

    public Socket socket;
    public String directory;

    public SocketHandler(Socket socket, String directory){
        this.socket = socket;
        this.directory = directory;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            Request request = new Request(inputString(input), directory);
            System.out.println(request.inputString);
            Response response = new RequestHandler(request).call();
            output.write(response.output());
            output.flush();
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static String inputString(InputStream input) throws IOException {
        StringBuffer output = new StringBuffer();
        byte[] bytes = new byte[4096];
        output.append(new String(bytes, 0, input.read(bytes)));
        return output.toString();
    }
}

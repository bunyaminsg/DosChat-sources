import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ThreadedEchoServer {

    static final int PORT = 1978;

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
	List<Socket> requests = new ArrayList<Socket>();
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new threa for a client
	    synchronized(requests)
	    {
	        requests.add(socket);
	    }
            new EchoThread(socket, requests).start();
        }
    }
}

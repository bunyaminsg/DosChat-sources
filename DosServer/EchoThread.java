import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class EchoThread extends Thread {
    protected Socket socket;
    protected ArrayList<Socket> clients;
    public EchoThread(Socket clientSocket, List<Socket> clients) {
        this.socket = clientSocket;
	this.clients = (ArrayList<Socket>) clients;
    }

    public void run() {
        InputStream inp = null;
        DataInputStream dinp = null;
        DataOutputStream out = null;
	System.out.println("New Client");
        try {
            inp = socket.getInputStream();
            dinp = new DataInputStream(inp);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = dinp.readUTF();
		if(line.startsWith("quit")){
			synchronized(clients)
                	{
                   	for(Socket s: clients)
                    	{
                       	if(s!=this.socket)
                       		try{
                                	DataOutputStream sout  = new DataOutputStream(s.getOutputStream());
                                	sout.writeUTF(line.split("&col;")[1] + "quitted.");
                                	sout.flush();
                        	} catch (IOException e) {
                                	return;
                        	}
                	     }
                	}
			return;
		}
		synchronized(clients)
                {
                   for(Socket s: clients)
                    {
                       if(s!=this.socket)
                       try{
                                DataOutputStream sout  = new DataOutputStream(s.getOutputStream());
                                sout.writeUTF(line);
                                sout.flush();
                        } catch (IOException e) {
                                return;
                        }
                     }
                }

            } catch (IOException e) {
                synchronized(clients){clients.remove(socket);}
                return;
            }
        }
    }
}

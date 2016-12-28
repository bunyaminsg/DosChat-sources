import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.util.Random;
import jline.console.ConsoleReader;
import print.color.ColoredPrinter;
import print.color.Ansi.*;

public class DosClient
{
   public static void main(String [] args) throws IOException
   {
      ConsoleReader c_in = new ConsoleReader();
      Scanner inp = new Scanner(System.in);
      String serverName = "server-ip"; // CHANGE TO YOUR SERVER IP
      String userName = "";
      int color = 30;
      int port = 1978;
      try
      {
         System.out.println("Connecting to " + serverName +
		 " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to " 
		 + client.getRemoteSocketAddress());
         //System.out.print("User Name:");
         userName = c_in.readLine("User Name: ");
         Random r = new Random();
         color = r.nextInt(7) + 31;
         
         String welcome = "\033[31m**************************************************\n"
                        + "*                                                *\n"
                        + "*                                                *\n"
                        + "*                                                *\n"
                        + "*                                                *\n"
                        + "*                  DosClientv1.2                 *\n"
                        + "*                                                *\n"
                        + "*                                                *\n"
                        + "*                                                *\n"
                        + "*                                                *\n"
                        + "**************************************************\n\033[;;m";

         c_in.println();
         c_in.println(welcome);
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);

	 
         InputStream inFromServer = client.getInputStream();
         DataInputStream in =
                        new DataInputStream(inFromServer);
	new DosReader(userName, in, out, c_in);
        
        while(true){
            String msg = c_in.readLine("\033[" + color + "m" + userName + ">>\033[;;m ");
            //System.out.println("msg: " + msg);
            out.writeUTF("\033[" + color + "m" + "&col;" + userName + "&col;" + msg);
            if(msg.equals("quit")){
                System.exit(0);
            }
            
            
        }
         
      }catch(ConnectException e){
          System.out.println("Server is closed.");
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}

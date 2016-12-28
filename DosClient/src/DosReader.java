
import java.io.*;
import jline.console.ConsoleReader;
import java.util.Date;

public class DosReader extends Thread
{
	DataInputStream in = null;
	DataOutputStream out = null;
        String userName = "";
        ConsoleReader c_in = null;
        
        @Override
	public void run(){
		while(true){
			try{
				String s_in = in.readUTF();
				Date dt = new Date();
				String[] msg = s_in.split("&col;");
                                if(msg.length > 2){
                                    c_in.print("\r");
                                    c_in.print(msg[0] + "[" + dt.toString().split(" ")[3] + "] " 
                                    				+ msg[1] + ":\033[0;;m " + msg[2] + "\n");
                                    //cp.print(userName + "> " + inbuf.getBuffer(), Attribute.NONE, FColor.RED,BColor.BLACK);
                                    //System.out.print(userName + ": ");
                                    c_in.redrawLine();
                                    c_in.flush();
                                    
                                    
                                }
			}catch(IOException e)
		        {
				break;
                                
		        }
		}
	}

	public DosReader(String userName, DataInputStream inp, DataOutputStream outp, ConsoleReader con)
	{
            this.userName = userName;
            this.in = inp;
            this.out = outp;
            this.c_in = con;
            this.start();
	}

}

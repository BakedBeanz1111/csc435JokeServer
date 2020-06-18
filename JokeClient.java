import java.io.*;
import java.net.*;

public class JokeClient {
	public static void main(String[]args){
		String serverName;
		if(args.length < 1)
			serverName = "localhost";
		else
			serverName = args[0];
		
		System.out.println("Amad Ali's Inet Client, 1.8.\n");
		System.out.println("Using server: " + serverName + ", Port: 1565");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String input;
			do {
				System.out.println("Suck my dick"); //Change to an appropriate joke, not an insult please
				System.out.flush();
				
				input = in.readLine();
			}
			while (input.indexOf("quit") < 0);
			System.out.println("Canceled by user request.");
		}
		catch (IOException x) {
			x.printStackTrace();
		}
	}
	
	static String toText(byte ip[]) {
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < ip.length; i++) {
			if(i>0)
				result.append(".");
			result.append(0xff & ip[i]);
		}
		return result.toString();
	}
	
	static void getRemoteAddress(String name, String serverName) {
		Socket sock;
		BufferedReader fromServer;
		PrintStream toServer;
		String textFromServer;
		
		try {
			sock = new Socket(serverName, 1565);
			
			fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			toServer = new PrintStream(sock.getOutputStream());
			
			toServer.println(name);
			toServer.flush();
			
			for(int i = 1; i <= 3; i++) {
				textFromServer = fromServer.readLine();
				if(textFromServer != null)
					System.out.println(textFromServer);
			}
			
			sock.close();
		} 
		catch (IOException x) {
			System.out.println("SOcket error.");
			x.printStackTrace();
		}
	}
}
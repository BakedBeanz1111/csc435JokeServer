import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class InetClient {

    public static void main(String args[]) {

        String serverName;

        if(args.length < 1)
            serverName = "localhost";
        else
            serverName = args[0];

        System.out.println("Amad Ali's Inet Client, 1.8.\n");
        System.out.println("Using server: " + serverName + ", Port: 9001");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Main Loop for command input
        try {

            String name;

            do {

                System.out.print("Enter a hostname or an IP address, (quit) to end: ");
                System.out.flush();

                name = in.readLine();

                if(name.indexOf("quit") < 0)
                    getRemoteAddress(name, serverName);
            }

            //If "quit" is typed, exit client application
            while(name.indexOf("quit") < 0);
                System.out.println("Cancelled by user request.");

        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //Format IP address as string
    static String toText(byte ip[]) {

        StringBuffer result = new StringBuffer();

        for(int i = 0; i<ip.length; i++) {

            if(i>0)
                result.append(".");
            result.append(0xff & ip[i]);
        }

        return result.toString();
    }

    //Get remote address of queries website
    static void getRemoteAddress(String name, String serverName){

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        //Try/Catch loop to process thread between server and client
        try {

            sock = new Socket(serverName, 9001);
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(name);
            toServer.flush();

            for (int i = 1; i <= 3; i++) {

                textFromServer = fromServer.readLine();

                if (textFromServer != null)
                    System.out.println(textFromServer);
            }

            sock.close();
        } catch(IOException x) {

            System.out.println("Socket errors.");
            x.printStackTrace();
        }
    }
}
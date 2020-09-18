import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class JokeClient {

    public static void main(String args[]) {

        String serverName;

        if(args.length < 1)
            serverName = "localhost";
        else
            serverName = args[0];

        System.out.println("Amad Ali's Joke Client, 1.8.\n");
        System.out.println("Using server: " + serverName + ", Port: 9001");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Main Loop for command input
        try {

            String command;

            do {

                getJoke(serverName);

                System.out.print("A joke should appear before and after this line: ");
                System.out.flush();

                command = in.readLine();

                if(command.indexOf("quit") < 0)
                    getJoke(serverName);
            }

            //If "quit" is typed, exit client application
            while(command.indexOf("quit") < 0);
            System.out.println("Cancelled by user request.");

        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //Get joke from Server
    static void getJoke(String serverName){

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        //Try/Catch loop to process thread between server and client
        try {

            sock = new Socket(serverName, 9001);
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

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
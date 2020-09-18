import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

//Get joke from Server
//The Steps to getting a joke
//Step 1) Connect to Joke Server
//Step 2) Get the Joke from the server
//Step 3) Close connection to the server
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

            String name;

            do {

                System.out.print("Press anything and then enter, (quit) to end: ");
                System.out.flush();

                name = in.readLine();

                if(name.indexOf("quit") < 0)
                    getJoke(serverName);
            }

            //If "quit" is typed, exit client application
            while(name.indexOf("quit") < 0);
            System.out.println("Cancelled by user request.");

        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //Get joke from Server
    //The Steps to getting a joke
    //Step 1) Connect to Joke Server
    //Step 2) Get the Joke from the server
    //Step 3) Close connection to the server
    static void getJoke(String serverName){

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        //Attempt to get a response from the server
        try {

            //Step 1) Connect to Joke Server
            sock = new Socket(serverName, 9001);
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            textFromServer = fromServer.readLine();

            if (textFromServer != null)
                System.out.println(textFromServer);

            sock.close();
        } catch(IOException x) {

            System.out.println("Socket errors.");
            x.printStackTrace();
        }
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class JokeClient {

    public static void main(String args[]) {

        String serverName;
        int port = 9001;

        //When launching the client, if there is a command line argument, set that as the serverName
        if(args.length < 1)
            serverName = "localhost";
        else
            serverName = args[0];

        System.out.println("Amad Ali's Joke Client, 1.8.\n");
        System.out.println("Using server: " + serverName + ", Port: " + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Main loop of user input
        try {

            System.out.println("Please enter anything! Type (quit) to exit: ");
            String name = in.readLine();
            String cmd;

            do {

                System.out.flush();

                cmd = in.readLine();

                getJoke(name, serverName, port);
            }
            while (cmd.indexOf("quit") < 0);


        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //getJoke takes the name of the client, the server name and the port
    //Gets a joke from the server with the supplied connection info(name and port)
    //And returns a joke!
    static void getJoke(String name, String serverName, int port) {

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try {

            sock = new Socket(serverName, port);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(name);
            toServer.println(serverName);
            toServer.println(port);

            textFromServer =fromServer.readLine();

            System.out.println(textFromServer);

            sock.close();

        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
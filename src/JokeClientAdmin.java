import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class JokeClientAdmin {

    //if isJoke is true, mode is Joke Mode
    //if isJoke is false, mode is Proverb Mode
    static boolean isJoke = true;
    //When isJoke is true, currentMode is Joke
    //When isJoke is false, currentMode is Proverb
    static String currentMode = "Joke";

    public static void main(String args[]) {

        String serverName;
        int port = 9001;

        //When launching the client, if there is a command line argument, set that as the serverName
        if(args.length < 1)
            serverName = "localhost";
        else
            serverName = args[0];

        System.out.println("Amad Ali's Joke Admin Client, 1.8.\n");
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

                if(cmd.equals("")) {

                    if(currentMode.equals("Joke")) {

                        System.out.println("current mode was: " + currentMode);
                        currentMode = "Proverb";
                        isJoke = false;
                        System.out.println("current mode is: " + currentMode);
                        System.out.println("isJoke: " + isJoke);
                    }
                    else {

                        System.out.println("current mode was: " + currentMode);
                        currentMode = "Joke";
                        isJoke = true;
                        System.out.println("current mode is: " + currentMode);
                        System.out.println("isJoke: " + isJoke);
                    }
                }

                changeMode(currentMode, serverName, port);
            }
            while (cmd.indexOf("quit") < 0);


        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    //changeMode takes the name, the servername, and the port and changes from Joke to Proverb/Proverb to Joke
    private static void changeMode(String currentMode, String serverName, int port) {

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        if (isJoke)
            currentMode = "Joke";
        else
            currentMode = "Proverb";

        try {

            sock = new Socket(serverName, port);
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(currentMode);
            toServer.flush();

            sock.close();

        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}

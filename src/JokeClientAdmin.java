import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class JokeClientAdmin {

    //Defaults as Joke
    static String currentMode = "Joke";

    public static void main(String args[]) {

        String serverName;
        int port; //Change Port to production port number from Deliverables document

        //When launching the client, if there is a command line argument, set that as the serverName, next command line argument is port
        if(args.length >= 1) {

            //When starting the client, if it has command line arguments, the 1st argument is the serverName, the 2nd argument is the port
            //Still needs input validation
            serverName = args[1]; //Has not been tested with hosting server on different machine
            port = Integer.parseInt(args[2]);
        }
        else {

            serverName = "localhost";
            port = 9001;
        }

        System.out.println("Amad Ali's Joke Admin Client! ");
        System.out.println("Using server: " + serverName + ", Port: " + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Main loop of user input
        try {

            System.out.println("Please enter anything! Type (quit) to exit: ");
            String name = in.readLine();
            String cmd;

            //With current implementation, just tapping enter switches mode on JokeClientAdmin
            //Main loop for user input on admin console
            do {

                System.out.flush();

                cmd = in.readLine();

                //Press enter on a blank input to change mode
                if(cmd.equals("")) {

                    if(currentMode.equals("Joke")) {

                        currentMode = "Proverb";
                    }
                    else {

                        currentMode = "Joke";

                    }
                }
                changeMode(currentMode, serverName, port);
            }
            while (cmd.indexOf("quit") < 0);
        }
        catch (IOException x) {
            x.printStackTrace();
        }
    }

    //changeMode takes the current mode, the servername, and the port and changes from Joke to Proverb/Proverb to Joke
    private static void changeMode(String currentMode, String serverName, int port) {

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try {

            sock = new Socket(serverName, port);
            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(currentMode);
            toServer.flush();

            sock.close();
        }
        catch (IOException x) {
            x.printStackTrace();
        }
    }
}

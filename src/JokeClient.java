import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class JokeClient {

    //Global Variables for the client to keep track of the messages its received
    //I understand that global variables are bad programming practices but I see no simple solution to this problem
    static int jokeCounter = 0;
    static int proverbCounter = 0;

    public static void main(String args[]) {

        String serverName;
        int port; //Change Port to production port number from Deliverables document

        //When launching the client, if there is a command line argument, set that as the serverName, next command line argument is port
        if(args.length >= 1) {

            //When starting the client, if it has command line arguments, the 1st argument is the serverName, the 2nd argument is the port
            serverName = args[1];
            port = Integer.parseInt(args[2]);
        }
        else {

            serverName = "localhost";
            port = 9001;
        }

        System.out.println("Amad Ali's Joke Client.\n");
        System.out.println("Using server: " + serverName + ", Port: " + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Main loop of user input
        try {

            System.out.println("Please enter anything to identify as! Type (quit) to exit: ");
            String name = in.readLine();
            String cmd;

            do {

                System.out.flush();

                cmd = in.readLine();

                getMessage(name, serverName, port, jokeCounter, proverbCounter);
            }
            while (cmd.indexOf("quit") < 0);
        }
        catch (IOException x) {
            x.printStackTrace();
        }
    }

    //getJoke takes the name of the client, the server name and the port
    //Gets a joke from the server with the supplied connection info(name and port)
    //And returns a message!
    static void getMessage(String name, String serverName, int port, int jokes, int proverbs) {

        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try {

            sock = new Socket(serverName, port);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream())); //receiving data from server
            toServer = new PrintStream(sock.getOutputStream()); //Sending data to server

            //Current Format of Message to Server
            //Name
            //jokeCounter
            //proverbCounter

            toServer.println(name);
            toServer.println(jokes);
            toServer.println(proverbs);
            toServer.flush();

            textFromServer = fromServer.readLine();
            System.out.println("textFromServer: " + textFromServer);

            if(textFromServer.contains("Joke")) {
                if (jokeCounter == 3) {

                    System.out.println("Joke Cycle Complete!");
                    jokeCounter = 0;
                    System.out.println("Joke Counter Reset!");
                } else {

                    jokeCounter++;
                    System.out.println("Joke incremented!");
                }
            }
            else if(textFromServer.contains("Proverb")) {
                    if (proverbCounter == 3) {

                        System.out.println("Proverb Cycle Complete!");
                        proverbCounter = 0;
                        System.out.println("Proverb Counter Reset!");
                    } else {

                        proverbCounter++;
                        System.out.println("Proverb incremented!");
                    }
            }
            else
                System.out.println("I'm not incrementing anything!");

            System.out.println(textFromServer);

            sock.close();
        }
        catch (IOException x) {
            x.printStackTrace();
        }
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

//Our worker class extends the java thread class to expand functionality
class Worker extends Thread {

    Socket sock;

    //Dummy joke and proverb just to get this working properly

    final int totalJokes = 3; //4 Jokes

    static int jokeCount = 0;

    final String[] jokePrefix = {
            "JA",
            "JB",
            "JC",
            "JD"
    };

    final String[] jokes = {
            "Joke #1",
            "Joke #2",
            "Joke #3",
            "Joke #4"
    };

    final int totalProverbs = 3; //4 Proverbs

    static int proverbCount = 0;

    final String[] proverbPrefix = {
            "PA",
            "PB",
            "PC",
            "PD"
    };

    final String[] proverbs = {
            "Proverb #1",
            "Proverb #2",
            "Proverb #3",
            "Proverb #4"
    };

    //isJoke is the boolean value for confirming the mode between Joke and Proverb
    //if isJoke is true, the server is in Joke mode, so display jokes
    //if isJoke is false, the server is in proverb mode, so display proverbs
    static boolean isJoke = true;

    //Default constructor for creating new worker object
    Worker(Socket s) {

        sock = s;
    }

    //This function executes upon each new creation of worker
    public void run() {

        PrintStream out = null;
        BufferedReader in = null;

        try {

            //input from the socket connection
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            //Text output to send to client
            out = new PrintStream(sock.getOutputStream());

            //Establishing connection loop
            try {

                String name = in.readLine();

                if (name.equals("Joke")) {

                    isJoke = true;
                }

                else if (name.equals("Proverb")) {

                    isJoke = false;
                }
                sendMessage(name, out, isJoke);
                printServerMode(name, out);
            }
            catch (IOException x) {

                System.out.println("Server read error");
                x.printStackTrace();
            }
        }
        catch (IOException ioe) {

            System.out.println(ioe);
        }

        System.out.println("I did a thing");
    }

    //sendMessage takes the name of the client and sends them a message!
    //Does not output to Server Console!!!
    //Does output to Client Console!!!
    private void sendMessage(String name, PrintStream out, Boolean isJoke) {

        try {

            System.out.println("Sending message to " + name + "...");

            while (true) {

                //isJoke is only being set to true!
                if(isJoke) {

                    if(jokeCount < totalJokes) {

                        out.println(name + " " + jokePrefix[jokeCount] + " " + jokes[jokeCount]);

                        if(jokeCount == 4)
                            jokeCount = 0;
                    }

                    jokeCount++;
                }

                else {

                    if (proverbCount < totalProverbs) {

                        out.println(name + " " + proverbPrefix[proverbCount] + " " + proverbs[proverbCount]);

                        if (proverbCount == 4)
                            proverbCount = 0;
                    }
                    proverbCount++;
                }
            }
        }
        catch (Exception ex) {

            out.println("Failed to send message to " + name);
        }
    }

    //printServerMode outputs
    //If an Admin executes this command, the mode changes
    //else do nothing
    //Does not output to Server console!!!
    static void printServerMode(String name, PrintStream out){

        //This doesn't execute ever
        System.out.println("Server mode changed!");

        try {

            while (name.equals("Admin")) {

                out.println(name + " changed the mode!");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

public class JokeServer {

    public static void main(String args[]) throws  IOException {

        int q_len = 6;
        int port = 9001; //Change Port Number
        Socket sock;

        ServerSocket servsocket = new ServerSocket(port, q_len); //fix variable name

        System.out.println("Amad Ali's Joke server is starting up, listening at port 9001.\n");

        //Main loop of Server waiting to receive input and doing work based on input
        while(true) {

            sock = servsocket.accept();
            new Worker(sock).start();
        }
    }
}
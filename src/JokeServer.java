import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//Our worker class extends the java thread class to expand functionality
class Worker extends Thread {

    Socket sock;

    //Dummy joke and proverb just to get this working properly
    final String[] jokePrefix = {

            "JA",
            "JB",
            "JC",
            "JD"
    };

    static String[] jokes = {

            "Joke #1",
            "Joke #2",
            "Joke #3",
            "Joke #4"
    };

    final String[] proverbPrefix = {

            "PA",
            "PB",
            "PC",
            "PD"
    };

    static String[] proverbs = {

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

                //Reading in data from server
                String name = in.readLine();
                String jokes = in.readLine();
                String proverbs = in.readLine();

                System.out.println(jokes);
                System.out.println(proverbs);

                if(name.equals("Joke") || name.equals("Proverb"))
                    if(name.equals("Joke")) {

                        isJoke = true;
                        System.out.println("Joke Mode!");
                    }
                    else if(name.equals("Proverb")) {

                        isJoke = false;
                        System.out.println("Proverb Mode!");
                    }
                    else
                        System.out.println("It shouldn't be able to get here!");

                else {

                    int jokeCounter = Integer.parseInt(jokes);
                    int proverbCounter = Integer.parseInt(proverbs);

                    sendMessage(name, out, isJoke, jokeCounter, proverbCounter);
                }
            }
            catch (IOException x) {

                System.out.println("Server read error");
                x.printStackTrace();
            }
        }
        catch (IOException ioe) {

            System.out.println(ioe);
        }
    }

    //sendMessage takes the name of the client and sends them a message!
    //Does not output to Server Console!!!
    //Does output to Client Console!!!
    private void sendMessage(String name, PrintStream out, Boolean isJoke, int jokeCounter, int proverbCounter) {

        try {

            while (true) {

                if(isJoke) {

                    out.println(name + " " + jokePrefix[jokeCounter] + " " + jokes[jokeCounter]);

                    if(jokeCounter == 3)
                        shuffleArray(jokes);
                }
                else {

                    out.println(name + " " + proverbPrefix[proverbCounter] + " " + proverbs[proverbCounter]);
                    if(proverbCounter == 3)
                        shuffleArray(proverbs);
                }
            }
        }
        catch (Exception ex) {

            out.println("Failed to send message to " + name);
            ex.printStackTrace();
        }
    }

    //Re-Randomize the message for each client conversation at the start of the 4 message cycle
    //Input: Take one of the arrays I declared previously
    //Outputs: Shuffles the array
    //Method was taken from here: https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    //I googled "Java shuffle array of strings"
    public static void shuffleArray(String[] stringArray) {

        //I've attempted this two different ways and in both cases I lose string entries until I'm only returning one message over and over

        Random rnd = ThreadLocalRandom.current();

        for(int i = stringArray.length - 1; i > 0; i--) {

            int index = rnd.nextInt(i + 1);

            //simple swap
            String a = stringArray[index];
            stringArray[index] = stringArray[i];
            stringArray[i] = a;
        }

        //The results of the code below is identical to the code above
        //It shouldn't be called shuffleArray, it should be called keepExecutingTilOnlyOneStringRemains

        //https://www.journaldev.com/32661/shuffle-array-java
        //List<String> stringList = Arrays.asList(stringArray);
        //Collections.shuffle(stringList);
        //stringArray = stringList.toArray(new String[stringList.size()]);

        //Debug output
        //System.out.println(Arrays.toString(stringArray));

    }
}

public class JokeServer {

    public static void main(String args[]) throws  IOException {

        int q_len = 6;
        int primaryPort = 9001; //Change Port Number
        int secondaryPort;
        Socket sock;

        ServerSocket serverSocket;

        System.out.println("Amad Ali's Joke server is starting up, listening at port 9001.\n"); //Change Port Number

        //If there is a commandLine argument, set the port to that commandLine argument
        //EX: Launch JokeServer on primaryPort
        //EX: Java JokeServer
        //EX:
        //EX: Launch JokeServer on secondaryPort
        //EX: Java JokeServer 9002
        //!!: Will fail if non integer is provided
        //!!: To fix, add input validation on command line argument to check for Integer value
        if(args.length == 1){
            secondaryPort = Integer.parseInt(args[1]);
            serverSocket = new ServerSocket(secondaryPort , q_len);
        }
        else
            serverSocket = new ServerSocket(primaryPort, q_len);

        //Main loop of Server waiting to receive input and doing work based on input
        while(true) {

            sock = serverSocket.accept();
            new Worker(sock).start();
        }
    }
}
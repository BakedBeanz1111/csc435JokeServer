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
    String joke = "Joke #1";
    String proverb = "Proverb #1";

    //isJoke is the boolean value for confirming the mode between Joke and Proverb
    //if isJoke is true, the server is in Joke mode, so display jokes
    //if isJoke is false, the server is in proverb mode, so display proverbs
    boolean isJoke = true;

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

                System.out.println(name);

                if (name.equals("Joke")) {

                    isJoke = true;
                }
                else if (name.equals("Proverb")) {

                    isJoke = false;
                }

                sendMessage(name, out);
                //printServerMode(name, out);

            } catch (IOException x) {

                System.out.println("Server read error");
                x.printStackTrace();
            }

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
    }

    //sendMessage takes the name of the client and sends them a message!
    //Does not output to Server Console!!!
    private void sendMessage(String name, PrintStream out) {

        try {

            while (true) {

                if(isJoke)
                    out.println("name: " + name + " " + "out: " + out + " " + "message: " + joke);
                else
                    out.println("name: " + name + " " + "out: " + out + " " + "message: " + proverb);
            }

        } catch (Exception ex) {

            out.println("Failed to send joke to " + name);
        }
    }

    //printServerMode outputs
    //If an Admin executes this command, the mode changes
    //else do nothing
    //Does not output to Server console!!!
    static void printServerMode(String name, PrintStream out){

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
        int port = 9001;
        Socket sock;

        ServerSocket servsocket = new ServerSocket(port, q_len);

        System.out.println("Amad Ali's Joke server is starting up, listening at port 9001.\n");

        //Main loop of Server waiting to receive input and doing work based on input
        while(true) {

            sock = servsocket.accept();
            new Worker(sock).start();
        }
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

//Our worker class extends the java thread class to expand functionality
class Worker extends Thread {

    Socket sock;
    String joke = "Joke #1";

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

                sendJoke(name, out);

            } catch (IOException x) {

                System.out.println("Server read error");
                x.printStackTrace();
            }

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
    }

    //sendJoke takes the name of the client and sends them a joke!
    private void sendJoke(String name, PrintStream out) {

        try {

            while (true) {

                out.println("name: " + name + " " + "out: " + out + " " + "joke: " + joke);
            }

        } catch (Exception ex) {

            out.println("Failed to send joke to " + name);
        }
    }
}

public class JokeServer {

    public static void main(String args[]) throws  IOException {

        int q_len = 6;
        int port = 9001;
        Socket sock;

        ServerSocket servsocket = new ServerSocket(port, q_len);

        System.out.println("Amad Ali's Joke server 1.8 starting up, listening at port 9001.\n");

        //Main loop of Server waiting to receive input and doing work based on input
        while(true) {

            sock = servsocket.accept();
            new Worker(sock).start();
        }
    }
}
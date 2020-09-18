import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

//Get joke from Server
//The Steps to getting a joke
//Step 1) Connect to Joke Server
//Step 2) Get the Joke from the server
//Step 3) Close connection to the server
class Worker extends Thread {

    Socket sock;

    //Worker Object
    Worker (Socket s) {

        sock = s;
    }

    String joke = "Two neutrons walk into a bar and order drinks! They ask for the tab, and the bar tender says 'no charge!'";

    //This method is for when the worker thread starts
    public void run() {

        PrintStream out = null; //Output String starts as null
        BufferedReader in = null; //Input String starts as nul

        try {

            in = new BufferedReader(new InputStreamReader(sock.getInputStream())); //Information from the socket connection
            out = new PrintStream(sock.getOutputStream()); //Output Information from the socket connection
            try {

                String name;

                //it freezes here
                name = in.readLine();

                System.out.println("Sending joke to whoever typed: " + name);
                printJoke(out);
            }

            //If it can't communicate, output the error
            catch (IOException x) {

                System.out.println("Server read error");
                x.printStackTrace();
            }

            //Step 3) Close Connection From Server
            sock.close();
        }

        //If it can't communicate, output the error
        catch (IOException ioe) {

            System.out.println(ioe);
        }
    }

    static void printJoke(PrintStream out){

        String joke = "Two neutrons walk into a bar and order drinks! They ask for the tab, and the bar tender says 'no charge!'";
        int i = 0;

        out.println(joke);

        try {

            while(i == 0) {
                out.println(joke);
                i++;
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

public class JokeServer {

    public static void main(String a[]) throws IOException {

        int q_len = 6; //Length of the queue
        int port = 9001; //Port used to listen for incoming connections
        Socket sock;

        ServerSocket servsock = new ServerSocket(port, q_len);

        System.out.println("Amad Ali's Joke server 1.8 starting up, listening at port 9001.\n");

        //Main Loop to accept incoming connections to output to server screen
        while (true) {

            sock = servsock.accept();
            System.out.println("I got something!");
            new Worker(sock).start();
        }
    }
}
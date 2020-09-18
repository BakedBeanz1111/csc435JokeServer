import java.io.*;	//Get the input output libraries
import java.net.*;	//Get the java networking libraries

//Worker class extends Thread class
class Worker extends Thread {

    Socket sock;
    Worker (Socket s) {sock = s;} //I'm not entirely sure what this syntax means yet but I'm reading into it

    public void run() {

        PrintStream out = null; //Output String starts as null
        BufferedReader in = null; //Input String starts as nul

        try {

            in = new BufferedReader(new InputStreamReader(sock.getInputStream())); //Information from the socket connection
            out = new PrintStream(sock.getOutputStream()); //Output Information from the socket connection

            //If the name exists, output the connection information on the Server Client
            try {

                String name;
                name = in.readLine();
                System.out.println("Sending joke to whoever typed: " + name);
                printJoke(out);
            }

            //If it can't communicate, output the error
            catch (IOException x) {

                System.out.println("Server read error");
                x.printStackTrace();
            }

            sock.close();
        }

        //If it can't communicate, output the error
        catch (IOException ioe) {

            System.out.println(ioe);
        }
    }

    static void printJoke(PrintStream out){

        out.println("Two neutrons walk into a bar and order drinks! They ask for the tab, and the bar tender says 'no charge!'");
    }
}
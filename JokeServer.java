import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InetServer {

    public static void main(String a[]) throws IOException {

        int q_len = 6; //Length of the queue
        int port = 9001; //Port used to listen for incoming connections
        Socket sock;

        ServerSocket servsock = new ServerSocket(port, q_len);

        System.out.println("Amad Ali's Inet server 1.8 starting up, listening at port 9001.\n");

        //Main Loop to accept incoming connections to output to server screen
        while (true) {

            sock = servsock.accept();
            new Worker(sock).start();
        }
    }
}
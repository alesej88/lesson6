import org.w3c.dom.ls.LSOutput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SimpleSingleConsoleClient {

    private static final String HOST = "localhost";
    private static final int PORT = 12256;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread clientConsoleThread;


    public static void main(String[] args) {
        new SimpleSingleConsoleClient().runClient();

    }
    private void runClient(){
        try(Socket socket = new Socket(HOST, PORT)){
        System.out.println("Client started!");
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        startConsoleThread();

        while (true){
String msg = in.readUTF();
if (msg.equals("/end")){
shutdownClient(socket);
break;
        }
            System.out.println("Received: " + msg);
    }
}

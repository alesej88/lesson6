import org.w3c.dom.ls.LSOutput;

import java.io.*;
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
    private void runClient() {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Client started!");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            startConsoleThread();

            while (true) {
                String msg = in.readUTF();
                if (msg.equals("/end")) {
                    shutdownClient(socket);
                    break;
                }
                System.out.println("Received: " + msg);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("FINISHED");
        }
        }
        private void shutdownClient(Socket socket) throws IOException{
        clientConsoleThread.interrupt();
        socket.close();
            System.out.println("Client stopped");
        }
        private void startConsoleThread(){
        clientConsoleThread = new Thread(() -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("You can enter message from server");
            try{
                while (!Thread.currentThread().isInterrupted());
                if(bufferedReader.ready()){
                    String messageFromClient = bufferedReader.readLine();
                    out.writeUTF(messageFromClient);
                    Thread.sleep(200);
                }

            }catch (IOException | InterruptedException e){
                e.printStackTrace();
            }

    });
        clientConsoleThread.start();
        }
    }


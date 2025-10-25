package za.ac.cput.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket listener;
    private Socket client;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Server() {
        try {
            listener = new ServerSocket(6666, 1);
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }

    }

    public void listen() {
        try {
        System.out.println("Server is listening");
        client = listener.accept();
        System.out.println("Now we processClient");
        processClient();
    }catch(IOException ioe){
            System.out.println("IO Exception found:"+ioe.getMessage());
    }
    }

    private void getStreams() throws IOException {
 
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

    }

    public void closeAll() {
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void processClient() {
        try {
            getStreams();
            do{
               
            }while(true);

        }catch(IOException ioe){
            System.out.println("IO Exception:"+ioe.getMessage());
        }finally{
            closeAll();
        }
    }
    

    public static void main(String[] args) {
            Server server = new Server();
            server.listen();
        
    }
}
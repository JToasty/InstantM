import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Matt on 4/25/17.
 */
public class Server {

    //make port bigger then 2000
    private static int port;
    private static ServerSocket server;
    private static Socket clientsocket;
    private static BufferedReader input;
    private static PrintStream output;
    private static String LinesToGet;

    public Server(int port){
        this.port = port;
        try{
        server = new ServerSocket(port);
        }catch (IOException e){
            System.out.println("Failed to create server socket @"+port);
            System.out.println(e);
        }
    }

    public static void run(){
        try {
            createConnection();
            makeServerDataInputStream();
            makeServerPrintStream();
            System.out.println("Server has been started. To stop it, end this process on the terminal.");
            while (true) {
                LinesToGet = input.readLine();
                int index = LinesToGet.indexOf("*");
                System.out.println("Received Message: " + LinesToGet.substring(0,index));
                output.println("Server: " + LinesToGet.substring(0,index));
            }
        }catch (IOException e) {
            System.out.println("Failed to echo messages!");
            System.out.println(e);
        }

    }

    public static void createConnection(){
        try {
            clientsocket = server.accept();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void makeServerDataInputStream(){
        try {
            input = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
        }
        catch (IOException e) {
            System.out.println("Failed to create input stream for server.");
        }
    }

    public static void makeServerPrintStream(){
        try {
            output = new PrintStream(clientsocket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println("Failed to create output stream for server.");
        }
    }


    public static void end(){
        try {
            output.close();
            input.close();
            clientsocket.close();
            server.close();
        }
        catch (IOException e) {
            System.out.println("Failed to close streams for server.");
        }
    }

    public static void main(String[] args){

        Server server = new Server(1212);
        while(true) {
            server.run();
        }
    }


    public static int getPort() {
        return port;
    }

    public static ServerSocket getServer() {
        return server;
    }

    public static BufferedReader getInput() {
        return input;
    }

    public static PrintStream getOutput() {
        return output;
    }

    public static Socket getClientsocket() {
        return clientsocket;
    }
}

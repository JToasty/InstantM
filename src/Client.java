/**
 * Created by Matthew Caggiano on 4/25/17.
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    private static String name;
    //make port bigger then 2000
    private static int port;
    private static Socket user;
    private static BufferedReader input;
    private static BufferedReader lines;
    private static PrintStream output;

    public Client(String name, int port){
        this.port = port;
        this.name = name;
        try {
            user = new Socket(name, port);
            lines = new BufferedReader(new InputStreamReader(System.in));

        }catch (IOException e)
        {
            System.out.println("Failed to create client socket @"+port);
            System.out.println(e);
        }
    }

    public static void run(){
        makeClientDataInputStream();
        makeClientPrintStream();
    }

    public static void makeClientDataInputStream(){
        try {
            input = new BufferedReader(new InputStreamReader(user.getInputStream()));
        }catch (IOException e){
            System.out.println("Failed to create input stream for "+name+".");
        }
    }

    public static void makeClientPrintStream(){
        try {
            output = new PrintStream(user.getOutputStream());
        }
        catch (IOException e) {
            System.out.println("Failed to create output stream for "+name+".");
        }
    }

    public static void sendMessage(){
        try {
            String LineToSend;
            System.out.println("Use argument ('message' *) to send a message.");

            output.println(lines.readLine());
            while ((LineToSend = input.readLine()) != null) {
                System.out.println(LineToSend);
               if (LineToSend.indexOf("*") != -1) {
                   break;
               }
                output.println(input.readLine());
            }
            //end();
        }catch (IOException e){
            System.out.println("Message send failed.");
        }
    }

    public static void end(){
        try {
            output.close();
            input.close();
            user.close();
        }
        catch (IOException e) {
            System.out.println("Failed to close streams for "+name+".");
        }
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter ip of server.");
        Client client = new Client(scan.next(),1212);
        client.run();
        while(true) {
            client.sendMessage();
        }

    }

    //Dont need atm
    public static int getPort() {
        return port;
    }

    public static String getName() {
        return name;
    }

    public static Socket getUser() {
        return user;
    }

    public static BufferedReader getInput() {
        return input;
    }

    public static PrintStream getOutput() {
        return output;
    }
}

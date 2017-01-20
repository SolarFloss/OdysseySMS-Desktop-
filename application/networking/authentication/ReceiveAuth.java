package application.networking.authentication;

import application.Controller;
import application.Main;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nicholas on 1/6/2017.
 */
public class ReceiveAuth implements Runnable{
    private static ServerSocket serverSocket;
    private static ObjectInputStream objectInputStream;
    private static boolean running = true;
    private static Socket socket;
    private final int PORT = 24680;
    private boolean done = false;
    private String[] info;


    private void receive(){
        System.out.println("Waiting for authentication");
        try {

            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            //System.out.println("received");

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            //System.out.println("Received");

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(running) {
            Controller.setStatus("Received");
            while (!done) {
                try {
                    //code, localip
                    Main.setInfo((String[]) objectInputStream.readObject());

                    //System.out.println("The Iliad at " + info[1] + " wants to connect with code: " + info[0] + ". Do you accept?");

                    done = true;
                    serverSocket.close();
                    objectInputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void close(){
        try {

            running = false;

            if(socket != null)
                socket.close();
            if(objectInputStream != null)
                objectInputStream.close();
            if(serverSocket != null)
                serverSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        receive();
        if(running) {
            Controller.setStatus("Moving on");
            Platform.runLater(() -> Main.received());
        }

    }
}

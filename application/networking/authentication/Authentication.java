package application.networking.authentication;

import application.YesOrNoDialog;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nicholas on 1/4/2017.
 */
public class Authentication implements Runnable{
    private ServerSocket serverSocket;
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private final int PORT = 13579;
    private boolean done = false;
    private String[] info;

    private void receive(){
        System.out.println("Waiting for authentication");
        try {

            serverSocket = new ServerSocket(PORT);
            socket = serverSocket.accept();
            //System.out.println("received");

            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!done){
            try {
                //code, localip
                info = (String[])objectInputStream.readObject();

                System.out.println("The Iliad at " + info[1] + " wants to connect with code: " + info[0] + ". Do you accept?");

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

        //new YesOrNoDialog("The Iliad at " + info[1] + " wants to connect with code: " + info[0] + ". Do you accept?");

    }

    private void send(){

    }


    @Override
    public void run() {
        receive();
        send();
    }
}

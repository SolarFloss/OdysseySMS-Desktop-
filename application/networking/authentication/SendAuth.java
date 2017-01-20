package application.networking.authentication;

import application.Main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Nicholas on 1/6/2017.
 */
public class SendAuth implements Runnable{
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private final int PORT = 24680;
    private final String IP = Main.getphoneIP();
    private boolean authenticate;

    public SendAuth(boolean authenticate){
        this.authenticate = authenticate;
    }


    @Override
    public void run() {
        try{
            socket = new Socket(IP,PORT);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());


            if(authenticate)
                dataOutputStream.writeByte(1);
            else
                dataOutputStream.writeByte(0);

            socket.close();
            dataOutputStream.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent response to Iliad at: " + IP);
        close();
    }


    private void close(){

        try {
            if(dataOutputStream != null)
                dataOutputStream.close();

            if(socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

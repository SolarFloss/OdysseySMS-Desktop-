package application.networking;

import application.Main;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicholas on 1/2/2017.
 */
public class Sending{
    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static ObjectOutputStream objectOutputStream;
    private static final int PORT = 13579;
    public static String phoneIP;
    private static String subnet;
    private static String[] info;
    private static List<String> adresses = new ArrayList<String>();



    public static void send(String message,String number) {
        try {
            phoneIP = Main.getphoneIP();
            info = new String[]{message, number};

            socket = new Socket();
            socket.connect(new InetSocketAddress(phoneIP, PORT), 5000);


            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(info);

            socket.close();
            objectOutputStream.close();
        }catch(SocketTimeoutException e){
            close();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
        close();
    }


    public static void close(){
        try {
            if (socket != null)
                socket.close();
            if(objectOutputStream != null)
                objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

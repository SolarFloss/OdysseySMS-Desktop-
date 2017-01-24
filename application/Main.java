package application;

import application.networking.authentication.Authentication;
import application.networking.Recieving;
import application.networking.Sending;
import application.networking.authentication.ReceiveAuth;
import application.networking.authentication.SendAuth;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Main extends Application {
    private static Stage stage;
    public static Scene scene;
    private static Recieving recieving;
    private static Controller controller;
    private static Sending sending;
    //private static Thread receiveThread;
    private static Authentication authentication;



    private static SendAuth send;
    private static Thread sendThread;
    private static Thread receiveThread;
    private static ReceiveAuth receiveAuth;
    private static YesOrNoDialog yesOrNoDialog;
    private static boolean authenticate;
    private static String phoneIP;


    private static String[] info = null;



    public static void setInfo(String[] i){info = i;}

    public static Stage getStage(){
        return stage;
    }

    public static Thread getReceiveThread(){
        return receiveThread;
    }

    public static String getphoneIP(){return phoneIP;}


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        scene = new Scene(root,600,40);
        scene.getStylesheets().add("/css/styles.css");


        //For testing purposes
        //MessageReceived messageReceived = new MessageReceived();
        //messageReceived.createWindow("Hello","5712653518","Ya boi");


        Platform.setImplicitExit(false);

        stage = primaryStage;
        stage.setTitle("Hello World");

        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.initStyle(StageStyle.UTILITY);
        //stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setOpacity(0);
        stage.show();








        Controller controller = new Controller();
        controller.initialize(stage);
        Controller.setStatus("Waiting at " + getLocalIP());
        //Listen
        receiveAuth = new ReceiveAuth();
        receiveThread = new Thread(receiveAuth);
        receiveThread.start();


    }


    private void trayIconClicked(){

    }

    private String getLocalIP(){
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface networkInterface : interfaces){
                List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
                for(InetAddress address : addresses){
                    if(!address.isLoopbackAddress()){
                        String sAddr = address.getHostAddress();
                        boolean isIPV4 = !sAddr.contains(":");
                        if(isIPV4){
                            return sAddr;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void received(){
        Controller.setStatus("At received");
        phoneIP = info[1];
        yesOrNoDialog = new YesOrNoDialog();
        authenticate = yesOrNoDialog.createWindow("The Iliad at " + info[1] + " wants to connect with code: " + info[0] + ". Do you accept?");




        Controller.setStatus("Sending response");

        //SendAuth
        send = new SendAuth(authenticate);
        sendThread = new Thread(send);
        sendThread.start();

        if(authenticate) {
            beginReceiving();
            Controller.setStatus("Connected to phone at: " + phoneIP);
        }

    }

    public static void beginReceiving(){
        Recieving.setRunning(true);
        recieving = new Recieving();
        receiveThread = new Thread(recieving);
        receiveThread.start();
    }





    public static void main(String[] args) {
        launch(args);
    }
}

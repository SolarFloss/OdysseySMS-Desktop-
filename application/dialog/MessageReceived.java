package application.dialog;

import application.networking.Sending;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Nicholas on 1/7/2017.
 */
public class MessageReceived {

    public ToggleButton btnClose;
    public Separator spTop;
    public Separator spBottom;
    public Text txtContact;
    public Text txtNumber;
    public TextField fldMessageSend;
    public javafx.scene.control.TextArea txtMessageReceived;


    private static Stage messageStage;
    private String message,contact;
    private Parent root;
    private Scene scene;
    private static String number;
    private static int positionBufferX = 50;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static boolean running;
    public boolean getRunning(){return running;}
    private Media sound = new Media(getClass().getResource("/sounds/notification.wav").toString());
    private static MediaPlayer mediaPlayer;


    public void editWindow(String mes,String num,String con){
        //System.out.println("Editing");

            number = num;
            message = mes;
            contact = con;


            txtNumber.setText(number);
            txtContact.setText(contact);
            txtMessageReceived.setText(message);

            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            //messageStage.setOnCloseRequest(e -> onClose());
            //messageStage.showAndWait();
        }

    public void createWindow(String mes,String num,String con){
        try {
            running = true;

            number = num;
            message = mes;
            contact = con;


            messageStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/messageReceived.fxml"));
            //messageStage.setTitle("Message Received");
            //messageStage.setResizable(false);
            messageStage.setAlwaysOnTop(true);
            scene = new Scene(root, 364,160);
            scene.getStylesheets().add("/css/styles.css");
            messageStage.setScene(scene);
            messageStage.initModality(Modality.APPLICATION_MODAL);
            messageStage.setX(screenSize.getWidth() - scene.getWidth() - 20);
            messageStage.setY(screenSize.getHeight() - scene.getHeight() - 20);
            messageStage.initStyle(StageStyle.UNDECORATED);

            btnClose = (ToggleButton)scene.lookup("#btnClose");
            txtContact = (Text) scene.lookup("#txtContact");
            txtNumber = (Text)scene.lookup("#txtNumber");
            txtMessageReceived = (TextArea) scene.lookup("#txtMessageReceived");
            fldMessageSend = (TextField)scene.lookup("#fldMessageSend");



            txtNumber.setText(number);
            txtContact.setText(contact);
            txtMessageReceived.setText(message);
            messageStage.setOnCloseRequest(e -> onClose());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            messageStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void onClose(){
        running = false;
        Platform.runLater(() -> messageStage.close());
    }





    public void fieldKeyPressed(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();
        if(key == KeyCode.ENTER){
            if(!fldMessageSend.getText().equals("")) {
                Sending.send(fldMessageSend.getText(),number);
                messageStage.setAlwaysOnTop(false);

                onClose();
            }
        }
    }

}

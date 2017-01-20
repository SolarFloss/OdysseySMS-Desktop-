package application;

import application.networking.Recieving;
import application.networking.Sending;
import application.networking.authentication.ReceiveAuth;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.Menu;
import java.awt.event.MouseAdapter;
import java.io.IOException;

public class Controller {

    public Circle circleStatus;
    public static Text txtStatus;
    private Stage stage;

    public ToggleButton btnClose;

    private SystemTray tray = SystemTray.getSystemTray();
    private Image image = Toolkit.getDefaultToolkit().getImage("/images/close.png");
    private TrayIcon trayIcon = null;
    private PopupMenu popup = new PopupMenu();
    private double deltaX;
    private double deltaY;


    public static void setStatus(String text){
        txtStatus = (Text) Main.scene.lookup("#txtStatus");
        txtStatus.setText(text);
    }



    public void initialize(Stage mainStage){
        stage = mainStage;
        if(stage != null) {
            stage.setOnCloseRequest(event -> {
                close(true,event);
            });

        }
    }

    private void close(boolean exit, Event event){
        stage = Main.getStage();
        if(!exit) {

            //Only create the tray item if it doesn't exist
            if(trayIcon == null){


                CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");


                Menu displayMenu = new Menu("Display");
                java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
                popup.add(exitItem);


                trayIcon = new TrayIcon(image,"Odyssey");
                trayIcon.setImageAutoSize(false);




                /*
                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                */

                /*
                trayIcon.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        //super.mouseClicked(e);
                        if(e.getButton() == 3){
                            //Right click
                        }else if(e.getButton() == 1){
                            //Primary button
                            Platform.runLater(() -> stage.show());
                        }

                    }
                });
                */


            }

            stage.setIconified(true);





            //stage.hide();

            /*
            Main.getStage().setIconified(true);
            event.consume();
            //Recieving.close();
            //Sending.close();
            */
        }else{
            ReceiveAuth.close();
            Recieving.close();
            Sending.close();
            Platform.exit();
        }
    }

    public void dragBoxClicked(MouseEvent mouseEvent){
        if(Main.getStage() != null) {
            deltaX = Main.getStage().getX() - mouseEvent.getSceneX();
        }
    }

    public void boxDragged(MouseEvent mouseEvent) {
        if(Main.getStage() != null) {
            Stage mStage = Main.getStage();
            double distance = mouseEvent.getScreenX() - Main.getStage().getX();
            //Platform.runLater(() -> mStage.setX(mouseEvent.getScreenX() + distance));
             //Platform.runLater(() -> Main.getStage().setY(mouseEvent.getScreenY() - Main.getStage().getHeight()/2));
        }
    }

    public void btnCloseClicked(MouseEvent mouseEvent) {
        close(false,mouseEvent);
    }

    public void stopBtnClick(ActionEvent event) {
        ReceiveAuth.close();
        Recieving.close();
        Sending.close();
        Controller.setStatus("Disconnected");
    }
}

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nicholas on 1/5/2017.
 */
public class YesOrNoDialog {


    @FXML
    Button btnYes;

    @FXML
    Button btnNo;

    @FXML
    Text txtMessage;


    private static Stage stage;
    private Parent root;
    private Scene scene;
    private static boolean success = false;



    public boolean createWindow(String message){
        try {
            Controller.setStatus("Staged up");
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/responseDialog.fxml"));
            Controller.setStatus("Rooted up");
            stage.setTitle("ATTENTION");
            stage.setResizable(false);
            Controller.setStatus("Scened up");
            scene = new Scene(root, 300,115);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            Text msg = (Text)scene.lookup("#txtMessage");
            msg.setText(message);
            stage.showAndWait();
            Controller.setStatus("?");


            return success;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }


    public void btnClick(ActionEvent event) {
        if(event.getSource().toString().contains("btnYes")){
            success = true;
            stage.close();
        }else if(event.getSource().toString().contains("btnNo")){
            success = false;
            stage.close();
        }
    }
}

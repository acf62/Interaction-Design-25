package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;

    @FXML
    private Button settingsButton;

    @FXML
    private Button dayButton;

    @FXML
    private Button locationButton;

    @FXML
    private Button weekButton;

    @FXML
    void settingsClick(ActionEvent event) {

    }

    @FXML
    void dayClick(ActionEvent event) {

    }

    @FXML
    void weekClick(ActionEvent event) {

    }

    @FXML
    void locationClick(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image1.setImage(new Image("file:resources/sunny.png"));
    }
}

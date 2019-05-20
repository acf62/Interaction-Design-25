package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class WeekController implements Initializable {

    private String selectedColor = "-fx-background-color: #9b9b9b; ";
    private String unselectedColor = "-fx-background-color: #ededed; ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationButton1.setColor(unselectedColor);
        weekButton1.setColor(selectedColor);
        dayButton1.setColor(unselectedColor);
    }

    @FXML
    private Text thursLow;

    @FXML
    private Text thursDescription;

    @FXML
    private Text satLow;

    @FXML
    private Text tue;

    @FXML
    private Text wedDescription;

    @FXML
    private Text sunLow;

    @FXML
    private Text sunHigh;

    @FXML
    private Text title;

    @FXML
    private Text mon;

    @FXML
    private Text sun;

    @FXML
    private Text tueDescription;

    @FXML
    private Text wedLow;

    @FXML
    private Text satDescription;

    @FXML
    private Text wed;

    @FXML
    private Text wedHigh;

    @FXML
    private Text fri;

    @FXML
    private Text satHigh;

    @FXML
    private Text tueLow;

    @FXML
    private Text monDescription;

    @FXML
    private Button dayButton1;

    @FXML
    private Text monLow;

    @FXML
    private Text thur;

    @FXML
    private Text sat;

    @FXML
    private Text sunDescription;

    @FXML
    private Button weekButton1;

    @FXML
    private Text friDescription;

    @FXML
    private Text tueHigh;

    @FXML
    private Text monHigh;

    @FXML
    private Text thursHigh;

    @FXML
    private Text friHigh;

    @FXML
    private Text friLow;

    @FXML
    private Button settingsButton;

    @FXML
    private Button locationButton1;

    @FXML
    void settingsClick() {
        // Load settings page
    }

    @FXML
    void dayClick() {
        // Load main controller
    }

    @FXML
    void weekClick() {
        // Nothing?
    }

    @FXML
    void locationClick() {
        // Load location page
    }

}

package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uk.ac.cam.interactiondesign25.api.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private String selectedColor = "-fx-background-color: #9b9b9b; ";
    private String unselectedColor = "-fx-background-color: #ededed; ";
    private Settings settings;

    @FXML
    private Button celsius;

    @FXML
    private Button dayButton;

    @FXML
    private Button fahrenheit;

    @FXML
    private Button locationButton;

    @FXML
    private Button bymode;

    @FXML
    private Button weekButton;

    @FXML
    private Button rgmode;

    @FXML
    void celsiusClick(ActionEvent event) {
        settings.setCelsius(true);
        celsius.setStyle(selectedColor);
        fahrenheit.setStyle(unselectedColor);
    }

    @FXML
    void fahrenheitClick(ActionEvent event) {
        settings.setCelsius(false);
        fahrenheit.setStyle(selectedColor);
        celsius.setStyle(unselectedColor);
    }

    @FXML
    void rgClick(ActionEvent event) {
        settings.setRedGreenColourblind(true);
        settings.setBlueYellowColourblind(false);
        rgmode.setStyle(selectedColor);
        bymode.setStyle(unselectedColor);
    }

    @FXML
    void byClick(ActionEvent event) {
        settings.setBlueYellowColourblind(true);
        settings.setRedGreenColourblind(false);
        rgmode.setStyle(unselectedColor);
        bymode.setStyle(selectedColor);

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
        if (settings.getCelsius()) {
            celsius.setStyle(selectedColor);
            fahrenheit.setStyle(unselectedColor);
        } else {
            fahrenheit.setStyle(selectedColor);
            celsius.setStyle(unselectedColor);
        }

        if (settings.getRedGreenColourblind()) {
            rgmode.setStyle(selectedColor);
            bymode.setStyle(unselectedColor);
        } else {
            rgmode.setStyle(unselectedColor);
            bymode.setStyle(selectedColor);
        }
    }
}

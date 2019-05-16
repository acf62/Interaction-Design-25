package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import uk.ac.cam.interactiondesign25.api.Settings;
import uk.ac.cam.interactiondesign25.api.Weather;

public class SettingsController {

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
    }

    @FXML
    void fahrenheitClick(ActionEvent event) {
        settings.setCelsius(false);
    }

    @FXML
    void rgClick(ActionEvent event) {
        settings.setBlueYellowColourblind(false);
        settings.setRedGreenColourblind(true);
    }

    @FXML
    void byClick(ActionEvent event) {
        settings.setBlueYellowColourblind(false);
        settings.setRedGreenColourblind(true);

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
}

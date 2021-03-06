package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.input.MouseEvent;
import uk.ac.cam.interactiondesign25.api.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    // Colors for selected or unselected buttons in the settings page
    private String selectedColor = "-fx-background-color: #A9A9A9; ";
    private String unselectedColor = "-fx-background-color: #ededed; ";
    public Settings settings;

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

    // Functions based on changing temperature units
    // Submits the relevant changes to settings so it is recorded and
    // changes the colours of the buttons to show which one is selected
    @FXML
    void celsiusClick() {
        settings.setCelsius(true);
        celsius.setStyle(selectedColor);
        fahrenheit.setStyle(unselectedColor);
    }

    @FXML
    void fahrenheitClick() {
        settings.setCelsius(false);
        fahrenheit.setStyle(selectedColor);
        celsius.setStyle(unselectedColor);
    }

    // Functions to change color blind modes
    // Also submits the changes to settings and changes the button colors
    @FXML
    void rgClick() {
        settings.setBlueYellowColourblind(false);
        rgmode.setStyle(selectedColor);
        bymode.setStyle(unselectedColor);
    }
    @FXML
    void byClick() {
        settings.setBlueYellowColourblind(true);
        rgmode.setStyle(unselectedColor);
        bymode.setStyle(selectedColor);
    }


    // Initialises the settings page to whatever option has previously been selected
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        settings = Main.settings;

        if (settings.getCelsius()) {
            celsius.setStyle(selectedColor);
            fahrenheit.setStyle(unselectedColor);
        } else {
            fahrenheit.setStyle(selectedColor);
            celsius.setStyle(unselectedColor);
        }

        if (!settings.getBlueYellowColourblind()) {
            rgmode.setStyle(selectedColor);
            bymode.setStyle(unselectedColor);
        } else {
            rgmode.setStyle(unselectedColor);
            bymode.setStyle(selectedColor);
        }

        weekButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("week");
            }
        });

        locationButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("location");
            }
        });

        dayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("day");
            }
        });

        celsius.setOnMouseClicked(e -> {settings.setCelsius(true);celsiusClick();});
        fahrenheit.setOnMouseClicked(e -> {settings.setCelsius(false);fahrenheitClick();});
        rgmode.setOnMouseClicked(e -> rgClick());
        bymode.setOnMouseClicked(e -> byClick());


    }
    // Sync up the settings page
    public void sync() {
        settings = Main.settings;

        if (settings.getCelsius()) {
            celsius.setStyle(selectedColor);
            fahrenheit.setStyle(unselectedColor);
        } else {
            fahrenheit.setStyle(selectedColor);
            celsius.setStyle(unselectedColor);
        }

        if (!settings.getBlueYellowColourblind()) {
            rgmode.setStyle(selectedColor);
            bymode.setStyle(unselectedColor);
        } else {
            rgmode.setStyle(unselectedColor);
            bymode.setStyle(selectedColor);
        }

    }
}

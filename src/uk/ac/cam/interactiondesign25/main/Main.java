package uk.ac.cam.interactiondesign25.main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.cam.interactiondesign25.api.Settings;
import uk.ac.cam.interactiondesign25.api.Weather;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    public static Weather weather;

    public static Stage mainStage;

    public static Scene locationScene, dayScene, settingsScene, weekScene;

    public static Settings settings;

    public static WeekController weekController;
    public static LocationController locationController;
    public static SettingsController settingsController;
    public static MainController dayController;

    // Loads up components required for the duration of execution of app
    static {
        try {
            settings = new Settings("settingsFile");
            weather = new Weather(settings);
            weather.setLocationID ( weather.getLocationIDFromName( settings.getCurrentLocation() ) );
            
            FXMLLoader fxmlLoader1,fxmlLoader2,fxmlLoader3,fxmlLoader4;
            fxmlLoader1 = new FXMLLoader(new File("resources/location_scene.fxml").toURI().toURL());
            fxmlLoader2 = new FXMLLoader(new File("resources/week_scene.fxml").toURI().toURL());
            fxmlLoader3 = new FXMLLoader(new File("resources/settings_scene.fxml").toURI().toURL());
            fxmlLoader4 = new FXMLLoader(new File("resources/main_scene.fxml").toURI().toURL());
            locationScene = new Scene(fxmlLoader1.load(),1365,965);
            locationController = fxmlLoader1.getController();

            weekScene = new Scene(fxmlLoader2.load(),1365,965);
            weekController = fxmlLoader2.getController();

            settingsScene = new Scene(fxmlLoader3.load(),1365,965);
            settingsController = fxmlLoader3.getController();

            dayScene = new Scene(fxmlLoader4.load(),1365,965);
            dayController = fxmlLoader4.getController();
        }
        catch (Exception e) {
            System.out.println("Exception in Static Initializer of main");
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(dayScene);


        primaryStage.setTitle("Weather Application");
        primaryStage.show();

        primaryStage.setMinHeight(965);
        primaryStage.setMinWidth(1365);
        primaryStage.setWidth(1365);
        primaryStage.setHeight(965);
        primaryStage.setMaxHeight(1120);
        primaryStage.setMaxWidth(1600);
        primaryStage.widthProperty().addListener((obs,oldVal,newVal) -> {
            primaryStage.setWidth((double)newVal);
        });
        primaryStage.heightProperty().addListener((obs,oldVal,newVal) -> {
            primaryStage.setHeight((double)newVal);
        });

        mainStage = primaryStage;
    }

    // Logic for switching between different scenes
    public static void receive(String instr) {
        if (instr.equals("week")) {
            mainStage.setScene(weekScene);
            weekController.sync();
        }
        else if (instr.equals("location")) {
            mainStage.setScene(locationScene);
        }
        else if (instr.equals("settings")) {
            mainStage.setScene(settingsScene);
            settingsController.sync();
        }
        else if (instr.equals("day")) {
            mainStage.setScene(dayScene);
            dayController.sync();
        }
        else {
            System.out.println("Received incorrect screen");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}

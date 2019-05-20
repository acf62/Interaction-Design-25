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

    static {
        try {
            settings = new Settings("settingsFile");
            weather = new Weather(settings);
            
            FXMLLoader fxmlLoader1,fxmlLoader2,fxmlLoader3,fxmlLoader4;
            fxmlLoader1 = new FXMLLoader(new File("resources/location_scene.fxml").toURI().toURL());
            fxmlLoader2 = new FXMLLoader(new File("resources/week_scene.fxml").toURI().toURL());
            fxmlLoader3 = new FXMLLoader(new File("resources/settings_scene.fxml").toURI().toURL());
            locationScene = new Scene(fxmlLoader1.load(),1365,768);
            locationController = fxmlLoader1.getController();

            weekScene = new Scene(fxmlLoader2.load(),1365,768);
            weekController = fxmlLoader2.getController();

            settingsScene = new Scene(fxmlLoader3.load(),1365,768);
            settingsController = fxmlLoader3.getController();
            /*System.out.println(locationController== null);
            System.out.println (weekController== null);
            System.out.println(settingsController==null);*/
        }
        catch (Exception e) {
            System.out.println("Exception in Static Initializer of main");
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // todo redo this!!!
        Parent rootLocation, rootDay , rootWeek, rootSettings;

        /*

        rootDay = fxmlLoader.load(new File("C:\\Users\\Hari\\Desktop\\Year 2\\InteractionDesign\\Interaction-Design-25\\resources\\main_scene.fxml").toURI().toURL());
        dayScene = new Scene(rootDay, 1365, 768); */

        /*rootSettings = FXMLLoader.load(new File("C:\\Users\\Hari\\Desktop\\Year 2\\InteractionDesign\\Interaction-Design-25\\resources\\GUI.fxml").toURI().toURL());
        settingsScene = new Scene(rootSettings, 1365, 768);*/ // Need to set up the Initializer to point to correct Setttings file?

        primaryStage.setScene(locationScene);


        primaryStage.setTitle("Weather Application");
        primaryStage.show();

        mainStage = primaryStage;
    }

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
        else {
            System.out.println("Received incorrect screen");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}

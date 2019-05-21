package uk.ac.cam.interactiondesign25.main;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import uk.ac.cam.interactiondesign25.api.Weather;
import uk.ac.cam.interactiondesign25.api.WeatherType;

import java.net.URL;
import java.sql.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;

public class WeekController implements Initializable {

    //The weather icons for each day
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

    // The buttons to switch between the scenes
    private Button dayButton;
    @FXML
    private Button locationButton;
    @FXML
    private ImageView settingsButton;

    //High and low temperatures for each day
    @FXML
    private Text hi1;
    @FXML
    private Text lo1;
    @FXML
    private Text hi2;
    @FXML
    private Text lo2;
    @FXML
    private Text hi3;
    @FXML
    private Text lo3;
    @FXML
    private Text hi4;
    @FXML
    private Text lo4;
    @FXML
    private Text hi5;
    @FXML
    private Text lo5;

    //Descriptions of the weather for each day
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;
    @FXML
    private Text text4;
    @FXML
    private Text text5;
    @FXML
    private Text topText;

    // Fields for the Day header
    @FXML
    private Text dayName1;
    @FXML
    private Text dayName2;
    @FXML
    private Text dayName3;
    @FXML
    private Text dayName4;
    @FXML
    private Text dayName5;


    // Helper functions to load appropriate images based on Color Blind Mode choice
    // For each WeatherType (see the enum in backend), it selects appropriate image (This includes factoring in the Color Blind Modes)

    public String[] getImageLocationsFrom(WeatherType[] weatherTypes) {
        String[] results = new String[weatherTypes.length];
        int i=0;
        for (WeatherType weatherType : weatherTypes) {
            switch(weatherType) {
                case RAINY:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/rainCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/rain.png"));
                    }
                    break;
                case PARTIALLY_CLOUDY:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/partly_cloudyCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/partly_cloudy.png"));
                    }
                    break;
                case UNKNOWN:
                    results[i] = String.valueOf(MainController.class.getResource("/unknown.png"));
                    break;
                case SNOWY:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/snowCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/snow.png"));
                    }
                    break;
                case THUNDER:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/thunderstormCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/thunderstorm.png"));
                    }
                    break;
                case SUNNY:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/sunnyCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/sunny.png"));
                    }
                    break;
                case CLOUDY:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/cloudyCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/cloudy.png"));
                    }
                    break;
                case CLEAR_NIGHT:
                    if (Main.settings.getBlueYellowColourblind()) {
                        results[i] = String.valueOf(MainController.class.getResource("/clear_nightCB.png"));
                    }
                    else {
                        results[i] = String.valueOf(MainController.class.getResource("/clear_night.png"));
                    }
                    break;
            }
            i++;
        }
        return results;
    }

    // Initialize page
    // This involves :
    // 1. Setting the images, text , temperatures to the corresponding components on the screen on initializing
    // 2. Adding listeners to buttons to listen to Events
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        topText.setText("This Week in " + Main.settings.getCurrentLocation());

        int[][] temps = Main.weather.getWeekTemperatures();
        String[] description = Main.weather.getWeekWeatherDescription();
        WeatherType[] weatherTypes = Main.weather.getWeekWeatherTypes();
        String[] images = getImageLocationsFrom(weatherTypes);

        String[] dayNames = getDayNames();
        dayName1.setText(dayNames[0]);
        dayName2.setText(dayNames[1]);
        dayName3.setText(dayNames[2]);
        dayName4.setText(dayNames[3]);
        dayName5.setText(dayNames[4]);


        String suffix = Main.settings.getCelsius() ? "°C" : "°F";

        // Setting the text description
        text1.setText(description[0]);
        text2.setText(description[1]);
        text3.setText(description[2]);
        text4.setText(description[3]);
        text5.setText(description[4]);

        // Setting the Hi temperatures for each day
        hi1.setText(String.valueOf(temps[0][0])+suffix);
        hi2.setText(String.valueOf(temps[1][0])+suffix);
        hi3.setText(String.valueOf(temps[2][0])+suffix);
        hi4.setText(String.valueOf(temps[3][0])+suffix);
        hi5.setText(String.valueOf(temps[4][0])+suffix);

        // Setting the Low temperatures for each day
        lo1.setText(String.valueOf(temps[0][1])+suffix);
        lo2.setText(String.valueOf(temps[1][1])+suffix);
        lo3.setText(String.valueOf(temps[2][1])+suffix);
        lo4.setText(String.valueOf(temps[3][1])+suffix);
        lo5.setText(String.valueOf(temps[4][1])+suffix);

        settingsButton.setImage(new Image(String.valueOf(WeekController.class.getResource("/settings-512.png"))));
        // Set the images accordingly
        image1.setImage(new Image(images[0]));
        image2.setImage(new Image(images[1]));
        image3.setImage(new Image(images[2]));
        image4.setImage(new Image(images[3]));
        image5.setImage(new Image(images[4]));

        // Adding interactivity

        locationButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("location");
            }
        });

        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("settings");
            }
        });

        dayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("day");
            }
        });
    }

    // Sync up the page
    // TO make sure that changing location or settings change the data displayed for the Week Screen
    public void sync() {
        int[][] temps = Main.weather.getWeekTemperatures();
        String[] description = Main.weather.getWeekWeatherDescription();
        WeatherType[] weatherTypes = Main.weather.getWeekWeatherTypes();
        String[] images = getImageLocationsFrom(weatherTypes);

        topText.setText("This Week in " + Main.settings.getCurrentLocation());

        String suffix = Main.settings.getCelsius() ? "°C" : "°F";

        String[] dayNames = getDayNames();
        dayName1.setText(dayNames[0]);
        dayName2.setText(dayNames[1]);
        dayName3.setText(dayNames[2]);
        dayName4.setText(dayNames[3]);
        dayName5.setText(dayNames[4]);

        for (int i =0 ; i <description.length ; i++) {
            if (description[i].trim().contains(" ")) {
                String[] texts = description[i].split(" ");
                String temp = "";
                for (String h : texts) {
                    temp = temp + h + "\n";
                }
                description[i] = temp;
            }

        }

        // Setting the text description
        text1.setText(description[0]);
        text2.setText(description[1]);
        text3.setText(description[2]);
        text4.setText(description[3]);
        text5.setText(description[4]);

        // Setting the Hi temperatures for each day
        hi1.setText(String.valueOf(temps[0][0])+suffix);
        hi2.setText(String.valueOf(temps[1][0])+suffix);
        hi3.setText(String.valueOf(temps[2][0])+suffix);
        hi4.setText(String.valueOf(temps[3][0])+suffix);
        hi5.setText(String.valueOf(temps[4][0])+suffix);

        // Setting the Low temperatures for each day
        lo1.setText(String.valueOf(temps[0][1])+suffix);
        lo2.setText(String.valueOf(temps[1][1])+suffix);
        lo3.setText(String.valueOf(temps[2][1])+suffix);
        lo4.setText(String.valueOf(temps[3][1])+suffix);
        lo5.setText(String.valueOf(temps[4][1])+suffix);

        settingsButton.setImage(new Image(String.valueOf(WeekController.class.getResource("/settings-512.png"))));

        // Adding the images
        image1.setImage(new Image(images[0]));
        image2.setImage(new Image(images[1]));
        image3.setImage(new Image(images[2]));
        image4.setImage(new Image(images[3]));
        image5.setImage(new Image(images[4]));

        locationButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("location");
            }
        });
    }

    // Get the day labels to be displayed alongside each Day
    private static String[] getDayNames() {
        String[] days = new String[]{"Mon", "Tues", "Weds", "Thur", "Fri", "Sat", "Sun"};

        String[] toReturn = new String[5];
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for(int i=0; i<5; i++){
            toReturn[i] = days[(day+i-1)%7];
        }

        return toReturn;
    }
}

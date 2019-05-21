package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import uk.ac.cam.interactiondesign25.api.Weather;
import uk.ac.cam.interactiondesign25.api.WeatherType;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;



public class MainController implements Initializable {

    @FXML
    private ImageView imagePeriod1;

    @FXML
    private ImageView imagePeriod2;

    @FXML
    private ImageView imagePeriod3;

    @FXML
    private Text tempPeriod5;

    @FXML
    private Button dayButton;

    @FXML
    private ImageView imagePeriod4;

    @FXML
    private Button locationButton;

    @FXML
    private ImageView imagePeriod5;

    @FXML
    private Text hiDay;

    @FXML
    private ImageView mainImage;

    @FXML
    private Text textDay;

    @FXML
    private ImageView settingsButton;

    @FXML
    private Text tempPeriod2;

    @FXML
    private Text timePeriod4;

    @FXML
    private Text tempPeriod1;

    @FXML
    private Text timePeriod5;

    @FXML
    private Text timePeriod2;

    @FXML
    private Text tempPeriod4;

    @FXML
    private Text timePeriod3;

    @FXML
    private Text tempPeriod3;

    @FXML
    private Text loDay;

    @FXML
    private Text timePeriod1;

    @FXML
    private Button weekButton;

    @FXML
    private Text topText;

    // Initialize Main controller with location page
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topText.setText("Today in " + Main.settings.getCurrentLocation());
        settingsButton.setImage(new Image(String.valueOf(MainController.class.getResource("/settings-512.png"))));

        // Adding Interactivity to buttons
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
        sync();
        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("settings");
            }
        });
    }

    // Helper function to load appropriate images based on Color Blind Modes

    public Image getImage(WeatherType weatherType) {
        if (!Main.settings.getBlueYellowColourblind()) {
            switch (weatherType) {
                case RAINY:
                    return new Image(String.valueOf(MainController.class.getResource("/rain.png")));
                case UNKNOWN:
                    return new Image(String.valueOf(MainController.class.getResource("/unknown.png")));
                case SUNNY:
                    return new Image(String.valueOf(MainController.class.getResource("/sunny.png")));
                case SNOWY:
                    return new Image(String.valueOf(MainController.class.getResource("/snow.png")));
                case PARTIALLY_CLOUDY:
                    return new Image(String.valueOf(MainController.class.getResource("/partly_cloudy.png")));
                case CLOUDY:
                    return new Image(String.valueOf(MainController.class.getResource("/cloudy.png")));
                case THUNDER:
                    return new Image(String.valueOf(MainController.class.getResource("/thunderstorm.png")));
                case CLEAR_NIGHT:
                    return new Image(String.valueOf(MainController.class.getResource("/clear_night.png")));
            }
        }
        else {
            switch (weatherType) {
                case RAINY:
                    return new Image(String.valueOf(MainController.class.getResource("/rainCB.png")));
                case UNKNOWN:
                    return new Image(String.valueOf(MainController.class.getResource("/unknown.png")));
                case SUNNY:
                    return new Image(String.valueOf(MainController.class.getResource("/sunnyCB.png")));
                case SNOWY:
                    return new Image(String.valueOf(MainController.class.getResource("/snowCB.png")));
                case PARTIALLY_CLOUDY:
                    return new Image(String.valueOf(MainController.class.getResource("/partly_cloudyCB.png")));
                case CLOUDY:
                    return new Image(String.valueOf(MainController.class.getResource("/cloudyCB.png")));
                case THUNDER:
                    return new Image(String.valueOf(MainController.class.getResource("/thunderstormCB.png")));
                case CLEAR_NIGHT:
                    return new Image(String.valueOf(MainController.class.getResource("/clear_nightCB.png")));
            }
        }
        return new Image(String.valueOf(MainController.class.getResource("/unknown.png")));
    }

    // Sync function for changes when page is reloaded

    public void sync() {
        Weather weather = Main.weather;
        mainImage.setImage(getImage(weather.getTodayWeatherType()));
        topText.setText("Today in " + Main.settings.getCurrentLocation());

        int[] hilo = weather.getTodayTemperatures();

        String suffix = Main.settings.getCelsius() ? "°C" : "°F";

        // Set text for High,Low temperatures and the description of text
        hiDay.setText(String.valueOf(hilo[0])+suffix);
        loDay.setText(String.valueOf(hilo[1])+suffix);
        textDay.setText(weather.getTodayWeatherDescription() + "\n" + "Wind Speed " + weather.getTodayWindSpeed() + " mph");

        // Set time segments for each of the 3-hour segments
        String[] fiveTimes = weather.getTodayThreeHourTimes();
        timePeriod1.setText(fiveTimes[0]);
        timePeriod2.setText(fiveTimes[1]);
        timePeriod3.setText(fiveTimes[2]);
        timePeriod4.setText(fiveTimes[3]);
        timePeriod5.setText(fiveTimes[4]);

        // Set temperatures for each of the 3-hour segments
        int[] fiveTemps = weather.getTodayThreeHourlyTemperatures();
        tempPeriod1.setText(String.valueOf(fiveTemps[0])+suffix);
        tempPeriod2.setText(String.valueOf(fiveTemps[1])+suffix);
        tempPeriod3.setText(String.valueOf(fiveTemps[2])+suffix);
        tempPeriod4.setText(String.valueOf(fiveTemps[3])+suffix);
        tempPeriod5.setText(String.valueOf(fiveTemps[4])+suffix);

        // Set images for each of the 3-hour segments
        WeatherType[] weatherType = weather.getTodayThreeHourlyWeatherTypes();
        imagePeriod1.setImage(getImage(weatherType[0]));
        imagePeriod2.setImage(getImage(weatherType[1]));
        imagePeriod3.setImage(getImage(weatherType[2]));
        imagePeriod4.setImage(getImage(weatherType[3]));
        imagePeriod5.setImage(getImage(weatherType[4]));

    }
}

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
    void dayClick(ActionEvent event) {

    }

    @FXML
    void weekClick(ActionEvent event) {

    }

    @FXML
    void locationClick(ActionEvent event) {

    }

    /*private void setTriple(int number, int h, int l, String text){
        String high = String.valueOf(h);
        String low = String.valueOf(l);
        switch (number){
            case 1:
                high1.setText(high);
                low1.setText(low);
                text1.setText(text);
                break;
            case 2:
                high2.setText(high);
                low2.setText(low);
                text2.setText(text);
                break;
            case 3:
                high3.setText(high);
                low3.setText(low);
                text3.setText(text);
                break;
            case 4:
                high4.setText(high);
                low4.setText(low);
                text4.setText(text);
                break;
            case 5:
                high5.setText(high);
                low5.setText(low);
                text5.setText(text);
                break;
        }
    }*/


    /*private void setImage(int number, WeatherType type) {
        switch (number) {
            case 1:
                image1.setImage(typeToImage(type));
                break;
            case 2:
                image2.setImage(typeToImage(type));
                break;
            case 3:
                image3.setImage(typeToImage(type));
                break;
            case 4:
                image4.setImage(typeToImage(type));
                break;
            case 5:
                image5.setImage(typeToImage(type));
                break;
        }
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsButton.setImage(new Image("file:resources/settings-512.png"));
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

    public String getImageString(WeatherType weatherType) {
        if (!Main.settings.getBlueYellowColourblind()) {
            switch (weatherType) {
                case RAINY:
                    return "file:resources/rain.png";
                case UNKNOWN:
                    return "file:resources/unknown.png";
                case SUNNY:
                    return "file:resources/sunny.png";
                case SNOWY:
                    return "file:resources/snow.png";
                case PARTIALLY_CLOUDY:
                    return "file:resources/partly_cloudy.png";
                case CLOUDY:
                    return "file:resources/cloudy.png";
                case THUNDER:
                    return "file:resources/thunderstorm.png";
                case CLEAR_NIGHT:
                    return "file:resources/clear_night.png";
            }
        }
        else {
            switch (weatherType) {
                case RAINY:
                    return "file:resources/rainCB.png";
                case UNKNOWN:
                    return "file:resources/unknown.png";
                case SUNNY:
                    return "file:resources/sunnyCB.png";
                case SNOWY:
                    return "file:resources/snowCB.png";
                case PARTIALLY_CLOUDY:
                    return "file:resources/partly_cloudyCB.png";
                case CLOUDY:
                    return "file:resources/cloudyCB.png";
                case THUNDER:
                    return "file:resources/thunderstormCB.png";
                case CLEAR_NIGHT:
                    return "file:resources/clear_nightCB.png";
            }
        }
        return "file:resources/unknown.png";
    }

    public void sync() {
        Weather weather = Main.weather;
        mainImage.setImage(new Image(getImageString(weather.getTodayWeatherType())));

        int[] hilo = weather.getTodayTemperatures();

        String suffix = Main.settings.getCelsius() ? "°C" : "°F";

        hiDay.setText(String.valueOf(hilo[0])+suffix);
        loDay.setText(String.valueOf(hilo[1])+suffix);
        textDay.setText(weather.getTodayWeatherDescription() + "\n" + "Wind Speed " + weather.getTodayWindSpeed() + " mph");

        String[] fiveTimes = weather.getTodayThreeHourTimes();
        timePeriod1.setText(fiveTimes[0]);
        timePeriod2.setText(fiveTimes[1]);
        timePeriod3.setText(fiveTimes[2]);
        timePeriod4.setText(fiveTimes[3]);
        timePeriod5.setText(fiveTimes[4]);

        int[] fiveTemps = weather.getTodayThreeHourlyTemperatures();
        tempPeriod1.setText(String.valueOf(fiveTemps[0])+suffix);
        tempPeriod2.setText(String.valueOf(fiveTemps[1])+suffix);
        tempPeriod3.setText(String.valueOf(fiveTemps[2])+suffix);
        tempPeriod4.setText(String.valueOf(fiveTemps[3])+suffix);
        tempPeriod5.setText(String.valueOf(fiveTemps[4])+suffix);

        WeatherType[] weatherType = weather.getTodayThreeHourlyWeatherTypes();
        imagePeriod1.setImage(new Image(getImageString(weatherType[0])));
        imagePeriod2.setImage(new Image(getImageString(weatherType[1])));
        imagePeriod3.setImage(new Image(getImageString(weatherType[2])));
        imagePeriod4.setImage(new Image(getImageString(weatherType[3])));
        imagePeriod5.setImage(new Image(getImageString(weatherType[4])));

    }
}

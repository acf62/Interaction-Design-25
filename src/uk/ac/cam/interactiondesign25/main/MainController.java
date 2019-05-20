package uk.ac.cam.interactiondesign25.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Text high1;
    @FXML
    private Text low1;
    @FXML
    private Text text1;
    @FXML
    private Text high2;
    @FXML
    private Text low2;
    @FXML
    private Text text2;
    @FXML
    private Text high3;
    @FXML
    private Text low3;
    @FXML
    private Text text3;
    @FXML
    private Text high4;
    @FXML
    private Text low4;
    @FXML
    private Text text4;
    @FXML
    private Text high5;
    @FXML
    private Text low5;
    @FXML
    private Text text5;

    @FXML
    void settingsClick() {
        Main.receive("settings");
    }

    @FXML
    void dayClick() {
        Main.receive("day");
    }

    @FXML
    void weekClick() {
        Main.receive("week");
    }

    @FXML
    void locationClick() {
        Main.receive("location");
    }


    private void setTriple(int number, int h, int l, String text){
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
    }

    private Image typeToImage(WeatherType type) {
        switch (type) {
            case RAINY: return new Image("file:resources/rainy.png");
            case SUNNY: return new Image("file:resources/sunny.png");
            case CLOUDY: return new Image("file:resources/cloudy.png");
            case PARTIALLY_CLOUDY: return new Image("file:resources/partly_cloudy.png");
            case SNOWY: return new Image("file:resources/snowy.png");
            case THUNDER: return new Image("file:resources/thunderstorm.png");
            default: return null;
        }
    }

    private void setImage(int number, WeatherType type) {
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Weather weather = Main.weather;
        weather.setLocationID(weather.getLocationIDFromName("Cambridge"));
        setTriple(1, weather.getTodayTemperatures()[0],
                weather.getTodayTemperatures()[1], weather.getTodayWeatherDescription());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        for (int i = 2; i <= 5 ; i++){
            calendar.add(Calendar.HOUR, 3);
            setTriple(i, weather.getTodayThreeHourlyTemperatures()[i-2], weather.getTodayTemperatures()[i-2], format.format(calendar.getTime()));
        }
        setImage(0, weather.getTodayWeatherType());
        setImage(1, weather.getTodayWeatherType());
        setImage(2, weather.getTodayWeatherType());
        setImage(3, weather.getTodayWeatherType());
        setImage(4, weather.getTodayWeatherType());
    }
}

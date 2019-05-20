package uk.ac.cam.interactiondesign25.main;

import javafx.event.EventHandler;
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
import java.util.ResourceBundle;

public class WeekController implements Initializable {

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
    private Button dayButton;
    @FXML
    private Button locationButton;

    @FXML
    private ImageView settingsButton;


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

    public String[] getImageLocationsFrom(WeatherType[] weatherTypes) {
        String[] results = new String[weatherTypes.length];
        int i=0;
        for (WeatherType weatherType : weatherTypes) {
            switch(weatherType) {
                case RAINY:
                    results[i] = "file:resources/rain.png";
                    break;
                case PARTIALLY_CLOUDY:
                    results[i] = "file:resources/partly_cloudy.png";
                    break;
                case UNKNOWN:
                    results[i] = "file:resources/unknown.png";
                    break;
                case SNOWY:
                    results[i] = "file:resources/snow.png";
                    break;
                case THUNDER:
                    results[i] = "file:resources/thunderstorm.png";
                case SUNNY:
                    results[i] = "file:resources/sunny.png";
                    break;
                case CLOUDY:
                    results[i] = "file:resources/cloudy.png";
                    break;
            }
            i++;
        }
        for (String image : results) {
            System.out.print(image);
        }
        System.out.println();
        return results;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int[][] temps = Main.weather.getWeekTemperatures();
        String[] description = Main.weather.getWeekWeatherDescription();
        WeatherType[] weatherTypes = Main.weather.getWeekWeatherTypes();
        String[] images = getImageLocationsFrom(weatherTypes);

        text1.setText(description[0]);
        text2.setText(description[1]);
        text3.setText(description[2]);
        text4.setText(description[3]);
        text5.setText(description[4]);


        hi1.setText(String.valueOf(temps[0][0]));
        hi2.setText(String.valueOf(temps[1][0]));
        hi3.setText(String.valueOf(temps[2][0]));
        hi4.setText(String.valueOf(temps[3][0]));
        hi5.setText(String.valueOf(temps[4][0]));

        lo1.setText(String.valueOf(temps[0][1]));
        lo2.setText(String.valueOf(temps[1][1]));
        lo3.setText(String.valueOf(temps[2][1]));
        lo4.setText(String.valueOf(temps[3][1]));
        lo5.setText(String.valueOf(temps[4][1]));



        settingsButton.setImage(new Image("file:resources/settings-512.png"));
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

    public void sync() {
        int[][] temps = Main.weather.getWeekTemperatures();
        String[] description = Main.weather.getWeekWeatherDescription();
        WeatherType[] weatherTypes = Main.weather.getWeekWeatherTypes();
        String[] images = getImageLocationsFrom(weatherTypes);

        for (int i =0 ; i < description.length; i++) {
            if (description[i].strip().contains(" ")) {
                String[] texts = description[i].split(" ");
                String temp = "";
                for (String h : texts) {
                    temp = temp + h + "\n";
                }
                description[i] = temp.stripTrailing();
            }
        }

        text1.setText(description[0]);
        text2.setText(description[1]);
        text3.setText(description[2]);
        text4.setText(description[3]);
        text5.setText(description[4]);

        hi1.setText(String.valueOf(temps[0][0]));
        hi2.setText(String.valueOf(temps[1][0]));
        hi3.setText(String.valueOf(temps[2][0]));
        hi4.setText(String.valueOf(temps[3][0]));
        hi5.setText(String.valueOf(temps[4][0]));

        lo1.setText(String.valueOf(temps[0][1]));
        lo2.setText(String.valueOf(temps[1][1]));
        lo3.setText(String.valueOf(temps[2][1]));
        lo4.setText(String.valueOf(temps[3][1]));
        lo5.setText(String.valueOf(temps[4][1]));



        settingsButton.setImage(new Image("file:resources/settings-512.png"));
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
}

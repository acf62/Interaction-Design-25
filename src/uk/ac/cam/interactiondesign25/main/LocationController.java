package uk.ac.cam.interactiondesign25.main;

import com.sun.javafx.css.StyleCache;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uk.ac.cam.interactiondesign25.api.Sitelist;
import uk.ac.cam.interactiondesign25.api.Weather;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class LocationController implements Initializable  {

    private String[] recents = {"","","","","","","","",""};

    public void addToRecents(String location) {
        int i = 0;
        for (String recent : recents) {
            if (!recent.isEmpty()) {
                i++;
            }
            else {
                break;
            }
        }
        if (i==recents.length) {
            for (int j =4; j>=1; j--){
                recents[j] = recents[j-1];
            }
            recents[0]=location;
        }
        else {
            for (int j =i; j>=1; j--){
                recents[j]=recents[j-1];
            }
            recents[0]=location;
        }

        recentList.getItems().removeAll();
        LinkedList<String> temp = new LinkedList<>();
        for (String string : recents) {
            if (!string.isEmpty() && string!=null) {
                temp.add(string);
            }
        }
        recentList.setFixedCellSize(60);
        recentList.getItems().setAll(temp);
    }

    @FXML
    private ListView<String> recentList;

    @FXML
    private ListView<String> listSearch;

    @FXML
    private VBox mainVBox;

    @FXML
    private ImageView settingsButton;

    @FXML
    private ImageView searchIcon;

    @FXML
    private Button DayButton;

    @FXML
    private TextArea searchBar;

    @FXML
    private Button WeekButton;

    @FXML
    void openSettings(ActionEvent event) {

    }

    @FXML
    void keyPressFromSearchBar(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            listSearch.setFixedCellSize(75);
            listSearch.getItems().removeAll(listSearch.getItems());
            for (String site : Main.weather.autocomplete(searchBar.getText().strip(),5)) {
                listSearch.getItems().add(site);
            }
        }


    }

    @FXML
    void openDayTab(ActionEvent event) {
        // need to redirect to the other tab

    }

    @FXML
    void openWeekTab(ActionEvent event) {
        // need to redirect to weej tab

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsButton.setImage(new Image("file:resources/settings-512.png"));
        searchIcon.setImage(new Image("file:resources/search.png"));

        listSearch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                boolean found = false;
                if (newValue != null  && !newValue.isEmpty()) {
                    for (String string:recents) {
                        if (string.equals(newValue)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        addToRecents(newValue);
                    }
                    Main.weather.setLocationID(Main.weather.getLocationIDFromName(newValue));
                }
            }
        });

        recentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null  && !t1.isEmpty()) {
                    System.out.println(s+ " " + t1);
                    Main.weather.setLocationID(Main.weather.getLocationIDFromName(t1));
                }

            }
        });

        WeekButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("week");
            }
        });

        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.receive("settings");
            }
        });
    }
}

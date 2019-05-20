package uk.ac.cam.interactiondesign25.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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

            listSearch.setFixedCellSize(75);
            listSearch.getItems().removeAll(listSearch.getItems());
            List<String> stringList = Main.weather.autocomplete(searchBar.getText().trim(),5);
            for (String site : stringList) {
                listSearch.getItems().add(site);
            }

            if (stringList.size()==0 && searchBar.getText().length()!=0) {
                listSearch.getItems().add("No Locations Found");
            }

            if (event.getCode().equals(KeyCode.ENTER)) {
                searchBar.setText(searchBar.getText().trim());
                searchBar.positionCaret(searchBar.getText().length());
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

        listSearch.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                final ListCell<String> cell = new ListCell<String>() {
                    @Override
                    public void updateItem(String item,
                                           boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setFont(this.getFont().font(this.getFont().getName(), 30.0)); //set your desired size
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        recentList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                final ListCell<String> cell = new ListCell<String>() {
                    @Override
                    public void updateItem(String item,
                                           boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setFont(this.getFont().font(this.getFont().getName(), 30.0)); //set your desired size
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
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
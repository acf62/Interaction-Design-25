package uk.ac.cam.interactiondesign25.api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings {
    private String filename;
    private boolean Celsius;
    private boolean BlueYellowColourblind;
    private String currentLocationName;
    private List<String> recentLocations = new ArrayList<>();

    public Settings(String fn){
        filename = fn;
        try (FileReader f = new FileReader(filename)){
            // Load file contents into variables
            BufferedReader r = new BufferedReader(f);
            Celsius = r.readLine().equals("1");
            BlueYellowColourblind = r.readLine().equals("1");
            currentLocationName = r.readLine();
            String recent = null;
            while ( (recent = r.readLine()) != null ) {
                recentLocations.add(recent);
            }

        } catch (FileNotFoundException e1) {
            // Initialise file - didn't exist
            Celsius = true;
            BlueYellowColourblind = false;
            currentLocationName = "Cambridge";
            writeBack();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes over the file with the current boolean values
    private void writeBack(){
        try (FileWriter f = new FileWriter(filename)) {
            BufferedWriter w = new BufferedWriter(f);
            w.write(((Celsius)?"1":"0")+"\n");
            w.write(((BlueYellowColourblind)?"1":"0")+"\n");
            w.write(currentLocationName+"\n");
            for ( String s : recentLocations) {
                w.write ( s + "\n" );
            }
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCelsius(boolean val) {
        Celsius = val;
        writeBack();
    }

    public void setBlueYellowColourblind(boolean val){
        BlueYellowColourblind = val;
        writeBack();
    }

    public void setCurrentLocation ( String name ) {
        currentLocationName = name;
        writeBack();
    }

    public void setRecentLocations(List<String> l ) {
        recentLocations = l;
        writeBack();
    }

    public boolean getCelsius() {
        return Celsius;
    }

    public boolean getBlueYellowColourblind() {
        return BlueYellowColourblind;
    }

    public String getCurrentLocation () {
        return currentLocationName;
    }

    public List<String> getRecentLocations() {
        return recentLocations;
    }
}

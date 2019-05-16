package uk.ac.cam.interactiondesign25.api;

import java.io.*;

public class Settings {
    private String filename;
    private boolean Celcius;
    private boolean BlueYellowColourblind;
    private boolean RedGreenColourblind;

    public Settings(String fn){
        filename = fn;
        try (FileReader f = new FileReader(filename)){
            // Load file contents into variables
            BufferedReader r = new BufferedReader(f);
            Celcius = r.readLine().equals("1");
            BlueYellowColourblind = r.readLine().equals("1");
            RedGreenColourblind = r.readLine().equals("1");

        } catch (FileNotFoundException e1) {
            // Initialise file - didn't exist
            Celcius = true;
            BlueYellowColourblind = false;
            RedGreenColourblind = false;
            writeBack();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes over the file with the current boolean values
    private void writeBack(){
        try (FileWriter f = new FileWriter(filename)) {
            BufferedWriter w = new BufferedWriter(f);
            w.write(((Celcius)?"1":"0")+"\n");
            w.write(((BlueYellowColourblind)?"1":"0")+"\n");
            w.write(((RedGreenColourblind)?"1":"0")+"\n");
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCelcius(boolean val){
        Celcius = val;
        writeBack();
    }

    public void setBlueYellowColourblind(boolean val){
        BlueYellowColourblind = val;
        writeBack();
    }

    public void setRedGreenColourblind(boolean val){
        RedGreenColourblind = val;
        writeBack();
    }

    public boolean getCelcius(){
        return Celcius;
    }

    public boolean getBlueYellowColourblind(){
        return BlueYellowColourblind;
    }

    public boolean getRedGreenColourblind(){
        return RedGreenColourblind;
    }
}

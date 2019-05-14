public class Settings {
    private boolean Celcius;
    private boolean BlueYellowColourblind;
    private boolean RedGreenColourblind;

    public Settings(String filename){
        // TODO
        // if filename doesn't exist, create it, write default data to it
        // otherwise, load it into variables
    }

    public void setCelcius(boolean val){
        Celcius = val;
        // TODO Update in file also
    }

    public void setBlueYellowColourblind(boolean val){
        BlueYellowColourblind = val;
        // TODO Update in file also
    }

    public void setRedGreenColourblind(boolean val){
        RedGreenColourblind = val;
        // TODO Update in file also
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

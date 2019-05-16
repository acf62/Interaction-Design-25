package uk.ac.cam.interactiondesign25.api;

public class Weather {
    private Settings settings;
    private int locationID;

    public Weather(String settingsFilename){
        settings = new Settings(settingsFilename);
        locationID = 310042; // default to cambridge
    }

    // Returns today's maximum and minimum temperatures
    public int[] getTodayTemperatures(){
        return new int[]{0,0};
    }

    // Returns today's 3-hourly temperature forecasts
    public int[] getTodayThreeHourlyTemperatures(){
        return new int[]{0,0,0,0,0};
    }

    // Returns a textual description of today's weather
    public String getTodayWeatherDescription(){
        return "Cloudy and mildly depressing";
    }

    // Returns type of today's weather (for use in selecting icons)
    public WeatherType getTodayWeatherType(){
        return WeatherType.UNKNOWN;
    }

    // Returns high and low temperatures for each day for the next week,
    // starting with today
    public int[][] getWeekTemperatures(){
        return new int[][]{{0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
    }

    // Returns textual descriptions of the weather each day this week,
    // starting with today
    public String[] getWeekWeatherDescription(){
        return new String[]{"", "", "", "", ""};
    }

    // Returns types of the weather each day this week, starting with today
    public WeatherType[] getWeekWeatherType(){
        return new WeatherType[]{
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
        };
    }

    // Calls the API and caches the results locally unless already have data
    private void doAPICallIfNecessary(){
        if (!haveData()){
            // doAPICall
        }
    }

    // Returns true if we already have up-to-date data for the location
    private boolean haveData(){
        return true;
    }

    // Checks user settings for whether they use Celcius or Fahrenheit
    private boolean useCelcius(){
        return settings.getCelcius();
    }
}

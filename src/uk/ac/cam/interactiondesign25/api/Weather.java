package uk.ac.cam.interactiondesign25.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import static uk.ac.cam.interactiondesign25.api.TemperatureConversion.toFahrenheit;

public class Weather {
    private Settings settings;
    private int locationID;
	
	private final String baseUrl = "http://datapoint.metoffice.gov.uk/public/data/";
	private final String apiKey = "5887b42a-ab8e-4285-94e8-9ef8ca4fe411";
	private final boolean active = false; //Used during development to reduce number of API calls

	//JSON data
	private String WeeklyForecast;
	private String ThreeHourlyForecast;
	
	private long lastUpdateTime = 0;
	
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
        int[] result = {0,0,0,0,0};
		if ( active ) {
			/*JSONObject weatherObject = new JSONObject ( ThreeHourlyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			for ( int i = 0; i < Period.length(); ++i ) {
				JSONObject j = Period.getJSONObject(i);
				JSONArray a = j.getJSONArray("Rep");
				JSONObject day = a.getJSONObject(0);
				result[i] = day.getInt("Dm");
			}*/
		}
		return result;
    }

    // Returns a textual description of today's weather
    public String getTodayWeatherDescription(){
        return "Cloudy and mildly depressing";
    }

    // Returns type of today's weather (for use in selecting icons)
    public WeatherType getTodayWeatherType(){
		if ( active ) {
			JSONObject weatherObject = new JSONObject ( WeeklyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			JSONObject j = Period.getJSONObject(0);
			JSONArray a = j.getJSONArray("Rep");
			JSONObject day = a.getJSONObject(0);
			int weatherCode = day.getInt("W");
			return WeatherType.convert(weatherCode);
		} else {
			return WeatherType.UNKNOWN;
		}
    }

    // Returns high and low temperatures for each day for the next week,
    // starting with today
    public int[][] getWeekTemperatures(){
		int[][] result = {{0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
		if ( active ) {
			JSONObject weatherObject = new JSONObject ( WeeklyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			for ( int i = 0; i < Period.length() && i < 5; ++i ) {
				JSONObject j = Period.getJSONObject(i);
				JSONArray a = j.getJSONArray("Rep");
				JSONObject day = a.getJSONObject(0);
				JSONObject night = a.getJSONObject(1);
				result[i][0] = day.getInt("Dm");
				result[i][1] = night.getInt("Nm");
			}
			
			if ( !useCelcius() ) {
				for ( int i = 0; i < Period.length() && i < 5; ++i ) {
					result[i][0] = toFahrenheit(result[i][0]);
					result[i][1] = toFahrenheit(result[i][1]);
				}
			}
		}
		return result;
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
            downloadWeeklyForecast();
			downloadThreeHourlyForecast();
        }
    }

    // Returns true if we already have up-to-date data for the location
    private boolean haveData(){
		
		if ( System.currentTimeMillis() - lastUpdateTime >= 60000 ) {
			downloadWeeklyForecast();
			downloadThreeHourlyForecast();
		}
        return true;
    }
	
	private void downloadWeeklyForecast() {
		String result = "";
		try {
			URL url = new URL(baseUrl + "val/wxfcs/all/json/" + locationID + "?res=daily&key=" + apiKey );
			//url = new URL(baseUrl + "val/wxfcs/all/json/sitelist" + "?key=" + apiKey );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader ( new InputStreamReader ( conn.getInputStream() ) );
			String line;
			while ((line=rd.readLine()) != null ) {
				result += line;
			}
			rd.close();
			WeeklyForecast = result;
		} catch (MalformedURLException ex) {
			System.out.println("URL malformed");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}
	
	private void downloadThreeHourlyForecast() {
		String result = "";
		try {
			URL url = new URL(baseUrl + "val/wxfcs/all/json/" + locationID + "?res=3hourly&key=" + apiKey );
			//url = new URL(baseUrl + "val/wxfcs/all/json/sitelist" + "?key=" + apiKey );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader ( new InputStreamReader ( conn.getInputStream() ) );
			String line;
			while ((line=rd.readLine()) != null ) {
				result += line;
			}
			rd.close();
			ThreeHourlyForecast = result;
		} catch (MalformedURLException ex) {
			System.out.println("URL malformed");
		} catch (IOException ex) {
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}

    // Checks user settings for whether they use Celcius or Fahrenheit
    private boolean useCelcius(){
        return settings.getCelcius();
    }
}

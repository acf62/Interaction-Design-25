package uk.ac.cam.interactiondesign25.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import static uk.ac.cam.interactiondesign25.api.TemperatureConversion.toFahrenheit;

public class Weather {
    private Settings settings;
    private int locationID;
	
	private final String baseUrl = "http://datapoint.metoffice.gov.uk/public/data/";
	private final String apiKey = "5887b42a-ab8e-4285-94e8-9ef8ca4fe411";
	private final boolean active = false; //Used during development to reduce number of API calls
	
	private final long updateTime = 600; //update time in seconds

	//JSON data
	private String weeklyForecast;
	private String threeHourlyForecast;
	
	private long lastUpdateTime = 0;
	
    public Weather(String settingsFilename){
        settings = new Settings(settingsFilename);
        locationID = 310042; // default to cambridge
		if ( active) {
			doAPICallIfNecessary();
		}
    }

    // Returns today's maximum and minimum temperatures
    public int[] getTodayTemperatures(){
		int[] result = {0,0};
		if ( active ) {
			doAPICallIfNecessary();
			JSONObject weatherObject = new JSONObject ( weeklyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			JSONObject j = Period.getJSONObject(0);
			JSONArray a = j.getJSONArray("Rep");
			JSONObject day = a.getJSONObject(0);
			JSONObject night = a.getJSONObject(1);
			result[0] = day.getInt("Dm");
			result[1] = night.getInt("Nm");
			
			if ( !useCelcius() ) {
				result[0] = toFahrenheit(result[0]);
				result[1] = toFahrenheit(result[1]);
			}
		}
		return result;
    }

    // Returns today's 3-hourly temperature forecasts
    public int[] getTodayThreeHourlyTemperatures(){
        int[] result = {0,0,0,0,0};
		if ( active ) {
			doAPICallIfNecessary();
			JSONObject weatherObject = new JSONObject ( ThreeHourlyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			for ( int i = 0; i < Period.length(); ++i ) {
				JSONObject j = Period.getJSONObject(i);
				JSONArray a = j.getJSONArray("Rep");
				JSONObject day = a.getJSONObject(0);
				result[i] = day.getInt("Dm");
			}
		}
		return result;
    }

	public static void main(String[] args){
		// for testing purposes
		Weather w = new Weather("settings");
		w.getTodayThreeHourlyTemperatures();
	}

	// Returns a textual description of today's weather
    public String getTodayWeatherDescription(){
        return "Cloudy and mildly depressing";
    }

    // Returns type of today's weather (for use in selecting icons)
    public WeatherType getTodayWeatherType(){
		if ( active ) {
			doAPICallIfNecessary();
			JSONObject weatherObject = new JSONObject ( weeklyForecast );
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
			doAPICallIfNecessary();
			JSONObject weatherObject = new JSONObject ( weeklyForecast );
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
        String[] result = {"", "", "", "", ""};
		if ( active ) {
			String[] codes = {
				"Clear night",
				"Sunny day",
				"Partly cloudy",
				"Partly cloudy",
				"Error",
				"Mist",
				"Fog",
				"Cloudy",
				"Overcast",
				"Light rain shower",
				"Light rain shower",
				"Drizzle",
				"Light rain",
				"Heavy rain shower",
				"Heavy rain shower",
				"Heavy rain",
				"Sleet shower",
				"Sleet shower",
				"Sleet",
				"Hail shower",
				"Hail shower",
				"Hail",
				"Light snow shower",
				"Light snow shower",
				"Light snow",
				"Heavy snow shower",
				"Heavy snow shower",
				"Heavy snow",
				"Thunder shower",
				"Thunder shower",
				"Thunder"
			};
			doAPICallIfNecessary();
			JSONObject weatherObject = new JSONObject ( weeklyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			for ( int i = 0; i < Period.length() && i < 5; ++i ) {
				JSONObject j = Period.getJSONObject(i);
				JSONArray a = j.getJSONArray("Rep");
				JSONObject day = a.getJSONObject(0);
				int weatherCode = day.getInt("W");
				result[i] = codes[weatherCode];
			}
		}
		return result;
    }

    // Returns types of the weather each day this week, starting with today
    public WeatherType[] getWeekWeatherTypes(){
		WeatherType[] result = {
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
                WeatherType.UNKNOWN,
        };
		if ( active ) {
			doAPICallIfNecessary();
			JSONObject weatherObject = new JSONObject ( weeklyForecast );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			
			for ( int i = 0; i < Period.length() && i < 5; ++i ) {
				JSONObject j = Period.getJSONObject(i);
				JSONArray a = j.getJSONArray("Rep");
				JSONObject day = a.getJSONObject(0);
				int weatherCode = day.getInt("W");
				result[i] = WeatherType.convert(weatherCode);
			}
		}
		return result;

    }

    // Calls the API and caches the results locally unless already have data
    private void doAPICallIfNecessary(){
        if (!haveData()){
            downloadWeeklyForecast();
            downloadThreeHourlyForecast();
			lastUpdateTime = System.currentTimeMillis();
			System.out.println ( "Did API calls" );
        } else {
			System.out.println ( "Used cache" );
		}
    }

    // Returns true if we already have up-to-date data for the location
    private boolean haveData(){
		//lastUpdateTime starts at 0 and currentTimeMillis is since 1970 so this will pass on first try
		return System.currentTimeMillis() - lastUpdateTime < 1000*updateTime;
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
			weeklyForecast = result;
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
			threeHourlyForecast = result;
		} catch (MalformedURLException ex) {
			System.out.println("URL malformed");
		} catch (IOException ex) {
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}

    // Checks user settings for whether they use Celcius or Fahrenheit
    private boolean useCelcius(){
        return settings.getCelsius();
    }
}

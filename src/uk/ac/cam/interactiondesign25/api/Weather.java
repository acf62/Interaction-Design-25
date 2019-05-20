package uk.ac.cam.interactiondesign25.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import static uk.ac.cam.interactiondesign25.api.TemperatureConversion.toFahrenheit;

public class Weather {
	//Stores application settings persistently
	private Settings settings;
	//The id used by the MET office database for the current targeted location
	private int locationID;
	
	//The base URL for MET office database requests
	private final String baseUrl = "http://datapoint.metoffice.gov.uk/public/data/";
	//Personal API key used for database requests
	private final String apiKey = "5887b42a-ab8e-4285-94e8-9ef8ca4fe411";
	private final boolean active = true; //Used during development to reduce number of API calls
	private final boolean test = false; //Used during development to enable or disable testing

	private final long updateTime = 600; //update time in seconds

	//JSON data
	private String weeklyForecast;
	private String threeHourlyForecast;
	
	//Keeps track of when data was last downloaded from the MET office
	private long lastUpdateTime = 0;
	
	//Stores all locations about which we have data
	private Sitelist siteList;
	
	//Translation from integer to string of the MET office weather codes
	private static final String[] codes = {
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

	//Main used for testing
	public static void main(String[] args){
		Settings settings = new Settings ( "settingsTest" );
		Weather w = new Weather( settings );

		try(BufferedReader r = new BufferedReader(new FileReader("." + File.separator + "resources" + File.separator + "3hourlytestdata"))){
			w.threeHourlyForecast = r.readLine();
		} catch (IOException e){
			System.out.println("Setup failed");
			throw new Error();
		}

		try(BufferedReader r = new BufferedReader(new FileReader("." + File.separator + "resources" + File.separator + "dailytestdata"))){
			w.weeklyForecast = r.readLine();
		} catch (IOException e){
			System.out.println("Setup failed");
			throw new Error();
		}

		int[] expected = new int[]{17, 10};
		int[] result = w.getTodayTemperatures();
		if (!Arrays.equals(expected, result)) {
			System.out.println("getTodayTemperatures() broken");
		}

		expected = new int[]{16, 14, 11, 10, 10}; //depends on time of day
		result = w.getTodayThreeHourlyTemperatures();
		if (false) {
			System.out.println("getTodayThreeHourlyTemperatures() broken");
		}

		String exp = "Partly cloudy";
		String res = w.getTodayWeatherDescription();
		if (!exp.equals(res)){
			System.out.println("getTodayWeatherDescription() broken");
			System.out.println(res);
		}

		WeatherType e = WeatherType.PARTIALLY_CLOUDY;
		WeatherType r = w.getTodayWeatherType();
		if (e != r){
			System.out.println("getTodayWeatherType() broken");
		}

		int[][] Expected = new int[][]{{17, 10}, {14, 10}, {16, 8}, {18, 10}, {17, 10}};
		int[][] Result = w.getWeekTemperatures();
		if (!Arrays.deepEquals(Expected, Result)){
			System.out.println("getWeekTemperatures() broken");
		}

		String[] Exp = new String[]{
				"Partly cloudy",
				"Light rain",
				"Cloudy",
				"Light rain shower",
				"Cloudy",
		};
		String[] Res = w.getWeekWeatherDescription();
		if (!Arrays.equals(Exp, Res)){
			System.out.println("getWeekWeatherDescription() broken");
		}

		WeatherType[] E = new WeatherType[]{
				WeatherType.PARTIALLY_CLOUDY,
				WeatherType.RAINY,
				WeatherType.CLOUDY,
				WeatherType.RAINY,
				WeatherType.CLOUDY,
		};
		WeatherType[] R = w.getWeekWeatherTypes();
		if (!Arrays.equals(E, R)){
			System.out.println("getWeekWeatherTypes() broken");
		}
	}

	//Create a weather object with default location of cambridge
	public Weather(Settings settings){
		this.settings = settings;
		locationID = 310042; // default to cambridge
		if ( active) {
			doAPICallIfNecessary();
			siteList = new Sitelist(baseUrl + "val/wxfcs/all/json/sitelist?key=" + apiKey );
		}
	}

	//Change the location about which we are reporting the weather
	public void setLocationID(int location){
		locationID = location;
		lastUpdateTime = 0; //Make sure that the data is reloaded next time it's needed
	}
	
	//Return a list of suggestions of sites we have data about
	public List<String> autocomplete ( String input, int cutoffLength ) {
		return siteList.autocomplete(input, cutoffLength);
	}
	
	//Translates the name of a site to the id used internally
	public int getLocationIDFromName ( String name ) {
		return siteList.getId(name);
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
			
			if ( !usecelsius() ) {
				result[0] = toFahrenheit(result[0]);
				result[1] = toFahrenheit(result[1]);
			}
		}
		return result;
	}
	
	public int getTodayWindSpeed() {
		int result = 0;
		if ( active ) {
			doAPICallIfNecessary();
			JSONArray Period = new JSONObject ( threeHourlyForecast )
					.getJSONObject("SiteRep")
					.getJSONObject("DV")
					.getJSONObject("Location")
					.getJSONArray("Period");

			JSONArray rep = Period
				.getJSONObject(0) //today
				.getJSONArray("Rep");
				
			int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			int chunksLeftToday = (26-currentHour)/3;
			int availableChunks = rep.length();
			int currentChunk = availableChunks - chunksLeftToday;

			result = rep.getJSONObject(currentChunk).getInt("S");
		}
		return result;
	}

	// Returns today's 3-hourly temperature forecasts (array of 5 items)
	// Selects next five 3-hour chunks, starting from the current one
	public int[] getTodayThreeHourlyTemperatures(){
		int[] result = {0,0,0,0,0};
		if ( active ) {
			doAPICallIfNecessary();
			JSONArray Period = new JSONObject ( threeHourlyForecast )
					.getJSONObject("SiteRep")
					.getJSONObject("DV")
					.getJSONObject("Location")
					.getJSONArray("Period");

			int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			int chunksLeftToday = (26-currentHour)/3;
			int chunksFromTomorrow = 5 - chunksLeftToday;

			for(int i=0; i<chunksLeftToday; i++){
				JSONArray rep = Period
						.getJSONObject(0) //today
						.getJSONArray("Rep");
				result[i] = rep
						.getJSONObject(rep.length() - chunksLeftToday + i)
						.getInt("T");
			}
			// May have to go into tomorrow to get 5 chunks
			for(int i=0; i<chunksFromTomorrow; i++){
				JSONArray rep = Period
						.getJSONObject(1) //tomorrow
						.getJSONArray("Rep");
				result[i + chunksLeftToday] = rep
						.getJSONObject(i)
						.getInt("T");
			}
		}
		return result;
	}
	
	// Returns today's 3-hourly weather types (array of 5 items)
	// Selects next five 3-hour chunks, starting from the current one
	public WeatherType[] getTodayThreeHourlyWeatherTypes(){
		WeatherType[] result = {WeatherType.UNKNOWN, WeatherType.UNKNOWN, WeatherType.UNKNOWN, WeatherType.UNKNOWN, WeatherType.UNKNOWN};
		if ( active ) {
			doAPICallIfNecessary();
			JSONArray Period = new JSONObject ( threeHourlyForecast )
					.getJSONObject("SiteRep")
					.getJSONObject("DV")
					.getJSONObject("Location")
					.getJSONArray("Period");

			int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			int chunksLeftToday = (26-currentHour)/3;
			int chunksFromTomorrow = 5 - chunksLeftToday;

			for(int i=0; i<chunksLeftToday; i++){
				JSONArray rep = Period
						.getJSONObject(0) //today
						.getJSONArray("Rep");
				result[i] = WeatherType.convert(rep
						.getJSONObject(rep.length() - chunksLeftToday + i)
						.getInt("W"));
			}
			// May have to go into tomorrow to get 5 chunks
			for(int i=0; i<chunksFromTomorrow; i++){
				JSONArray rep = Period
						.getJSONObject(1) //tomorrow
						.getJSONArray("Rep");
				result[i + chunksLeftToday] = WeatherType.convert(rep
						.getJSONObject(i)
						.getInt("W"));
			}
		}
		return result;
	}
	
	// Returns the times associated with the three hour temperatures
	public String[] getTodayThreeHourTimes() {
		int times[] = {0,0,0,0,0};
		int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		times[0] = currentHour;
		for ( int i = 1; i < 5; ++i ) {
			times[i] = (times[i-1] + 3)%24;
		}
		String[] result = {"","","","",""};
		for ( int i = 0 ; i < 5; ++i ) {
			result[i] = Integer.toString(times[i])+":00";
		}
		return result;
		
	}

	// Returns a textual description of today's weather
    public String getTodayWeatherDescription(){
		String result;
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
			result = codes[weatherCode];
		}
		return result;
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
			
			if ( !usecelsius() ) {
				for ( int i = 0; i < Period.length() && i < 5; ++i ) {
					result[i][0] = toFahrenheit(result[i][0]);
					result[i][1] = toFahrenheit(result[i][1]);
				}
			}
		}
		return result;
	}

	// Returns textual descriptions of the weather each day this week,
	// starting with today (returns data for 5 days)
	public String[] getWeekWeatherDescription(){
		String[] result = {"", "", "", "", ""};
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
				result[i] = codes[weatherCode];
			}
		}
		return result;
	}

	// Returns types of the weather each day this week, starting with today (returns data for 5 days)
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
		if( test ){
			return;
		}
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
	
	//Download daily forecast data for the next 5 days
	private void downloadWeeklyForecast() {
		String result = "";
		try {
			URL url = new URL(baseUrl + "val/wxfcs/all/json/" + locationID + "?res=daily&key=" + apiKey );
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
	
	//Download 3-hourly data for the next 5 days
	private void downloadThreeHourlyForecast() {
		String result = "";
		try {
			URL url = new URL(baseUrl + "val/wxfcs/all/json/" + locationID + "?res=3hourly&key=" + apiKey );
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

	// Checks user settings for whether they use celsius or Fahrenheit
	private boolean usecelsius(){
		return settings.getCelsius();
	}
}

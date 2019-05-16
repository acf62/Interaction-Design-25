package uk.ac.cam.interactiondesign25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;

import org.json.JSONObject;

public class Weather {
	private final static String baseUrl = "http://datapoint.metoffice.gov.uk/public/data/";
	private final static String apiKey = "5887b42a-ab8e-4285-94e8-9ef8ca4fe411";
	
	//temp
	private final static String cambridgeID = "310042";
	
	private static String WeeklyForecast;
	private static String Next12HoursForecast;
	private static String locationID = cambridgeID;
	
	private static void downloadWeeklyForecast() {
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
		} catch (IOException ex) {
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}
	
	private static void downloadNext12HoursForecast() {
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
			Next12HoursForecast = result;
		} catch (MalformedURLException ex) {
			System.out.println("URL malformed");
		} catch (IOException ex) {
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}
	
	public static List<Integer> getWeeklyMaxTemps() {
		JSONObject weatherObject = new JSONObject ( WeeklyForecast );
		JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
		JSONObject Location = DV.getJSONObject("Location");
		JSONArray Period = Location.getJSONArray("Period");
		List<Integer> dayMax = new ArrayList<>();
		//List<Integer> nightMin = new ArrayList<>();
			
		for ( int i = 0; i < Period.length(); ++i ) {
			JSONObject j = Period.getJSONObject(i);
			JSONArray a = j.getJSONArray("Rep");
			JSONObject day = a.getJSONObject(0);
			dayMax.add(day.getInt("Dm"));
		}
			
		return dayMax;
	}
	
	public static List<Integer> getWeeklyMinTemps() {
		JSONObject weatherObject = new JSONObject ( WeeklyForecast );
		JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
		JSONObject Location = DV.getJSONObject("Location");
		JSONArray Period = Location.getJSONArray("Period");
		List<Integer> nightMin = new ArrayList<>();
			
		for ( int i = 0; i < Period.length(); ++i ) {
			JSONObject j = Period.getJSONObject(i);
			JSONArray a = j.getJSONArray("Rep");
			JSONObject night = a.getJSONObject(1);
			nightMin.add(night.getInt("Nm"));
		}
			
		return nightMin;
	}
	
	public static List<Integer> getNext12HourMaxTemps() {
		JSONObject weatherObject = new JSONObject ( WeeklyForecast );
		JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
		JSONObject Location = DV.getJSONObject("Location");
		JSONArray Period = Location.getJSONArray("Period");
		List<Integer> dayMax = new ArrayList<>();
		//List<Integer> nightMin = new ArrayList<>();
			
		for ( int i = 0; i < Period.length(); ++i ) {
			JSONObject j = Period.getJSONObject(i);
			JSONArray a = j.getJSONArray("Rep");
			JSONObject day = a.getJSONObject(0);
			dayMax.add(day.getInt("Dm"));
		}
			
		return dayMax;
	}
	
	public static void getWeather() {
		String result = "";
		URL url;
		try {
			url = new URL(baseUrl + "val/wxfcs/all/json/" + cambridgeID + "?res=daily&key=" + apiKey );
			//url = new URL(baseUrl + "val/wxfcs/all/json/sitelist" + "?key=" + apiKey );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader ( new InputStreamReader ( conn.getInputStream() ) );
			String line;
			while ((line=rd.readLine()) != null ) {
				result += line;
			}
			rd.close();
			System.out.println(result);
			
			JSONObject weatherObject = new JSONObject ( result );
			JSONObject DV = weatherObject.getJSONObject("SiteRep").getJSONObject("DV");
			JSONObject Location = DV.getJSONObject("Location");
			JSONArray Period = Location.getJSONArray("Period");
			List<Integer> dayMax = new ArrayList<>();
			List<Integer> nightMin = new ArrayList<>();
			
			for ( int i = 0; i < Period.length(); ++i ) {
				JSONObject j = Period.getJSONObject(i);
				JSONArray a = j.getJSONArray("Rep");
				JSONObject day = a.getJSONObject(0);
				JSONObject night = a.getJSONObject(1);
				dayMax.add(day.getInt("Dm"));
				nightMin.add(night.getInt("Nm"));
			}
			
			System.out.println ( Arrays.toString ( dayMax.toArray() ) );
			System.out.println ( Arrays.toString ( nightMin.toArray() ) );
			
		} catch (MalformedURLException ex) {
			System.out.println("URL malformed");
		} catch (IOException ex) {
			System.out.println("IO exception");
			ex.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		Weather.getWeather();
	}
}

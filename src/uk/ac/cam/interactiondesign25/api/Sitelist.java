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

// Stores data about the sites for which the MET office provides data
public class Sitelist {

	// An individual location
	private class Site {
		public int id; // Internal ID used by the MET office
		public String name; // Unqualified name of the site, e.g. "Cambridge"
	}
	
	private List<Site> sites = new ArrayList<>();
	
	public Sitelist ( String link ) {
		String json = "";
		try {
			// Make request and read in json
			URL url = new URL( link );
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader ( new InputStreamReader ( conn.getInputStream() ) );
			String line;
			while ((line=rd.readLine()) != null ) {
				json += line;
			}
			rd.close();

			// Convert to sites and store in list
			JSONArray location = new JSONObject (json)
					.getJSONObject ("Locations")
					.getJSONArray ("Location");
			for ( int i = 0; i < location.length(); ++i ) {
				Site s = new Site();
				JSONObject siteObject = location.getJSONObject(i);
				s.id = siteObject.getInt("id");
				s.name = siteObject.getString("name").toLowerCase();
				sites.add(s);
			}
		} catch (MalformedURLException e) {
			;
			e.printStackTrace();
		} catch (IOException e) {
			;
			e.printStackTrace();
		}
	}

	// Returns list of autocomplete "suggestions" for a location name
	List<String> autocomplete ( String input, int cutoffLength ) {
		String str = input.toLowerCase();
		List<String> suggestions = new ArrayList<>();
		// Finds top "cutoffLength" number closest results
		while ( suggestions.size() < cutoffLength ) {
			String closest = "";
			int maxCloseness = 0;
			for ( Site s : sites ) {
				if ( !suggestions.contains ( s.name ) ) {
					if ( closeness ( s.name, str ) > maxCloseness ) {
						maxCloseness = closeness(s.name, str);
						closest = s.name;
					} else if ( closeness ( s.name, str ) == maxCloseness ) {
						if ( closest.length() > s.name.length() ) {
							closest = s.name;
						}
					}
				}
			}
			suggestions.add(closest);
		}
		// Filter out empty strings
		List<String> result = new ArrayList<>();
		for ( String s : suggestions ) {
			if ( s.length() > 0 ) {
				result.add(s);
			}
		}
		return result;
	}

	// Measure of how "close" two place name strings are- total number of characters in common
	private int closeness ( String a, String b ) {
		int result = 0;
		if ( a.equals (b) ) return a.length() + 1; //If strings are equal, return maximum closeness
		for ( int i = 0; i < a.length() && i < b.length(); ++i ) {
			if ( a.charAt(i) == b.charAt(i) ) {
				++result;
			}
		}
		return result;
	}

	// Returns ID of location given its name
	public int getId ( String name ) {
		int id = -1;
		String str = name.toLowerCase();
		for ( Site s : sites ) {
			if ( s.name.equals(str) ) {
				id = s.id;
			}
		}
		return id;
	}
}

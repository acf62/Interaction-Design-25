package uk.ac.cam.interactiondesign25.api;

public class TemperatureConversion {
	public static int toFahrenheit ( int celsius ) {
		double x = 1.8*celsius;
		return (int)x + 32;
	}
	
	public static int toCelsius ( int fahrenheit ) {
		double x = (fahrenheit-32)/1.8;
		return (int)x;
	}
}

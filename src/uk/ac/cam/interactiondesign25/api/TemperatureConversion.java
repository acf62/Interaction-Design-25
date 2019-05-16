package uk.ac.cam.interactiondesign25.api;


public class TemperatureConversion {
	public static int toFahrenheit ( int celcius ) {
		double x = 1.8*celcius;
		return (int)x + 32;
	}
	
	public static int toCelcius ( int fahrenheit ) {
		double x = (fahrenheit-32)/1.8;
		return (int)x;
	}
}

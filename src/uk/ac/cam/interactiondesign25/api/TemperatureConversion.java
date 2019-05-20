package uk.ac.cam.interactiondesign25.api;

//Utility class for unit conversion
public class TemperatureConversion {
	public static int toFahrenheit ( int celsius ) {
		double x = 1.8*celsius;
		return (int)x + 32;
	}
}

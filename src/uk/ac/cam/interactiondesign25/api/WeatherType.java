package uk.ac.cam.interactiondesign25.api;

//The MET office uses numeric codes for weather conditions
//WeatherType exists to group these codes together into groups which need their own icon
public enum WeatherType {
	
	UNKNOWN,            // NA
	CLEAR_NIGHT,        // 0
    SUNNY,              // 1
    PARTIALLY_CLOUDY,   // 2, 3, 5
    CLOUDY,             // 6-8
    RAINY,              // 9-18
    SNOWY,              // 19-27
    THUNDER;            // 28-30
	
	public static WeatherType convert ( int code ) {
		if ( code < 0 ) {
			return WeatherType.UNKNOWN;
		} else if ( code == 0 ) {
			return WeatherType.CLEAR_NIGHT;
		} else if ( code == 1 ) {
			return WeatherType.SUNNY;
		} else if ( code <= 5 ) {
			return WeatherType.PARTIALLY_CLOUDY;
		} else if ( code <= 8 ) {
			return WeatherType.CLOUDY;
		} else if ( code <= 18 ) {
			return WeatherType.RAINY;
		} else if ( code <= 27 ) {
			return WeatherType.SNOWY;
		} else if ( code <= 30 ) {
			return WeatherType.THUNDER;
		} else {
			return WeatherType.UNKNOWN;
		}
	}
}

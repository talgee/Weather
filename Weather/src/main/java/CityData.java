public class CityData {
    public String cityName;
    public int sunrise;
    public int sunset;
    public Double degreesKelvin;
    public Double degreesCelsius;
    public int daylightDuration ;

    public CityData(){
    }

    public CityData(String cityName, int sunrise, int sunset, Double degreesKelvin) {
        this.cityName = cityName;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.degreesKelvin = degreesKelvin;
        this.degreesCelsius = degreesKelvin - 273.0 ;
        this.daylightDuration = sunset - sunrise;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public void setDegreesKelvin(Double degreesKelvin) {
        this.degreesKelvin = degreesKelvin;
    }

    public void setDaylightDuration(int daylightDuration) {
        this.daylightDuration = daylightDuration;
    }

    public Double getDegreesCelsius() {
        return degreesCelsius;
    }

    public int getDaylightDuration() {
        return daylightDuration;
    }
}

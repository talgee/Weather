import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class WeatherMain {

    private static final String APPID = "90d43256f8a2db49fce09d509a22869a";
    private static ArrayList<String> cityNamesList = new ArrayList<>();
    private static ArrayList<CityData> allCitiesList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        cityNamesList.add("Tel Aviv");
        cityNamesList.add("Singapore");
        cityNamesList.add("Auckland");
        cityNamesList.add("Ushuaia");
        cityNamesList.add("Miami");
        cityNamesList.add("London");
        cityNamesList.add("Berlin");
        cityNamesList.add("Reykjavik");
        cityNamesList.add("Cape Town");
        cityNamesList.add("Kathmandu");
        WeatherMain http = new WeatherMain();
        for (String city : cityNamesList ){
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID="+ APPID;
            String jsonData = http.sendGet(url);
            CityData cityData = new CityData(city, sunriseOrSunset(jsonData,"sunrise"), sunriseOrSunset(jsonData,"sunset"),temp(jsonData));
            allCitiesList.add(cityData);
        }
//        for (int i = 0; i < cityNamesList.size(); i++) {
//            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityNamesList.get(i) + "&APPID="+ APPID;
//            String jsonData = http.sendGet(url);
//            CityData cityData = new CityData(cityNamesList.get(i),sunriseOrSunset(jsonData,"sunrise"),sunriseOrSunset(jsonData,"sunset"),temp(jsonData));
//            allCitiesList.add(cityData);
//        }
        for (CityData data: allCitiesList) {
            System.out.println("city " + data.cityName);
            System.out.println("sunrise time " + data.sunrise);
            System.out.println("sunset time " + data.sunset);
            System.out.println("kelvin degrees " + data.degreesKelvin);
            System.out.println("celsius degrees " + data.degreesCelsius);
            System.out.println("duration of daytime " + data.daylightDuration);
            System.out.println();
        }

//        for (int i = 0; i < allCitiesList.size(); i++) {
//            System.out.println("city " + allCitiesList.get(i).cityName);
//            System.out.println("sunrise time " + allCitiesList.get(i).sunrise);
//            System.out.println("sunset time " + allCitiesList.get(i).sunset);
//            System.out.println("kelvin degrees " + allCitiesList.get(i).degreesKelvin);
//            System.out.println("celsius degrees " + allCitiesList.get(i).degreesCelsius);
//            System.out.println("duration of daytime " + allCitiesList.get(i).daylightDuration);
//            System.out.println();
//        }

        getMax(allCitiesList);
        getMin(allCitiesList);
    }


    private String sendGet(String url) throws Exception {
//        String USER_AGENT = "Mozilla/5.0";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static Double temp (String jsonData){
        try{
            JSONObject cityJsonMain = new JSONObject(jsonData).getJSONObject("main");
            return (Double)  cityJsonMain.get("temp");
        } catch (Exception e){
            e.printStackTrace();
            return 0.0 ;
        }
    }

    public static int sunriseOrSunset (String jsonData,String sunriseOrSunset){
        try{
            JSONObject cityJsonMain = new JSONObject(jsonData).getJSONObject("sys");
            return (int)  cityJsonMain.get(sunriseOrSunset);
        }catch (Exception e){
            e.printStackTrace();
            return 0 ;
        }
    }

    public static void getMax(ArrayList<CityData> allCitiesList){
        int maxValue = allCitiesList.get(0).daylightDuration;
        String maxCity = null;
        Double maxTemp = 0.0;

        for(int i=1;i < allCitiesList.size();i++){
            if(allCitiesList.get(i).daylightDuration > maxValue){
                maxValue = allCitiesList.get(i).daylightDuration;
                maxCity = allCitiesList.get(i).cityName;
                maxTemp = allCitiesList.get(i).degreesCelsius;
            }
        }

        DecimalFormat df = new DecimalFormat("###.###");
        System.out.println(df.format(maxTemp));
        System.out.println("the city with the maximum time of day light is : " + maxCity + " and the temperature is : " + df.format(maxTemp));
    }

    public static void getMin(ArrayList<CityData> allCitiesList){
        int minValue = allCitiesList.get(0).daylightDuration;
        String minCity = null;
        Double minTemp = 0.0;

        for(int i=1;i < allCitiesList.size();i++){
            if(allCitiesList.get(i).daylightDuration < minValue){
                minValue = allCitiesList.get(i).daylightDuration;
                minCity = allCitiesList.get(i).cityName;
                minTemp = allCitiesList.get(i).degreesCelsius;
            }
        }
        System.out.println("the city with the minimum time of day light is : " + minCity + " and the temperature is : " + minTemp);
    }
}

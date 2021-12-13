package pl.wojciechsiwek.controller.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.WeatherDataResult;

import java.util.Locale;

public class GetWeatherDataService extends Service {

    WeatherManager weatherManager;
    String location;

    String whichPane;

    Integer currentStatus, forecastStatus;

    public GetWeatherDataService(WeatherManager weatherManager, String location, String whichPane) {
        this.weatherManager = weatherManager;
        this.location = location;
        this.whichPane = whichPane;
    }


    @Override
    protected Task createTask() {
        return new Task<WeatherDataResult>() {
            @Override
            protected WeatherDataResult call() throws Exception {
                return getWeatherData(location, whichPane);
            }
        };
    }


    private WeatherDataResult getWeatherData(String location, String whichPane) {

        location = location.toLowerCase(Locale.ROOT).replace(",", "%2C").replace(" ", "%20");
        try {
            HttpResponse<JsonNode> currentWeatherResponse = Unirest.get("https://community-open-weather-map.p.rapidapi.com/weather?q=" + location + "&id=2172797&lang=pl&units=metric&mode=json")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();

            HttpResponse<JsonNode> forecastResponse = Unirest.get("https://community-open-weather-map.p.rapidapi.com/forecast/daily?q=" + location + "&cnt=5&units=metric&mode=json&lang=pl")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();


            this.currentStatus = currentWeatherResponse.getStatus();
            this.forecastStatus = forecastResponse.getStatus();


            System.out.println("Forecast " + this.whichPane + " weather data response status: " + forecastResponse.getStatus());
            System.out.println("Current " + this.whichPane + " weather data response status: " + currentWeatherResponse.getStatus());
            if ((forecastResponse.getStatus() == 200) && (currentWeatherResponse.getStatus() == 200)) {
                if (this.whichPane == "left") {
                    weatherManager.currentDataLeft = currentWeatherResponse.getBody();
                    weatherManager.forecastDataLeft = forecastResponse.getBody();
                } else {
                    weatherManager.currentDataRight = currentWeatherResponse.getBody();
                    weatherManager.forecastDataRight = forecastResponse.getBody();
                }
                return WeatherDataResult.SUCCESS;
            }


        } catch (Exception e) {

            e.printStackTrace();
            return WeatherDataResult.FAILED;
        }

        if (this.currentStatus == 429 || this.forecastStatus == 429) {
            return WeatherDataResult.FAILED_BY_TOO_MANY_CONNECTIONS;
        } else if (this.currentStatus == 404 || this.forecastStatus == 404) {
            return WeatherDataResult.FAILED_NO_LOCATION_FOUND;
        } else if (this.currentStatus == 401 || this.forecastStatus == 401) {
            return WeatherDataResult.FAILED_WRONG_KEY;
        } else return WeatherDataResult.FAILED;
    }
}

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

    public void setLocation(String location) {
        this.location = location;
    }

    public GetWeatherDataService(WeatherManager weatherManager, String location) {
        this.weatherManager = weatherManager;
        this.location = location;
    }


    @Override
    protected Task createTask() {
        return new Task<WeatherDataResult>() {
            @Override
            protected WeatherDataResult call() throws Exception {
                return getWeatherDataLeft(location);
            }
        };
    }


    private WeatherDataResult getWeatherDataLeft(String location) {

        location = location.toLowerCase(Locale.ROOT).replace(",","%2C").replace(" ", "%20");
        try {
            HttpResponse<JsonNode> currentWeatherResponse = Unirest.get("https://community-open-weather-map.p.rapidapi.com/weather?q=" + location + "&id=2172797&lang=pl&units=metric&mode=json")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();

            HttpResponse<JsonNode> forecastResponse = Unirest.get("https://community-open-weather-map.p.rapidapi.com/forecast/daily?q=" + location + "&cnt=5&units=metric&mode=json&lang=pl")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();
            System.out.println("Forecast weather data response status: " + forecastResponse.getStatus());
            System.out.println("Current weather data response status: " + currentWeatherResponse.getStatus());
            if ((forecastResponse.getStatus() == 200) && (currentWeatherResponse.getStatus() == 200)) {
                weatherManager.currentDataLeft = currentWeatherResponse.getBody();
                weatherManager.forecastDataLeft = forecastResponse.getBody();
                return WeatherDataResult.SUCCESS;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return WeatherDataResult.FAILED;
        }

        return WeatherDataResult.FAILED;
    }
}

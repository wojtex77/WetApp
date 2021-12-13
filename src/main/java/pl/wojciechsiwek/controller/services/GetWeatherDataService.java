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
    String locationLeft;
    String locationRight;

    Integer leftCurrentStatus, leftForecastStatus, rightCurrentStatus, rightForecastStatus;

    public GetWeatherDataService(WeatherManager weatherManager, String locationLeft, String locationRight) {
        this.weatherManager = weatherManager;
        this.locationLeft = locationLeft;
        this.locationRight = locationRight;
    }


    @Override
    protected Task createTask() {
        return new Task<WeatherDataResult>() {
            @Override
            protected WeatherDataResult call() throws Exception {
                return getWeatherDataLeft(locationLeft, locationRight);
            }
        };
    }


    private WeatherDataResult getWeatherDataLeft(String locationLeft, String locationRight) {

        locationLeft = locationLeft.toLowerCase(Locale.ROOT).replace(",","%2C").replace(" ", "%20");
        locationRight = locationRight.toLowerCase(Locale.ROOT).replace(",","%2C").replace(" ", "%20");
        try {
            HttpResponse<JsonNode> currentWeatherResponseLeft = Unirest.get("https://community-open-weather-map.p.rapidapi.com/weather?q=" + locationLeft + "&id=2172797&lang=pl&units=metric&mode=json")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();

            HttpResponse<JsonNode> forecastResponseLeft = Unirest.get("https://community-open-weather-map.p.rapidapi.com/forecast/daily?q=" + locationLeft + "&cnt=5&units=metric&mode=json&lang=pl")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();


            HttpResponse<JsonNode> currentWeatherResponseRight = Unirest.get("https://community-open-weather-map.p.rapidapi.com/weather?q=" + locationRight + "&id=2172797&lang=pl&units=metric&mode=json")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();

            HttpResponse<JsonNode> forecastResponseRight = Unirest.get("https://community-open-weather-map.p.rapidapi.com/forecast/daily?q=" + locationRight + "&cnt=5&units=metric&mode=json&lang=pl")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asJson();


            this.leftCurrentStatus = currentWeatherResponseLeft.getStatus();
            this.leftForecastStatus = forecastResponseLeft.getStatus();
            this.rightCurrentStatus = currentWeatherResponseRight.getStatus();
            this.rightForecastStatus = forecastResponseRight.getStatus();


            System.out.println("Forecast left weather data response status: " + forecastResponseLeft.getStatus());
            System.out.println("Current left weather data response status: " + currentWeatherResponseLeft.getStatus());
            System.out.println("Forecast right weather data response status: " + forecastResponseRight.getStatus());
            System.out.println("Current right weather data response status: " + currentWeatherResponseRight.getStatus());
            if ((forecastResponseLeft.getStatus() == 200) && (currentWeatherResponseLeft.getStatus() == 200) && (forecastResponseRight.getStatus() == 200) && (currentWeatherResponseRight.getStatus() == 200)) {
                weatherManager.currentDataLeft = currentWeatherResponseLeft.getBody();
                weatherManager.forecastDataLeft = forecastResponseLeft.getBody();
                weatherManager.currentDataRight = currentWeatherResponseRight.getBody();
                weatherManager.forecastDataRight = forecastResponseRight.getBody();
                return WeatherDataResult.SUCCESS;
            }


        } catch (Exception e) {

            e.printStackTrace();
            return WeatherDataResult.FAILED;
        }

        if (this.leftCurrentStatus == 429 || this.leftForecastStatus == 429){
            return WeatherDataResult.FAILED_BY_TOO_MANY_CONNECTIONS;
        }
        else if (this.leftCurrentStatus == 404 || this.leftForecastStatus == 404){
            return WeatherDataResult.FAILED_NO_LOCATION_FOUND;
        }
        else if (this.leftCurrentStatus == 401 || this.leftForecastStatus == 401){
            return WeatherDataResult.FAILED_WRONG_KEY;
        }
        else return WeatherDataResult.FAILED;
    }
}

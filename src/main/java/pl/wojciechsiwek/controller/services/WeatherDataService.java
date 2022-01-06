package pl.wojciechsiwek.controller.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.config.Configuration;
import pl.wojciechsiwek.controller.WeatherDataResult;
import pl.wojciechsiwek.model.CurrentData;

import java.util.Locale;

public class WeatherDataService extends Service {

    private final WeatherManager weatherManager;
    private String location;
    private final String whichPane;
    private Integer currentStatus;

    public WeatherDataService(WeatherManager weatherManager, String location, String whichPane) {
        this.weatherManager = weatherManager;
        this.location = location;
        this.whichPane = whichPane;
    }


    @Override
    protected Task createTask() {
        return new Task<WeatherDataResult>() {
            @Override
            protected WeatherDataResult call() throws Exception {
                return getData(location, whichPane);
            }
        };
    }

    private WeatherDataResult getData(String location, String whichPane) {
        location = location.toLowerCase(Locale.ROOT).replace(",", "%2C").replace(" ", "%20");
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + Configuration.getAPIKey() + "&units=metric&lang=pl";
        try {
            HttpResponse<JsonNode> currentWeatherResponse = Unirest.get(url).asJson();

            this.currentStatus = currentWeatherResponse.getStatus();

            System.out.println("Current " + this.whichPane + " weather data response status: " + currentWeatherResponse.getStatus());
            if ((currentWeatherResponse.getStatus() == 200)) {
                if (whichPane == "left") {
                    weatherManager.setCurrentDataLeft(currentWeatherResponse.getBody());
                    weatherManager.convertCurrentToObject(whichPane);
                    getForecast(weatherManager.getCurrentDataObjectLeft());

                } else {
                }
                return WeatherDataResult.SUCCESS;
            }
            return WeatherDataResult.FAILED;

        } catch (Exception e) {
            e.printStackTrace();
            return WeatherDataResult.FAILED;
        }
    }

    private WeatherDataResult getForecast(CurrentData currentDataObjectLeft) {
        double longtitude = currentDataObjectLeft.getLongtitude();
        double latitude = currentDataObjectLeft.getLatitude();

        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longtitude + "&appid=" + Configuration.getAPIKey() + "&units=metric&lang=pl";
        try {
            HttpResponse<JsonNode> forecastWeatherResponse = Unirest.get(url).asJson();

            this.currentStatus = forecastWeatherResponse.getStatus();

            System.out.println("Forecast " + this.whichPane + " weather data response status: " + forecastWeatherResponse.getStatus());
            if ((forecastWeatherResponse.getStatus() == 200)) {
                if (whichPane == "left") {
                    weatherManager.setForecastDataLeft(forecastWeatherResponse.getBody());
                    weatherManager.convertForecastToObject(whichPane);
                } else {
                }
                return WeatherDataResult.SUCCESS;
            }
            return WeatherDataResult.FAILED;

        } catch (Exception e) {
            e.printStackTrace();
            return WeatherDataResult.FAILED;
        }
    }
}

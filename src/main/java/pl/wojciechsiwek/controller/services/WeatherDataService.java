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
    private final String whichPane;
    private final String location;

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
                try {
                    return getData(location, whichPane);
                } catch (Exception e) {
                    e.printStackTrace();
                    return WeatherDataResult.FAILED;
                }

            }
        };
    }

    private WeatherDataResult getData(String location, String whichPane) {
        location = location.toLowerCase(Locale.ROOT).replace(",", "%2C").replace(" ", "%20");
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + Configuration.getAPIKey() + "&units=metric&lang=pl";
        try {
            HttpResponse<JsonNode> currentWeatherResponse = Unirest.get(url).asJson();


            System.out.println("Current " + this.whichPane + " weather data response status: " + currentWeatherResponse.getStatus());
            if ((currentWeatherResponse.getStatus() == 200)) {
                if (whichPane.equals("left")) {
                    weatherManager.setCurrentDataLeft(currentWeatherResponse.getBody());
                    weatherManager.convertCurrentToObject(whichPane);
                    getForecast(weatherManager.getCurrentDataObjectLeft());

                } else {
                    weatherManager.setCurrentDataRight(currentWeatherResponse.getBody());
                    weatherManager.convertCurrentToObject("right");
                    getForecast(weatherManager.getCurrentDataObjectRight());
                }
                return WeatherDataResult.SUCCESS;
            } else if (currentWeatherResponse.getStatus() == 404) {
                return WeatherDataResult.FAILED_NO_LOCATION_FOUND;
            } else if (currentWeatherResponse.getStatus() == 401) {
                return WeatherDataResult.FAILED_WRONG_KEY;
            } else if (currentWeatherResponse.getStatus() == 429) {
                return WeatherDataResult.FAILED_BY_TOO_MANY_CONNECTIONS;
            }
            return WeatherDataResult.FAILED;

        } catch (Exception e) {
            e.printStackTrace();
            return WeatherDataResult.FAILED;
        }
    }

    private WeatherDataResult getForecast(CurrentData currentDataObject) {
        double longtitude = currentDataObject.getLongtitude();
        double latitude = currentDataObject.getLatitude();

        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longtitude + "&appid=" + Configuration.getAPIKey() + "&units=metric&lang=pl";
        try {
            HttpResponse<JsonNode> forecastWeatherResponse = Unirest.get(url).asJson();


            System.out.println("Forecast " + this.whichPane + " weather data response status: " + forecastWeatherResponse.getStatus());
            if ((forecastWeatherResponse.getStatus() == 200)) {
                if (whichPane.equals("left")) {
                    weatherManager.setForecastDataLeft(forecastWeatherResponse.getBody());
                    weatherManager.convertForecastToObject(whichPane);
                } else {
                    weatherManager.setForecastDataRight(forecastWeatherResponse.getBody());
                    weatherManager.convertForecastToObject(whichPane);
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

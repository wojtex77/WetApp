package pl.wojciechsiwek.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.config.Configuration;
import pl.wojciechsiwek.model.CurrentData;

import java.util.Locale;

public class DataDownloader {


    private final String whichPane;
    private final WeatherManager weatherManager;
    private String location;

    public DataDownloader(String whichPane, WeatherManager weatherManager, String location) {
        this.whichPane = whichPane;
        this.weatherManager = weatherManager;
        this.location = location;
    }



    public WeatherDataResult getData() {
        location = location.toLowerCase(Locale.ROOT).replace(",", "%2C").replace(" ", "%20");
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + Configuration.getAPIKey() + "&units=metric&lang=pl";
        try {
            HttpResponse<JsonNode> currentWeatherResponse = Unirest.get(url).asJson();


            System.out.println("Current " + this.whichPane + " weather data response status: " + currentWeatherResponse.getStatus());
            if ((currentWeatherResponse.getStatus() == 200)) {
                if (whichPane.equals("left")) {
                    weatherManager.setCurrentDataLeft(currentWeatherResponse.getBody());
                    weatherManager.convertCurrentToObject(whichPane);
                    return getForecast(weatherManager.getCurrentDataObjectLeft());

                } else {
                    weatherManager.setCurrentDataRight(currentWeatherResponse.getBody());
                    weatherManager.convertCurrentToObject("right");
                    return getForecast(weatherManager.getCurrentDataObjectRight());
                }
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

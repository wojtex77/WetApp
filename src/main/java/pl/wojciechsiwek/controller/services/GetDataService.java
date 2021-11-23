package pl.wojciechsiwek.controller.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.WeatherDataResult;

public class GetDataService extends Service {

    WeatherManager weatherManager;

    public GetDataService(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }


    @Override
    protected Task createTask() {
        return new Task<WeatherDataResult>() {
            @Override
            protected WeatherDataResult call() throws Exception {
                return getWeatherData();
            }
        };
    }


    private WeatherDataResult getWeatherData() {

        try {
            HttpResponse<String> response = Unirest.get("https://community-open-weather-map.p.rapidapi.com/weather?q=London%2Cuk&lat=0&lon=0&callback=test&id=2172797&lang=null&units=imperial&mode=xml")
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "13aed539c7msh2c42616037c9a87p1393eajsn2c644a8d22df")
                    .asString();
            System.out.println(response.getStatus());
            System.out.println(response.getHeaders().get("Content-Type"));
        } catch (Exception e){
            e.printStackTrace();
        }

        return WeatherDataResult.SUCCESS;
    }
}

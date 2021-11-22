package pl.wojciechsiwek.controller.services;

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
        return WeatherDataResult.SUCCESS;
    }
}

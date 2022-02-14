package pl.wojciechsiwek.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.DataDownloader;
import pl.wojciechsiwek.controller.WeatherDataResult;

public class WeatherDataService extends Service {

    private DataDownloader dataDownloader;

    public WeatherDataService(WeatherManager weatherManager, String location, String whichPane) {
        this.dataDownloader = new DataDownloader(whichPane,weatherManager, location);
    }


    @Override
    protected Task createTask() {
        return new Task<WeatherDataResult>() {
            @Override
            protected WeatherDataResult call() {
                try {
                    return dataDownloader.getData();
                } catch (Exception e) {
                    e.printStackTrace();
                    return WeatherDataResult.FAILED;
                }

            }
        };
    }
}

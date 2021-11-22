package pl.wojciechsiwek.view;

import pl.wojciechsiwek.WeatherManager;

public class ViewFactory {
    private WeatherManager weatherManager;

    public ViewFactory(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }

    public void showMainWindow () {
        System.out.println("Main window showed");
    }
}

package pl.wojciechsiwek.controller;

import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.view.ViewFactory;

public class BaseController {

    public WeatherManager weatherManager;
    public ViewFactory viewFactory;
    public String fxmlName;

    public BaseController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        this.weatherManager = weatherManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}

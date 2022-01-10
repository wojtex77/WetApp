package pl.wojciechsiwek.controller;

import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.view.ViewFactory;

public class BaseController {

    public final WeatherManager weatherManager;
    public final ViewFactory viewFactory;
    public final String fxmlName;

    public BaseController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        this.weatherManager = weatherManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}

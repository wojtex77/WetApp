package pl.wojciechsiwek.view;

import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;

public class AboutController extends BaseController {

    public AboutController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }
}

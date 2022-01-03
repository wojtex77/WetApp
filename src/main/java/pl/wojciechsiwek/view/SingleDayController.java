package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.wojciechsiwek.model.ForecastWeatherData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleDayController {

    @FXML
    private Label temp;

    @FXML
    private Label tempNight;

    @FXML
    private Label pressure;

    @FXML
    private Label hummidity;

    @FXML
    private Label description;

    @FXML
    private Label date;

    public void updateData(Date day, ForecastWeatherData forecastWeatherData, int whichDay) {
        SimpleDateFormat formatershort = new SimpleDateFormat("dd.MM.yy");

        date.setText(formatershort.format(day));
        temp.setText("dzień: " + forecastWeatherData.getForecast().get(whichDay).getTemperatures().getDay() + " " + (char) 176 + "C");
        tempNight.setText("noc: " + forecastWeatherData.getForecast().get(whichDay).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure.setText("ciśnienie: " + forecastWeatherData.getForecast().get(whichDay).getPressure() + " hPa");
        hummidity.setText("wilgotność: " + forecastWeatherData.getForecast().get(whichDay).getHumidity() + " %");
        description.setText(forecastWeatherData.getForecast().get(whichDay).getDescription().getDescription());


    }

}



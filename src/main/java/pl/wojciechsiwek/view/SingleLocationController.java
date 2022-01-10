package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.config.Messages;
import pl.wojciechsiwek.controller.WeatherDataResult;
import pl.wojciechsiwek.controller.services.WeatherDataService;
import pl.wojciechsiwek.model.CurrentData;
import pl.wojciechsiwek.model.ForecastData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleLocationController {

    @FXML
    private Label forecastLabel;

    @FXML
    private Label currentLocation;

    @FXML
    private Label actualTemp;

    @FXML
    private Label coordinates;

    @FXML
    private Label tempFeel;

    @FXML
    private Label actualWeathCond;

    @FXML
    private Label pressure;

    @FXML
    private Label actualizationInfo;

    // forecast
    @FXML
    private Parent firstDay, secondDay, thirdDay, fourthDay, fifthDay; //embeddedElements

    @FXML
    private SingleDayController firstDayController, secondDayController, thirdDayController, fourthDayController, fifthDayController;

    private final Messages messages = new Messages();


    public void updateWeather(WeatherManager weatherManager, String location, String whichPane) {
        WeatherDataService currentWeatherDataService = new WeatherDataService(weatherManager, location, whichPane);
        currentWeatherDataService.start();
        setActualizationInfo("Aktualizuję dane...");

        currentWeatherDataService.setOnSucceeded(event -> {
            WeatherDataResult weatherDataResult = (WeatherDataResult) currentWeatherDataService.getValue();

            switch (weatherDataResult) {
                case SUCCESS: {
                    System.out.println("Data refreshing done");

                    if (whichPane.equals("left")) {
                        setActualConditions(weatherManager.getCurrentDataObjectLeft());
                        setForecastConditions(weatherManager.getForecastDataArrayLeft());
                    } else {
                        setActualConditions(weatherManager.getCurrentDataObjectRight());
                        setForecastConditions(weatherManager.getForecastDataArrayRight());
                    }
                    break;

                }
                case FAILED: {
                    System.out.println("Data refreshing failed");
                    actualizationInfo.setText(messages.getDataActualizationFailedMessage());
                    break;
                }
                case FAILED_NO_LOCATION_FOUND: {
                    System.out.println("No location found");
                    actualizationInfo.setText(messages.getNoLocationFoundMessage());
                    break;
                }
                case FAILED_BY_TOO_MANY_CONNECTIONS: {
                    System.out.println("Too many connections");
                    actualizationInfo.setText(messages.getTooManyConectionsMessage());
                    break;
                }
                case FAILED_WRONG_KEY: {
                    System.out.println("Incorrect API key");
                    actualizationInfo.setText(messages.getIncorrectAPIMessage());
                    break;
                }
            }
        });
    }

    private void setForecastConditions(ArrayList<ForecastData> data) {
        firstDayController.updateData(data.get(0));
        secondDayController.updateData(data.get(1));
        thirdDayController.updateData(data.get(2));
        fourthDayController.updateData(data.get(3));
        fifthDayController.updateData(data.get(4));
    }

    private void setActualConditions(CurrentData data) {
        forecastLabel.setVisible(true);
        coordinates.setText("Szerokość: " + data.getLatitude() + "; Długość: " + data.getLongtitude());
        coordinates.setVisible(true);
        currentLocation.setText(data.getCity() + ", " + data.getCountry());
        actualTemp.setText(data.getTemperature() + " " + (char) 176 + "C");
        tempFeel.setText("Odczuwalna: " + data.getFeelsLike() + " " + (char) 176 + "C");
        pressure.setText("Ciśnienie: " + data.getPressure() + " hPa");
        actualWeathCond.setText(data.getDescription());
        setActualizationInfo("Ostatnio zaktualizowano " + getDate());
    }


    private String getDate() {
        Date date = new Date();
        SimpleDateFormat formatterLong = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return formatterLong.format(date);
    }

    public void setActualizationInfo(String info) {
        actualizationInfo.setText(info);
        actualizationInfo.setVisible(true);
    }


}

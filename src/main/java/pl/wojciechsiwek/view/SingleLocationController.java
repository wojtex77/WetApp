package pl.wojciechsiwek.view;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.WeatherDataResult;
import pl.wojciechsiwek.controller.services.GetWeatherDataService;
import pl.wojciechsiwek.model.CurrentWeatherData;
import pl.wojciechsiwek.model.ForecastWeatherData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public void updateWeather(WeatherManager weatherManager, String location, String whichPane) {
        GetWeatherDataService getDataService = new GetWeatherDataService(weatherManager, location, whichPane);
        getDataService.start();
        actualizationInfo.setText("Aktualizuję dane...");
        actualizationInfo.setVisible(true);

        getDataService.setOnSucceeded(event -> {
            WeatherDataResult weatherDataResult = (WeatherDataResult) getDataService.getValue();

            switch (weatherDataResult) {
                case SUCCESS: {
                    System.out.println("Data refreshing done");
                    updateData(weatherManager);
                    break;

                }
                case FAILED: {
                    System.out.println("Data refreshing failed");
                    actualizationInfo.setText("Aktualizacja danych nie powiodła się");
                    break;
                }
                case FAILED_NO_LOCATION_FOUND: {
                    System.out.println("No location found");
                    actualizationInfo.setText("Aktualizacja danych nie powiodła się - lokalizacja niepoprawna");
                    break;
                }
                case FAILED_BY_TOO_MANY_CONNECTIONS: {
                    System.out.println("Too many connections");
                    actualizationInfo.setText("Aktualizacja danych nie powiodła się - zbyt wiele zapytań, odśwież za minutkę");
                    break;
                }
                case FAILED_WRONG_KEY: {
                    System.out.println("Incorrect API key");
                    actualizationInfo.setText("Aktualizacja danych nie powiodła się - skontaktuj się z deweloperem");
                    break;
                }
            }
        });
    }

    private void updateData(WeatherManager weatherManager) {
        //getting date for info when last actualization was
        Date date = new Date();
        SimpleDateFormat formatterLong = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        actualizationInfo.setText("Ostatnio zaktualizowano " + formatterLong.format(date));

        //converting current weather json to object
        Gson gson = new Gson();
        CurrentWeatherData currentWeatherData = gson.fromJson(String.valueOf(weatherManager.getCurrentDataRight()), CurrentWeatherData.class);
        currentWeatherData.convertMainToObject();

        //converting forecast weather json to object
        ForecastWeatherData forecastWeatherData = gson.fromJson(String.valueOf(weatherManager.getForecastDataRight()), ForecastWeatherData.class);

        forecastWeatherData.convertListToArrayOfObjects();
        forecastWeatherData.convertCityToObject();
        forecastWeatherData.cityObject.convertCoordinatesToObject();
//left side
        forecastLabel.setVisible(true);
        coordinates.setText("Szerokość: " + forecastWeatherData.cityObject.coordinates.getLat() + "; Długość: " + forecastWeatherData.cityObject.coordinates.getLon());
        coordinates.setVisible(true);
        currentLocation.setText(currentWeatherData.getName() + ", " + forecastWeatherData.getCityObject().getCountry());
        actualTemp.setText(currentWeatherData.mainWeatherData.getTemp() + " " + (char) 176 + "C");
        tempFeel.setText("Odczuwalna: " + currentWeatherData.mainWeatherData.getFeels_like() + " " + (char) 176 + "C");
        pressure.setText("Ciśnienie: " + currentWeatherData.mainWeatherData.getPressure() + " hPa");
        actualWeathCond.setText(currentWeatherData.mainWeatherData.getDescription());


        int i =0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

// first day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        firstDayController.updateData(date, forecastWeatherData,i);
        i++;

// second day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        secondDayController.updateData(date, forecastWeatherData,i);
        i++;


// third day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        thirdDayController.updateData(date, forecastWeatherData,i);
        i++;

// fourth day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        fourthDayController.updateData(date, forecastWeatherData,i);
        i++;

// fifth day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        fifthDayController.updateData(date, forecastWeatherData,i);
        i++;

    }

    public void setActualizationInfo(String info){
        actualizationInfo.setText(info);
        actualizationInfo.setVisible(true);
    }


}

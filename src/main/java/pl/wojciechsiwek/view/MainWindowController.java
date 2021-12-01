package pl.wojciechsiwek.view;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;
import pl.wojciechsiwek.controller.WeatherDataResult;
import pl.wojciechsiwek.controller.services.GetWeatherDataService;
import pl.wojciechsiwek.model.CurrentWeatherData;
import pl.wojciechsiwek.model.ForecastWeatherData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainWindowController extends BaseController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button refreshButton;

    @FXML
    private Label currentLocalization;

    @FXML
    private Label leftHeader;

    @FXML
    private Label actualTempLeft;

    @FXML
    private Label actualWeathCondLeft;

    @FXML
    private MenuItem exitButton;

    @FXML
    private Label actualizationInfo;

    @FXML
    private Label tempFeelLeft;

    @FXML
    private Label pressureLeft;


    @FXML
    private Label date1Left, temp1left, temp1NightLeft, pressure1Left, hummidity1Left, description1Left;



    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }

    @FXML
    void exitProgramAction() {
        System.out.println("Exit program action called");
        Stage stage = (Stage) actualWeathCondLeft.getScene().getWindow();
        viewFactory.closeStage(stage);

    }

    @FXML
    void refreshDataAction() {
        System.out.println("Data refreshing");
        actualizationInfo.setText("Aktualizuję dane...");
        actualizationInfo.setVisible(true);

        GetWeatherDataService getDataService = new GetWeatherDataService(weatherManager);
        getDataService.start();
        getDataService.setOnSucceeded(event -> {
            WeatherDataResult weatherDataResult = (WeatherDataResult) getDataService.getValue();

            switch (weatherDataResult) {
                case SUCCESS: {
                    System.out.println("Data refreshing done");

                    //getting date for info when last actualization was
                    Date date = new Date();


                    SimpleDateFormat formatterLong = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                    actualizationInfo.setText("Ostatnio zaktualizowano " + formatterLong.format(date));

                    //converting current weather json to object
                    Gson currentDataJSON = new Gson();
                    CurrentWeatherData currentWeatherData = currentDataJSON.fromJson(String.valueOf(weatherManager.currentData), CurrentWeatherData.class);
                    currentWeatherData.convertMainToObject();

                    //converting forecast weather json to object
                    Gson forecastData = new Gson();
                    ForecastWeatherData forecastWeatherData = forecastData.fromJson(String.valueOf(weatherManager.forecastData), ForecastWeatherData.class);
                    forecastWeatherData.convertListToArrayOfObjects();


                    currentLocalization.setText(currentWeatherData.getName());
                    actualTempLeft.setText(currentWeatherData.mainWeatherData.getTemp() + " " + (char) 176 + "C");
                    tempFeelLeft.setText("Odczuwalna: " + currentWeatherData.mainWeatherData.getFeels_like() + " " + (char) 176 + "C");
                    pressureLeft.setText("Ciśnienie: " + currentWeatherData.mainWeatherData.getPressure() + " hPa");
                    actualWeathCondLeft.setText(currentWeatherData.mainWeatherData.getDescription());

// next day data
                    SimpleDateFormat formatershort = new SimpleDateFormat("dd.MM.yy");

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, 1);
                    date = calendar.getTime();

                    date1Left.setText(formatershort.format(date));
                    temp1left.setText("dzień: " + forecastWeatherData.getForecast().get(0).getTemperatures().getDay() + " " + (char) 176 + "C");
                    temp1NightLeft.setText("noc: " + forecastWeatherData.getForecast().get(0).getTemperatures().getNight() + " " + (char) 176 + "C");
                    pressure1Left.setText("ciśnienie: " + forecastWeatherData.getForecast().get(0).getPressure() + " hPa");
                    hummidity1Left.setText("wilgotność: " + forecastWeatherData.getForecast().get(0).getHumidity() + " %");
                    hummidity1Left.setText("wilgotność: " + forecastWeatherData.getForecast().get(0).getHumidity() + " %");
                    description1Left.setText(forecastWeatherData.getForecast().get(0).getDescription().getDescription());

                    System.out.println(forecastWeatherData.getForecast().get(0).getDescription().getDescription());



                    break;

                }
                case FAILED:
                    System.out.println("Data refreshing failed");
                    actualizationInfo.setText("Aktualizacja danych nie powiodła się");
                    break;
            }
        });
    }
}

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
import pl.wojciechsiwek.model.WeatherData;

import java.text.SimpleDateFormat;
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
    void exitProgramAction() {
        System.out.println("Exit program action called");
        Stage stage = (Stage) actualWeathCondLeft.getScene().getWindow();
        viewFactory.closeStage(stage);

    }



    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
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

            switch (weatherDataResult){
                case SUCCESS:{
                    System.out.println("Data refreshing done");

                    //getting date for info when last actualization was
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                    actualizationInfo.setText("Ostatnio zaktualizowano " + formatter.format(date));

                    //converting json to object
                    Gson gson = new Gson();
                    WeatherData weatherData = gson.fromJson(String.valueOf(weatherManager.currentData), WeatherData.class);
                    weatherData.convertMainToObject();

                    System.out.println("Widocznośc: " + weatherData.getVisibility());
                    System.out.println("Lokalizacja: " + weatherData.getName());
                    System.out.println("Temperatura: " + weatherData.mainWeatherData.getTemp());
                    System.out.println("Temperatura odczuwalna: " + weatherData.mainWeatherData.getFeels_like());
                    System.out.println("Temperatura minimalna: " + weatherData.mainWeatherData.getTemp_min());
                    System.out.println("Temperatura maksymalna: " + weatherData.mainWeatherData.getTemp_max());
                    System.out.println("Wilgotność: " + weatherData.mainWeatherData.getHumidity());
                    System.out.println("Ciśnienie: " + weatherData.mainWeatherData.getPressure());
                    System.out.println("Opis: " + weatherData.mainWeatherData.getDescription());

                    currentLocalization.setText(weatherData.getName());
                    actualTempLeft.setText(String.valueOf(weatherData.mainWeatherData.getTemp()) + " " + (char)176 + "C");
                    tempFeelLeft.setText("Odczuwalna: " + String.valueOf(weatherData.mainWeatherData.getFeels_like()) + " " + (char)176 + "C");
                    pressureLeft.setText("Ciśnienie: " + String.valueOf(weatherData.mainWeatherData.getPressure()) + " hPa");
                    actualWeathCondLeft.setText(weatherData.mainWeatherData.getDescription());

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

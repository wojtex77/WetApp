package pl.wojciechsiwek.view;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
    private Label secondLocalization;

    @FXML
    private Label coordinatesLeft;

    @FXML
    private Label coordinatesRight;

    @FXML
    private Label forecastLabelLeft;

    @FXML
    private Label forecastLabelRight;

    @FXML
    private Label leftHeader;

    @FXML
    private Label actualTempLeft;

    @FXML
    private Label actualTempRight;

    @FXML
    private Label actualWeathCondLeft;

    @FXML
    private Label actualWeathCondRight;

    @FXML
    private MenuItem exitButton;

    @FXML
    private Label actualizationInfoLeft;

    @FXML
    private Label actualizationInfoRight;

    @FXML
    private Label tempFeelLeft;

    @FXML
    private Label tempFeelRight;

    @FXML
    private Label pressureLeft;

    @FXML
    private Label pressureRight;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextField localizationInputRight;

    @FXML
    private TextField localizationInputLeft;


    @FXML
    private Label date1Left, temp1left, temp1NightLeft, pressure1Left, hummidity1Left, description1Left;

    @FXML
    private Label date2Left, temp2left, temp2NightLeft, pressure2Left, hummidity2Left, description2Left;

    @FXML
    private Label date3Left, temp3left, temp3NightLeft, pressure3Left, hummidity3Left, description3Left;

    @FXML
    private Label date4Left, temp4left, temp4NightLeft, pressure4Left, hummidity4Left, description4Left;

    @FXML
    private Label date5Left, temp5left, temp5NightLeft, pressure5Left, hummidity5Left, description5Left;


    @FXML
    private Label date1Right, temp1Right, temp1NightRight, pressure1Right, hummidity1Right, description1Right;

    @FXML
    private Label date2Right, temp2Right, temp2NightRight, pressure2Right, hummidity2Right, description2Right;

    @FXML
    private Label date3Right, temp3Right, temp3NightRight, pressure3Right, hummidity3Right, description3Right;

    @FXML
    private Label date4Right, temp4Right, temp4NightRight, pressure4Right, hummidity4Right, description4Right;

    @FXML
    private Label date5Right, temp5Right, temp5NightRight, pressure5Right, hummidity5Right, description5Right;



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
        actualizationInfoLeft.setText("Aktualizuję dane...");
        actualizationInfoLeft.setVisible(true);

        GetWeatherDataService getDataService = new GetWeatherDataService(weatherManager,localizationInputLeft.getText(), localizationInputRight.getText());
        getDataService.start();
        getDataService.setOnSucceeded(event -> {
            WeatherDataResult weatherDataResult = (WeatherDataResult) getDataService.getValue();

            switch (weatherDataResult) {
                case SUCCESS: {
                    System.out.println("Data refreshing done");
                    this.updateData();


                    break;

                }
                case FAILED:
                    System.out.println("Data refreshing failed");
                    actualizationInfoLeft.setText("Aktualizacja danych nie powiodła się");
                    actualizationInfoRight.setText("Aktualizacja danych nie powiodła się");
                    break;
            }
        });
    }

    private void updateData(){
        //getting date for info when last actualization was
        Date date = new Date();
        SimpleDateFormat formatterLong = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        actualizationInfoLeft.setText("Ostatnio zaktualizowano " + formatterLong.format(date));
        actualizationInfoRight.setText("Ostatnio zaktualizowano " + formatterLong.format(date));

        //converting current weather json to object
        Gson currentDataJSON = new Gson();
        CurrentWeatherData currentWeatherDataLeft = currentDataJSON.fromJson(String.valueOf(weatherManager.currentDataLeft), CurrentWeatherData.class);
        CurrentWeatherData currentWeatherDataRight = currentDataJSON.fromJson(String.valueOf(weatherManager.currentDataRight), CurrentWeatherData.class);
        currentWeatherDataLeft.convertMainToObject();
        currentWeatherDataRight.convertMainToObject();

        //converting forecast weather json to object
        Gson forecastDataLeft = new Gson();
        ForecastWeatherData forecastWeatherDataLeft = forecastDataLeft.fromJson(String.valueOf(weatherManager.forecastDataLeft), ForecastWeatherData.class);

        Gson forecastDataRight = new Gson();
        ForecastWeatherData forecastWeatherDataRight = forecastDataRight.fromJson(String.valueOf(weatherManager.forecastDataRight), ForecastWeatherData.class);

        forecastWeatherDataLeft.convertListToArrayOfObjects();
        forecastWeatherDataLeft.convertCityToObject();
        forecastWeatherDataLeft.cityObject.convertCoordinatesToObject();

        forecastWeatherDataRight.convertListToArrayOfObjects();
        forecastWeatherDataRight.convertCityToObject();
        forecastWeatherDataRight.cityObject.convertCoordinatesToObject();

        //left side
        forecastLabelLeft.setVisible(true);
        coordinatesLeft.setText("Szerokość: " + forecastWeatherDataLeft.cityObject.coordinates.getLat() + "; Długość: " + forecastWeatherDataLeft.cityObject.coordinates.getLon());
        coordinatesLeft.setVisible(true);
        currentLocalization.setText(currentWeatherDataLeft.getName() + ", " + forecastWeatherDataLeft.getCityObject().getCountry());
        actualTempLeft.setText(currentWeatherDataLeft.mainWeatherData.getTemp() + " " + (char) 176 + "C");
        tempFeelLeft.setText("Odczuwalna: " + currentWeatherDataLeft.mainWeatherData.getFeels_like() + " " + (char) 176 + "C");
        pressureLeft.setText("Ciśnienie: " + currentWeatherDataLeft.mainWeatherData.getPressure() + " hPa");
        actualWeathCondLeft.setText(currentWeatherDataLeft.mainWeatherData.getDescription());

        //right side
        forecastLabelRight.setVisible(true);
        coordinatesRight.setText("Szerokość: " + forecastWeatherDataRight.cityObject.coordinates.getLat() + "; Długość: " + forecastWeatherDataRight.cityObject.coordinates.getLon());
        coordinatesRight.setVisible(true);
        secondLocalization.setText(currentWeatherDataRight.getName() + ", " + forecastWeatherDataRight.getCityObject().getCountry());
        actualTempRight.setText(currentWeatherDataRight.mainWeatherData.getTemp() + " " + (char) 176 + "C");
        tempFeelRight.setText("Odczuwalna: " + currentWeatherDataRight.mainWeatherData.getFeels_like() + " " + (char) 176 + "C");
        pressureRight.setText("Ciśnienie: " + currentWeatherDataRight.mainWeatherData.getPressure() + " hPa");
        actualWeathCondRight.setText(currentWeatherDataRight.mainWeatherData.getDescription());

// first day data
        SimpleDateFormat formatershort1 = new SimpleDateFormat("dd.MM.yy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date1Left.setText(formatershort1.format(date));
        temp1left.setText("dzień: " + forecastWeatherDataLeft.getForecast().get(0).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp1NightLeft.setText("noc: " + forecastWeatherDataLeft.getForecast().get(0).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure1Left.setText("ciśnienie: " + forecastWeatherDataLeft.getForecast().get(0).getPressure() + " hPa");
        hummidity1Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(0).getHumidity() + " %");
        hummidity1Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(0).getHumidity() + " %");
        description1Left.setText(forecastWeatherDataLeft.getForecast().get(0).getDescription().getDescription());


// second day data
        SimpleDateFormat formatershort2 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date2Left.setText(formatershort2.format(date));
        temp2left.setText("dzień: " + forecastWeatherDataLeft.getForecast().get(1).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp2NightLeft.setText("noc: " + forecastWeatherDataLeft.getForecast().get(1).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure2Left.setText("ciśnienie: " + forecastWeatherDataLeft.getForecast().get(1).getPressure() + " hPa");
        hummidity2Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(1).getHumidity() + " %");
        hummidity2Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(1).getHumidity() + " %");
        description2Left.setText(forecastWeatherDataLeft.getForecast().get(1).getDescription().getDescription());


// third day data
        SimpleDateFormat formatershort3 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date3Left.setText(formatershort3.format(date));
        temp3left.setText("dzień: " + forecastWeatherDataLeft.getForecast().get(2).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp3NightLeft.setText("noc: " + forecastWeatherDataLeft.getForecast().get(2).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure3Left.setText("ciśnienie: " + forecastWeatherDataLeft.getForecast().get(2).getPressure() + " hPa");
        hummidity3Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(2).getHumidity() + " %");
        hummidity3Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(2).getHumidity() + " %");
        description3Left.setText(forecastWeatherDataLeft.getForecast().get(2).getDescription().getDescription());


// fourth day data
        SimpleDateFormat formatershort4 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date4Left.setText(formatershort4.format(date));
        temp4left.setText("dzień: " + forecastWeatherDataLeft.getForecast().get(3).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp4NightLeft.setText("noc: " + forecastWeatherDataLeft.getForecast().get(3).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure4Left.setText("ciśnienie: " + forecastWeatherDataLeft.getForecast().get(3).getPressure() + " hPa");
        hummidity4Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(3).getHumidity() + " %");
        hummidity4Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(3).getHumidity() + " %");
        description4Left.setText(forecastWeatherDataLeft.getForecast().get(3).getDescription().getDescription());

// fifth day data
        SimpleDateFormat formatershort5 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date5Left.setText(formatershort5.format(date));
        temp5left.setText("dzień: " + forecastWeatherDataLeft.getForecast().get(4).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp5NightLeft.setText("noc: " + forecastWeatherDataLeft.getForecast().get(4).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure5Left.setText("ciśnienie: " + forecastWeatherDataLeft.getForecast().get(4).getPressure() + " hPa");
        hummidity5Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(4).getHumidity() + " %");
        hummidity5Left.setText("wilgotność: " + forecastWeatherDataLeft.getForecast().get(4).getHumidity() + " %");
        description5Left.setText(forecastWeatherDataLeft.getForecast().get(4).getDescription().getDescription());

    }
}

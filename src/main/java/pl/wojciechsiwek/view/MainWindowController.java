package pl.wojciechsiwek.view;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

// ...

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;
import pl.wojciechsiwek.controller.WeatherDataResult;
import pl.wojciechsiwek.controller.services.GetWeatherDataService;
import pl.wojciechsiwek.model.CurrentWeatherData;
import pl.wojciechsiwek.model.ForecastWeatherData;

import java.net.URISyntaxException;
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
    private MenuItem aboutProgramButton;


    @FXML
    private MenuItem aboutAuthorButton;



    @FXML
    private Label actualizationInfoLeft;

    @FXML
    private Label tempFeelLeft;

    @FXML
    private Label pressureLeft;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextField localizationInputRight;

    @FXML
    private TextField localizationInputLeft;


    //left side forecast
    @FXML
    private Parent firstLeft, secondLeft, thirdLeft, fourthLeft, fifthLeft; //embeddedElement

    @FXML
    private SingleDayController firstLeftController, secondLeftController, thirdLeftController, fourthLeftController, fifthLeftController;

    //right side location embedded
    @FXML
    private Parent rightLocation; //embeddedElement

    @FXML
    private SingleLocationController rightLocationController;



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
    void aboutProgramAction() {
        System.out.println("About program action called");

        viewFactory.showAboutProgramWindow();

    }


    @FXML
    void aboutAuthorAction() throws URISyntaxException, IOException {
        System.out.println("About author action called");


        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://www.wojciechsiwek.pl"));
        }

    }

    @FXML
    void refreshDataAction() {
        System.out.println("Data refreshing");

        if (checkInputFilling("left")) {
            GetWeatherDataService getDataServiceLeft = new GetWeatherDataService(weatherManager, localizationInputLeft.getText(), "left");
            getDataServiceLeft.start();
            actualizationInfoLeft.setText("Aktualizuję dane...");
            actualizationInfoLeft.setVisible(true);

            getDataServiceLeft.setOnSucceeded(event -> {
                WeatherDataResult weatherDataResult = (WeatherDataResult) getDataServiceLeft.getValue();

                switch (weatherDataResult) {
                    case SUCCESS: {
                        System.out.println("Data refreshing done");
                        this.updateDataLeft();
                        break;

                    }
                    case FAILED: {
                        System.out.println("Data refreshing failed");
                        actualizationInfoLeft.setText("Aktualizacja danych nie powiodła się");
                        break;
                    }
                    case FAILED_NO_LOCATION_FOUND: {
                        System.out.println("No location found");
                        actualizationInfoLeft.setText("Aktualizacja danych nie powiodła się - lokalizacja niepoprawna");
                        break;
                    }
                    case FAILED_BY_TOO_MANY_CONNECTIONS: {
                        System.out.println("Too many connections");
                        actualizationInfoLeft.setText("Aktualizacja danych nie powiodła się - zbyt wiele zapytań, odśwież za minutkę");
                        break;
                    }
                    case FAILED_WRONG_KEY: {
                        System.out.println("Incorrect API key");
                        actualizationInfoLeft.setText("Aktualizacja danych nie powiodła się - skontaktuj się z deweloperem");
                        break;
                    }
                }
            });
        } else {
            actualizationInfoLeft.setText("Pole miejscowości nie może być puste");
            actualizationInfoLeft.setVisible(true);
        }

        if (checkInputFilling("right")) {
            rightLocationController.updateWeather(weatherManager, localizationInputRight.getText(), "right");
        } else {
            rightLocationController.setActualizationInfo("Pole miejscowości nie może być puste");
        }

    }

    private boolean checkInputFilling(String whichInput) {
        if (whichInput.equals("left"))
            return !localizationInputLeft.getText().equals("");
        else if (whichInput.equals("right"))
            return !localizationInputRight.getText().equals("");
        else return false;
    }


    private void updateDataLeft() {
        //getting date for info when last actualization was
        Date date = new Date();
        SimpleDateFormat formatterLong = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        actualizationInfoLeft.setText("Ostatnio zaktualizowano " + formatterLong.format(date));

        //converting current weather json to object
        Gson currentDataJSON = new Gson();
        CurrentWeatherData currentWeatherDataLeft = currentDataJSON.fromJson(String.valueOf(weatherManager.getCurrentDataLeft()), CurrentWeatherData.class);
        currentWeatherDataLeft.convertMainToObject();

        //converting forecast weather json to object
        Gson forecastDataLeft = new Gson();
        ForecastWeatherData forecastWeatherDataLeft = forecastDataLeft.fromJson(String.valueOf(weatherManager.getForecastDataLeft()), ForecastWeatherData.class);

        forecastWeatherDataLeft.convertListToArrayOfObjects();
        forecastWeatherDataLeft.convertCityToObject();
        forecastWeatherDataLeft.cityObject.convertCoordinatesToObject();
//left side
        forecastLabelLeft.setVisible(true);
        coordinatesLeft.setText("Szerokość: " + forecastWeatherDataLeft.cityObject.coordinates.getLat() + "; Długość: " + forecastWeatherDataLeft.cityObject.coordinates.getLon());
        coordinatesLeft.setVisible(true);
        currentLocalization.setText(currentWeatherDataLeft.getName() + ", " + forecastWeatherDataLeft.getCityObject().getCountry());
        actualTempLeft.setText(currentWeatherDataLeft.mainWeatherData.getTemp() + " " + (char) 176 + "C");
        tempFeelLeft.setText("Odczuwalna: " + currentWeatherDataLeft.mainWeatherData.getFeels_like() + " " + (char) 176 + "C");
        pressureLeft.setText("Ciśnienie: " + currentWeatherDataLeft.mainWeatherData.getPressure() + " hPa");
        actualWeathCondLeft.setText(currentWeatherDataLeft.mainWeatherData.getDescription());


        int i =0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

// first day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        firstLeftController.updateData(date, forecastWeatherDataLeft,i);
        i++;

// second day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        secondLeftController.updateData(date, forecastWeatherDataLeft,i);
        i++;


// third day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        thirdLeftController.updateData(date, forecastWeatherDataLeft,i);
        i++;

// fourth day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        fourthLeftController.updateData(date, forecastWeatherDataLeft,i);
        i++;

// fifth day data
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        fifthLeftController.updateData(date, forecastWeatherDataLeft,i);
        i++;

    }
}

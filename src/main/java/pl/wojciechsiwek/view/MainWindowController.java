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
    private Parent firstLeft, secondLeft, thirdLeft, fourthLeft, fifthLeft; //embeddedElement

    @FXML
    private SingleDayController firstLeftController, secondLeftController, thirdLeftController, fourthLeftController, fifthLeftController;

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

    private boolean isTextLeft, isTextRight;

    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
        this.isTextLeft = false;
        this.isTextRight = false;
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

        this.checkInputFilling();

        if (this.isTextLeft) {
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

        if (this.isTextRight) {
            GetWeatherDataService getDataServiceRight = new GetWeatherDataService(weatherManager, localizationInputRight.getText(), "right");
            getDataServiceRight.start();
            actualizationInfoRight.setText("Aktualizuję dane...");
            actualizationInfoRight.setVisible(true);

            getDataServiceRight.setOnSucceeded(event -> {
                WeatherDataResult weatherDataResult = (WeatherDataResult) getDataServiceRight.getValue();

                switch (weatherDataResult) {
                    case SUCCESS: {
                        System.out.println("Data refreshing done");
                        this.updateDataRight();
                        break;

                    }
                    case FAILED: {
                        System.out.println("Data refreshing failed");
                        actualizationInfoRight.setText("Aktualizacja danych nie powiodła się");
                        break;
                    }
                    case FAILED_NO_LOCATION_FOUND: {
                        System.out.println("No location found");
                        actualizationInfoRight.setText("Aktualizacja danych nie powiodła się - lokalizacja niepoprawna");
                        break;
                    }
                    case FAILED_BY_TOO_MANY_CONNECTIONS: {
                        System.out.println("Too many connections");
                        actualizationInfoRight.setText("Aktualizacja danych nie powiodła się - zbyt wiele zapytań, odśwież za minutkę");
                        break;
                    }
                    case FAILED_WRONG_KEY: {
                        System.out.println("Incorrect API key");
                        actualizationInfoRight.setText("Aktualizacja danych nie powiodła się - skontaktuj się z deweloperem");
                        break;
                    }
                }
            });
        } else {
            actualizationInfoRight.setText("Pole miejscowości nie może być puste");
            actualizationInfoRight.setVisible(true);
        }

    }

    private void checkInputFilling() {

        this.isTextLeft = !localizationInputLeft.getText().equals("");

        this.isTextRight = !localizationInputRight.getText().equals("");
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

    private void updateDataRight() {
        //getting date for info when last actualization was
        Date date = new Date();
        SimpleDateFormat formatterLong = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        actualizationInfoRight.setText("Ostatnio zaktualizowano " + formatterLong.format(date));

        //converting current weather json to object
        Gson currentDataJSON = new Gson();
        CurrentWeatherData currentWeatherDataRight = currentDataJSON.fromJson(String.valueOf(weatherManager.getCurrentDataRight()), CurrentWeatherData.class);
        currentWeatherDataRight.convertMainToObject();

        //converting forecast weather json to object

        Gson forecastDataRight = new Gson();
        ForecastWeatherData forecastWeatherDataRight = forecastDataRight.fromJson(String.valueOf(weatherManager.getForecastDataRight()), ForecastWeatherData.class);
        forecastWeatherDataRight.convertListToArrayOfObjects();
        forecastWeatherDataRight.convertCityToObject();
        forecastWeatherDataRight.cityObject.convertCoordinatesToObject();

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

        date1Right.setText(formatershort1.format(date));
        temp1Right.setText("dzień: " + forecastWeatherDataRight.getForecast().get(0).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp1NightRight.setText("noc: " + forecastWeatherDataRight.getForecast().get(0).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure1Right.setText("ciśnienie: " + forecastWeatherDataRight.getForecast().get(0).getPressure() + " hPa");
        hummidity1Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(0).getHumidity() + " %");
        hummidity1Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(0).getHumidity() + " %");
        description1Right.setText(forecastWeatherDataRight.getForecast().get(0).getDescription().getDescription());


// second day data
        SimpleDateFormat formatershort2 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date2Right.setText(formatershort2.format(date));
        temp2Right.setText("dzień: " + forecastWeatherDataRight.getForecast().get(1).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp2NightRight.setText("noc: " + forecastWeatherDataRight.getForecast().get(1).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure2Right.setText("ciśnienie: " + forecastWeatherDataRight.getForecast().get(1).getPressure() + " hPa");
        hummidity2Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(1).getHumidity() + " %");
        hummidity2Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(1).getHumidity() + " %");
        description2Right.setText(forecastWeatherDataRight.getForecast().get(1).getDescription().getDescription());


// third day data
        SimpleDateFormat formatershort3 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date3Right.setText(formatershort3.format(date));
        temp3Right.setText("dzień: " + forecastWeatherDataRight.getForecast().get(2).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp3NightRight.setText("noc: " + forecastWeatherDataRight.getForecast().get(2).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure3Right.setText("ciśnienie: " + forecastWeatherDataRight.getForecast().get(2).getPressure() + " hPa");
        hummidity3Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(2).getHumidity() + " %");
        hummidity3Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(2).getHumidity() + " %");
        description3Right.setText(forecastWeatherDataRight.getForecast().get(2).getDescription().getDescription());


// fourth day data
        SimpleDateFormat formatershort4 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date4Right.setText(formatershort4.format(date));
        temp4Right.setText("dzień: " + forecastWeatherDataRight.getForecast().get(3).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp4NightRight.setText("noc: " + forecastWeatherDataRight.getForecast().get(3).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure4Right.setText("ciśnienie: " + forecastWeatherDataRight.getForecast().get(3).getPressure() + " hPa");
        hummidity4Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(3).getHumidity() + " %");
        hummidity4Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(3).getHumidity() + " %");
        description4Right.setText(forecastWeatherDataRight.getForecast().get(3).getDescription().getDescription());

// fifth day data
        SimpleDateFormat formatershort5 = new SimpleDateFormat("dd.MM.yy");

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();

        date5Right.setText(formatershort5.format(date));
        temp5Right.setText("dzień: " + forecastWeatherDataRight.getForecast().get(4).getTemperatures().getDay() + " " + (char) 176 + "C");
        temp5NightRight.setText("noc: " + forecastWeatherDataRight.getForecast().get(4).getTemperatures().getNight() + " " + (char) 176 + "C");
        pressure5Right.setText("ciśnienie: " + forecastWeatherDataRight.getForecast().get(4).getPressure() + " hPa");
        hummidity5Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(4).getHumidity() + " %");
        hummidity5Right.setText("wilgotność: " + forecastWeatherDataRight.getForecast().get(4).getHumidity() + " %");
        description5Right.setText(forecastWeatherDataRight.getForecast().get(4).getDescription().getDescription());

    }
}

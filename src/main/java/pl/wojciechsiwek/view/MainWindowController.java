package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainWindowController extends BaseController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button refreshButton;

    @FXML
    private MenuItem exitButton;

    @FXML
    private MenuItem aboutProgramButton;

    @FXML
    private MenuItem aboutAuthorButton;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextField localizationInputRight;

    @FXML
    private TextField localizationInputLeft;


    //right side location embedded
    @FXML
    private Parent rightLocation, leftLocation; //embeddedElement

    @FXML
    private SingleLocationController rightLocationController, leftLocationController;


    public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
        super(weatherManager, viewFactory, fxmlName);
    }


    @FXML
    void exitProgramAction() {
        System.out.println("Exit program action called");
        Stage stage = (Stage) menuBar.getScene().getWindow();
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
            leftLocationController.updateWeather(weatherManager, localizationInputLeft.getText(), "left");
        } else {
            leftLocationController.setActualizationInfo("Pole miejscowości nie może być puste");
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
}

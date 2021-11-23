package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;
import pl.wojciechsiwek.controller.WeatherDataResult;
import pl.wojciechsiwek.controller.services.GetDataService;

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
        actualizationInfo.setVisible(true);

        GetDataService getDataService = new GetDataService(weatherManager);
        getDataService.start();
        getDataService.setOnSucceeded(event -> {
            WeatherDataResult weatherDataResult = (WeatherDataResult) getDataService.getValue();

            switch (weatherDataResult){
                case SUCCESS:{
                    System.out.println("Data refreshing done");
                    actualizationInfo.setVisible(false);
                    break;

                }
                case FAILED:
                    break;
            }
        });
    }
}

package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;

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

        public MainWindowController(WeatherManager weatherManager, ViewFactory viewFactory, String fxmlName) {
                super(weatherManager, viewFactory, fxmlName);
        }

        @FXML
        void refreshDataAction() {}
}

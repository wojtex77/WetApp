package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;

public class MainWindowController {

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
        void refreshDataAction() {}
}

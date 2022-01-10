package pl.wojciechsiwek.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;

import java.io.IOException;
import java.util.Objects;

public class ViewFactory {
    private final WeatherManager weatherManager;

    public ViewFactory(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }


    public void showMainWindow() {
        System.out.println("Main window method called");
        BaseController mainWindowController = new MainWindowController(weatherManager, this, "/pl.wojciechsiwek/view/MainWindow.fxml");
        initializeStage(mainWindowController, "WetApp - aplikacja pogodowa", false);
    }

    public void showAboutProgramWindow() {
        System.out.println("About program method called");
        BaseController controller = new AboutController(weatherManager, this, "/pl.wojciechsiwek/view/About.fxml");
        initializeStage(controller, "WetApp - O programie", true);
    }


    private void initializeStage(BaseController controller, String windowTitle, Boolean modal) {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(controller.getFxmlName())));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        //setting logo
        String logoURL = Objects.requireNonNull(getClass().getResource("/pl.wojciechsiwek/img/weather.png")).toString();
        Image image = new Image(logoURL);
        stage.getIcons().add(image);
        stage.setResizable(true);
        if (modal) {
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else stage.show();
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }
}

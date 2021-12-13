package pl.wojciechsiwek.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.wojciechsiwek.WeatherManager;
import pl.wojciechsiwek.controller.BaseController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ViewFactory {
    private final WeatherManager weatherManager;

    public ViewFactory(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }


    public void showMainWindow() {
        System.out.println("Main window method called");

        BaseController mainWindowController = new MainWindowController(weatherManager, this, "/view/MainWindow.fxml");
        initializeStage(mainWindowController);


    }

    public void showAboutProgramWindow() {
        System.out.println("About program method called");

        BaseController controller = new AboutController(weatherManager, this, "/view/About.fxml");
        initializeStage(controller);


    }


    private void initializeStage(BaseController controller) {

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
        stage.setTitle("WetApp");

        //setting logo
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String pathToImg = s + "/src/main/resources/img/weather.png";
        stage.getIcons().add(new Image("file:///" + pathToImg));


        stage.setResizable(true);
        stage.show();

    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }
}

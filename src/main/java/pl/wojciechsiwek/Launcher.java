package pl.wojciechsiwek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainWindow.fxml")));

        Scene scene = new Scene(parent);


        stage.setScene(scene);
        stage.setTitle("WetApp");

        //setting logo
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String pathToImg = s + "/src/main/resources/img/weather.png";
        stage.getIcons().add(new Image( "file:///" + pathToImg));

        stage.setResizable(false);
        stage.show();
    }
}

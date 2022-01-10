package pl.wojciechsiwek.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.wojciechsiwek.model.ForecastData;

public class SingleDayController {

    @FXML
    private Label temp;

    @FXML
    private Label tempNight;

    @FXML
    private Label pressure;

    @FXML
    private Label hummidity;

    @FXML
    private Label description;

    @FXML
    private Label date;

    public void updateData(ForecastData data) {

        date.setText(data.getDate());
        temp.setText("dzień: " + data.getTempDay() + " " + (char) 176 + "C");
        tempNight.setText("noc: " + data.getTempNight() + " " + (char) 176 + "C");
        pressure.setText("ciśnienie: " + data.getPressure() + " hPa");
        hummidity.setText("wilgotność: " + data.getHummidity() + " %");
        description.setText(data.getDescription());

        setDataVisibility(true);

    }

    private void setDataVisibility(boolean isVisible) {
        date.setVisible(isVisible);
        temp.setVisible(isVisible);
        tempNight.setVisible(isVisible);
        pressure.setVisible(isVisible);
        hummidity.setVisible(isVisible);
        description.setVisible(isVisible);
    }

}



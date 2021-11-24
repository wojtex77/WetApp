package pl.wojciechsiwek.model;

public class MainWeatherData {
    private float temp;
    private float temp_min;

    public float getTemp_min() {
        return temp_min;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public float getTemp_max() {
        return temp_max;
    }

    private float humidity;
    private float pressure;
    private float feels_like;
    private float temp_max;

    public float getTemp() {
        return temp;
    }
}

package pl.wojciechsiwek.model;

public class CurrentWeatherValues {
    private float temp;
    private float temp_min;
    private float humidity;
    private float pressure;
    private float feels_like;
    private float temp_max;
    private String description;
    private String icon;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

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


    public float getTemp() {
        return temp;
    }
}

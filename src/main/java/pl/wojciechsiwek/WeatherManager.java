package pl.wojciechsiwek;

import com.mashape.unirest.http.JsonNode;

public class WeatherManager {

    private JsonNode currentDataLeft = null;
    private JsonNode forecastDataLeft = null;
    private JsonNode currentDataRight = null;
    private JsonNode forecastDataRight = null;

    public JsonNode getCurrentDataLeft() {
        return currentDataLeft;
    }

    public JsonNode getForecastDataLeft() {
        return forecastDataLeft;
    }

    public JsonNode getCurrentDataRight() {
        return currentDataRight;
    }

    public JsonNode getForecastDataRight() {
        return forecastDataRight;
    }

    public void setCurrentDataLeft(JsonNode currentDataLeft) {
        this.currentDataLeft = currentDataLeft;
    }

    public void setForecastDataLeft(JsonNode forecastDataLeft) {
        this.forecastDataLeft = forecastDataLeft;
    }

    public void setCurrentDataRight(JsonNode currentDataRight) {
        this.currentDataRight = currentDataRight;
    }

    public void setForecastDataRight(JsonNode forecastDataRight) {
        this.forecastDataRight = forecastDataRight;
    }
}

package pl.wojciechsiwek;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.wojciechsiwek.model.CurrentData;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherManager {

    private JsonNode currentDataLeft = null;
    private JsonNode forecastDataLeft = null;
    private JsonNode currentDataRight = null;
    private JsonNode forecastDataRight = null;
    private CurrentData currentDataObjectLeft;

    public CurrentData getCurrentDataObjectLeft() {
        return currentDataObjectLeft;
    }

    public JsonNode getCurrentDataLeft() {
        return currentDataLeft;
    }

    public void setCurrentDataLeft(JsonNode currentDataLeft) {
        this.currentDataLeft = currentDataLeft;
    }

    public JsonNode getForecastDataLeft() {
        return forecastDataLeft;
    }

    public void setForecastDataLeft(JsonNode forecastDataLeft) {
        this.forecastDataLeft = forecastDataLeft;
    }

    public JsonNode getCurrentDataRight() {
        return currentDataRight;
    }

    public void setCurrentDataRight(JsonNode currentDataRight) {
        this.currentDataRight = currentDataRight;
    }

    public JsonNode getForecastDataRight() {
        return forecastDataRight;
    }

    public void setForecastDataRight(JsonNode forecastDataRight) {
        this.forecastDataRight = forecastDataRight;
    }

    public void convertCurrentToObject(String whichPane) throws JsonProcessingException {
        CurrentData currentData = new CurrentData();

        JSONObject main = currentDataLeft.getObject().getJSONObject("main");
        JSONObject sys = currentDataLeft.getObject().getJSONObject("sys");
        JSONObject coord = currentDataLeft.getObject().getJSONObject("coord");
        JSONArray weather = currentDataLeft.getObject().getJSONArray("weather");
        ArrayList myArrayList = (ArrayList) weather.toList();
        HashMap<String, Integer> desc = (HashMap) myArrayList.get(0);

        currentData.setTemperature(main.getDouble("temp"));
        currentData.setFeelsLike(main.getDouble("feels_like"));
        currentData.setPressure(main.getInt("pressure"));
        currentData.setCity(currentDataLeft.getObject().getString("name"));
        currentData.setCountry(sys.getString("country"));
        currentData.setLatitude(coord.getDouble("lat"));
        currentData.setLongtitude(coord.getDouble("lon"));
        currentData.setDescription(String.valueOf(desc.get("description")));

        currentDataObjectLeft = currentData;
    }
}

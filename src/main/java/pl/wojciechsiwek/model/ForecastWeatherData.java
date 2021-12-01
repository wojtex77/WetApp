package pl.wojciechsiwek.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.JsonNode;

public class ForecastWeatherData {
    private JsonObject city;
    private JsonArray list; //contains list of weather forecast for next days
    private int cnt;

    public JsonObject getCity() {
        return city;
    }

    public JsonArray getList() {
        return list;
    }
}

package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ForecastWeatherData {
    private final ArrayList<ForecastWeatherValues> forecast = new ArrayList<ForecastWeatherValues>();
    public City cityObject;
    private JsonObject city;
    private JsonArray list; //contains list of weather forecast for next days
    private int cnt;

    public int getCnt() {
        return cnt;
    }

    public ArrayList<ForecastWeatherValues> getForecast() {
        return forecast;
    }

    public JsonObject getCity() {
        return city;
    }

    public JsonArray getList() {
        return list;
    }

    public City getCityObject() {
        return cityObject;
    }

    public void convertListToArrayOfObjects() {

        for (int i = 0; i < this.cnt; i++) {
            Gson gson = new Gson();
            ForecastWeatherValues object = gson.fromJson(list.get(i), ForecastWeatherValues.class);
            object.convertTemperatures();
            object.convertWeatherToObject();
            forecast.add(object);
        }

    }


    public void convertCityToObject() {

        Gson gson = new Gson();
        cityObject = gson.fromJson(this.city, City.class);

    }

}

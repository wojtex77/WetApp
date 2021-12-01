package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class ForecastWeatherData {
    private JsonObject city;
    private JsonArray list; //contains list of weather forecast for next days
    private int cnt;

    private ArrayList<ForecastWeatherValues> forecast = new ArrayList<ForecastWeatherValues>();

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



    public void convertListToArrayOfObjects(){

        for (int i=0 ; i < this.cnt; i++){
            Gson gson = new Gson();
            ForecastWeatherValues object = gson.fromJson(list.get(i), ForecastWeatherValues.class);
            object.convertTemperatures();
            object.convertWeatherToObject();
            forecast.add(object);
        }

    }

}

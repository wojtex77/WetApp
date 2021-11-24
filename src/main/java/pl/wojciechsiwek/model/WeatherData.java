package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WeatherData {

    private int visibility;
    private String name;

    public JsonObject main;
    public MainWeatherData mainWeatherData;

    public String getName() {
        return name;
    }

    public int getVisibility() {
        return visibility;
    }


    public void convertMainToObject(){
        Gson gson = new Gson();
        mainWeatherData = gson.fromJson(String.valueOf(this.main), MainWeatherData.class);

    }

}

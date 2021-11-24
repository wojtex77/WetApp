package pl.wojciechsiwek.model;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.mashape.unirest.http.JsonNode;

import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherData {

    private int visibility;
    private String name;

    public JsonObject main;
    public ArrayList weather;
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
        Object obj = weather.get(0);
        String test = obj.toString();

        final String regex = "description=\\s*([^,\\r]*)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(test);

        if (matcher.find()){
            mainWeatherData.setDescription(matcher.group(1));
        }
    }
}

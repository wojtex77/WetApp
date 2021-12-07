package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrentWeatherData {

    public JsonObject main;
    public ArrayList weather;
    public CurrentWeatherValues mainWeatherData;
    private int visibility;
    private String name;

    public String getName() {
        return name;
    }

    public int getVisibility() {
        return visibility;
    }


    public void convertMainToObject() {
        Gson gson = new Gson();
        mainWeatherData = gson.fromJson(String.valueOf(this.main), CurrentWeatherValues.class);
        Object obj = weather.get(0);
        String test = obj.toString();

        final String regex = "description=\\s*([^,\\r]*)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(test);

        if (matcher.find()) {
            mainWeatherData.setDescription(matcher.group(1));
        }
    }
}

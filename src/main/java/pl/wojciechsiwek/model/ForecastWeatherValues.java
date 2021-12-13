package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ForecastWeatherValues {
    private final int pressure;
    private final int humidity;
    private JsonObject temp;
    private JsonArray weather; //contains list of weather forecast for next days
    private ForecastWeatherDescription description;
    private ForecastWeatherTemp temperatures;


    public ForecastWeatherValues(int pressure, int humidity) {
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public ForecastWeatherDescription getDescription() {
        return description;
    }

    public ForecastWeatherTemp getTemperatures() {
        return temperatures;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void convertTemperatures() {
        Gson gson = new Gson();
        temperatures = gson.fromJson(temp, ForecastWeatherTemp.class);
    }

    public void convertWeatherToObject() {
        Gson gson = new Gson();
        description = gson.fromJson(weather.get(0), ForecastWeatherDescription.class);

    }
}

package pl.wojciechsiwek.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ForecastWeatherValues {
    private int pressure;
    private int humidity;
    private JsonObject temp;

    private ForecastWeatherTemp temperatures;

    public ForecastWeatherTemp getTemperatures() {
        return temperatures;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }


    public ForecastWeatherValues(int pressure, int humidity) {
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public void convertTemperatures(){
        Gson gson = new Gson();
        temperatures = gson.fromJson(temp, ForecastWeatherTemp.class);
    }
}

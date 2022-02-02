package pl.wojciechsiwek;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.wojciechsiwek.model.CurrentData;
import pl.wojciechsiwek.model.ForecastData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherManager {

    private JsonNode currentDataLeft;
    private JsonNode forecastDataLeft;
    private JsonNode currentDataRight;
    private JsonNode forecastDataRight;
    private CurrentData currentDataObjectLeft;
    private CurrentData currentDataObjectRight;
    private ArrayList<ForecastData> forecastDataArrayLeft;
    private ArrayList<ForecastData> forecastDataArrayRight;

    public WeatherManager() {
        this.currentDataLeft = null;
        this.forecastDataLeft = null;
        this.currentDataRight = null;
        this.forecastDataRight = null;
        this.currentDataObjectLeft = null;
        this.currentDataObjectRight = null;
        this.forecastDataArrayLeft = null;
        this.forecastDataArrayRight = null;
    }

    public ArrayList<ForecastData> getForecastDataArrayLeft() {
        return forecastDataArrayLeft;
    }

    public ArrayList<ForecastData> getForecastDataArrayRight() {
        return forecastDataArrayRight;
    }

    public CurrentData getCurrentDataObjectLeft() {
        return currentDataObjectLeft;
    }

    public CurrentData getCurrentDataObjectRight() {
        return currentDataObjectRight;
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

    public void convertCurrentToObject(String whichPane) {
        CurrentData currentData = new CurrentData();
        JsonNode data;
        if (whichPane.equals("left")) {
            data = currentDataLeft;
        } else {
            data = currentDataRight;
        }

        JSONObject main = data.getObject().getJSONObject("main");
        JSONObject sys = data.getObject().getJSONObject("sys");
        JSONObject coord = data.getObject().getJSONObject("coord");
        JSONArray weather = data.getObject().getJSONArray("weather");
        ArrayList<Object> myArrayList = (ArrayList) weather.toList();
        HashMap<String, Integer> desc = (HashMap<String, Integer>) myArrayList.get(0);

        currentData.setTemperature(main.getDouble("temp"));
        currentData.setFeelsLike(main.getDouble("feels_like"));
        currentData.setPressure(main.getInt("pressure"));
        currentData.setCity(data.getObject().getString("name"));
        currentData.setCountry(sys.getString("country"));
        currentData.setLatitude(coord.getDouble("lat"));
        currentData.setLongtitude(coord.getDouble("lon"));
        currentData.setDescription(String.valueOf(desc.get("description")));

        if (whichPane.equals("left")) {
            currentDataObjectLeft = currentData;
        } else {
            currentDataObjectRight = currentData;
        }
    }

    public void convertForecastToObject(String whichPane) {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ArrayList<ForecastData> forecastData = new ArrayList<>();
        JSONArray daily;
        if (whichPane.equals("left")) {
            daily = forecastDataLeft.getObject().getJSONArray("daily");
        } else {
            daily = forecastDataRight.getObject().getJSONArray("daily");
        }
        ArrayList<Object> myArrayList = (ArrayList) daily.toList();
        HashMap<String, HashMap<String, Double>> dailyMapHashes;
        HashMap<String, Integer> dailyMapIntegers;
        HashMap temperatures;
        HashMap<String, Integer> desc;

        final String regex = "description=\\s*([^,\\r]*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String description;

        for (int i = 0; i < 5; i++) {
            dailyMapHashes = (HashMap<String, HashMap<String, Double>>) myArrayList.get(i);
            dailyMapIntegers = (HashMap<String, Integer>) myArrayList.get(i);
            temperatures = dailyMapHashes.get("temp");

            desc = (HashMap) myArrayList.get(i);
            description = String.valueOf(desc.get("weather"));

            matcher = pattern.matcher(description);


            ForecastData singleDayForecastData = new ForecastData();

            String dayTempByString = temperatures.get("day").toString();
            String nightTempByString = temperatures.get("night").toString();
            singleDayForecastData.setTempDay(Double.parseDouble(dayTempByString));
            singleDayForecastData.setTempNight(Double.parseDouble(nightTempByString));
            singleDayForecastData.setPressure(dailyMapIntegers.get("pressure"));
            singleDayForecastData.setHummidity(dailyMapIntegers.get("humidity"));
            if (matcher.find()) {
                singleDayForecastData.setDescription(matcher.group(1));
            }
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();

            singleDayForecastData.setDate(formater.format(date));
            forecastData.add(singleDayForecastData);
        }

        if (whichPane.equals("left")) {
            forecastDataArrayLeft = forecastData;
        } else {
            forecastDataArrayRight = forecastData;
        }

    }

}

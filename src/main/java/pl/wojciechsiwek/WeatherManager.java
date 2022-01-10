package pl.wojciechsiwek;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private JsonNode currentDataLeft = null;
    private JsonNode forecastDataLeft = null;
    private JsonNode currentDataRight = null;
    private JsonNode forecastDataRight = null;
    private CurrentData currentDataObjectLeft;
    private CurrentData currentDataObjectRight;
    private ArrayList<ForecastData> forecastDataArrayLeft;
    private ArrayList<ForecastData> forecastDataArrayRight;

    public ArrayList<ForecastData> getForecastDataArrayLeft() {
        return forecastDataArrayLeft;
    }

    public ArrayList<ForecastData> getForecastDataArrayRight() {
        return forecastDataArrayRight;
    }

    public CurrentData getCurrentDataObjectLeft() {
        return currentDataObjectLeft;
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

    public void convertCurrentToObject(String whichPane) throws JsonProcessingException {
        CurrentData currentData = new CurrentData();
        JsonNode data;
        if (whichPane.equals("left")){
            data = currentDataLeft;
        }
        else{
            data = currentDataRight;
        }

        JSONObject main = data.getObject().getJSONObject("main");
        JSONObject sys = data.getObject().getJSONObject("sys");
        JSONObject coord = data.getObject().getJSONObject("coord");
        JSONArray weather = data.getObject().getJSONArray("weather");
        ArrayList myArrayList = (ArrayList) weather.toList();
        HashMap<String, Integer> desc = (HashMap) myArrayList.get(0);

        currentData.setTemperature(main.getDouble("temp"));
        currentData.setFeelsLike(main.getDouble("feels_like"));
        currentData.setPressure(main.getInt("pressure"));
        currentData.setCity(data.getObject().getString("name"));
        currentData.setCountry(sys.getString("country"));
        currentData.setLatitude(coord.getDouble("lat"));
        currentData.setLongtitude(coord.getDouble("lon"));
        currentData.setDescription(String.valueOf(desc.get("description")));

        if (whichPane.equals("left")){
            currentDataObjectLeft = currentData;
        }
        else {
            currentDataObjectRight = currentData;
        }
    }

    public void convertForecastToObject(String whichPane) throws JsonProcessingException {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ArrayList<ForecastData> forecastData = new ArrayList<>();
        JSONArray daily;
        if (whichPane.equals("left")){
            daily = forecastDataLeft.getObject().getJSONArray("daily");
        }
        else {
            daily = forecastDataRight.getObject().getJSONArray("daily");
        }
        ArrayList myArrayList = (ArrayList) daily.toList();
        HashMap<String, HashMap> dailyMapHashes;
        HashMap<String, Integer> dailyMapIntegers;
        HashMap<String, Double> temperatures;
        HashMap<String, Integer> desc;

        final String regex = "description=\\s*([^,\\r]*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String description;

        for (int i = 0; i < 5; i++) {
            dailyMapHashes = (HashMap) myArrayList.get(i);
            dailyMapIntegers = (HashMap) myArrayList.get(i);
            temperatures = dailyMapHashes.get("temp");

            desc = (HashMap) myArrayList.get(i);
            description = String.valueOf(desc.get("weather"));

            matcher = pattern.matcher(description);


            ForecastData singleDayForecastData = new ForecastData();

            singleDayForecastData.setTempDay(temperatures.get("day"));
            singleDayForecastData.setTempNight(temperatures.get("night"));
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

        if (whichPane.equals("left")){
            forecastDataArrayLeft = forecastData;
        }
        else{
            forecastDataArrayRight = forecastData;
        }

    }

    public CurrentData getCurrentDataObjectRight() {
        return currentDataObjectRight;
    }
}

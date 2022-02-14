package pl.wojciechsiwek;

import com.mashape.unirest.http.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class WeatherManagerTest {

    private static JsonNode currentData;
    private static WeatherManager weatherManager;
    private static JsonNode forecastData;

    @BeforeAll
    static void readCurrentWeatherDataFile() throws IOException {
        weatherManager = new WeatherManager();
        currentData = new JsonNode(new String(Objects.requireNonNull(WeatherManagerTest.class.getResourceAsStream("/currentWeather.json")).readAllBytes(), StandardCharsets.UTF_8));
        forecastData = new JsonNode(new String(Objects.requireNonNull(WeatherManagerTest.class.getResourceAsStream("/forecastWeather.json")).readAllBytes(), StandardCharsets.UTF_8));
    }

    @Test
    void shouldConvertJSONToProperCurrentWeatherObject() {
        //given
        weatherManager.setCurrentDataRight(currentData);

        //when
        weatherManager.convertCurrentToObject("right");

        //then
        assertThat(weatherManager.getCurrentDataObjectRight(), notNullValue());
        assertThat(weatherManager.getCurrentDataObjectLeft(), nullValue());
    }

    @Test
    void shouldSetProperCurrentWeatherData() {
        //given
        weatherManager.setCurrentDataRight(currentData);

        //when
        weatherManager.convertCurrentToObject("right");

        //then
        assertThat(weatherManager.getCurrentDataObjectRight().getTemperature(), equalTo(-1.75));
        assertThat(weatherManager.getCurrentDataObjectRight().getPressure(), equalTo(1001));
        assertThat(weatherManager.getCurrentDataObjectRight().getFeelsLike(), equalTo(-3.47));
        assertThat(weatherManager.getCurrentDataObjectRight().getLongtitude(), equalTo(21.0118));
        assertThat(weatherManager.getCurrentDataObjectRight().getLatitude(), equalTo(52.2298));
        assertThat(weatherManager.getCurrentDataObjectRight().getCity(), equalTo("Warszawa"));
        assertThat(weatherManager.getCurrentDataObjectRight().getCountry(), equalTo("PL"));
        assertThat(weatherManager.getCurrentDataObjectRight().getDescription(), equalTo("zachmurzenie umiarkowane"));

    }


    @Test
    void shouldConvertJSONToProperForecastWeatherObject() {
        //given
        weatherManager.setForecastDataRight(forecastData);

        //when
        weatherManager.convertForecastToObject("right");

        //then
        assertThat(weatherManager.getForecastDataArrayRight(), notNullValue());
        assertThat(weatherManager.getCurrentDataObjectLeft(), nullValue());
    }

    @Test
    void shouldSetProperForecastWeatherData() {
        //given
        weatherManager.setForecastDataRight(forecastData);

        //when
        weatherManager.convertForecastToObject("right");

        //then

        assertThat(weatherManager.getForecastDataArrayRight().get(2).getTempDay(), equalTo(1.47));
        assertThat(weatherManager.getForecastDataArrayRight().get(2).getTempNight(), equalTo(0.37));
        assertThat(weatherManager.getForecastDataArrayRight().get(2).getPressure(), equalTo(994));
        assertThat(weatherManager.getForecastDataArrayRight().get(2).getHummidity(), equalTo(97));
        assertThat(weatherManager.getForecastDataArrayRight().get(2).getDescription(), equalTo("śnieg z deszczem"));

    }



}
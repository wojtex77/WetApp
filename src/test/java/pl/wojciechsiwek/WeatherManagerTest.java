package pl.wojciechsiwek;

import com.mashape.unirest.http.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class WeatherManagerTest {

    private static JsonNode currentData;
    private static WeatherManager weatherManager;
    private static JsonNode forecastData;

    @BeforeAll
    static void readCurrentWeatherDataFile() throws IOException {
        weatherManager = new WeatherManager();
        currentData = new JsonNode(Files.readString(Path.of("src/test/resources/currentWeather.json")));
        forecastData = new JsonNode(Files.readString(Path.of("src/test/resources/forecastWeather.json")));
    }

    @Test
    void shouldConvertJSONToProperCurrentWeatherObject() throws IOException {
        //given
        weatherManager.setCurrentDataRight(currentData);

        //when
        weatherManager.convertCurrentToObject("right");

        //then
        assertThat(weatherManager.getCurrentDataObjectRight(), notNullValue());
        assertThat(weatherManager.getCurrentDataObjectLeft(), nullValue());
    }

    @Test
    void shouldSetProperCurrentWeatherData() throws IOException {
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
}
package pl.wojciechsiwek.controller.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class WeatherDataServiceTest {

    @Test
    public void weatherDataResultShouldSuccessWhenStatus200(){
        //given

        HttpResponse<JsonNode> currentWeatherResponse = spy(HttpResponse.class);
        given(currentWeatherResponse.getStatus()).willReturn(200);
        //when


        //then
    }

}
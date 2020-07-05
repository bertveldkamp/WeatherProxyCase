package com.capgemini.logius.weatherproxy.client;

import com.capgemini.logius.weatherproxy.client.response.WeatherResponse;
import com.capgemini.logius.weatherproxy.model.City;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class WeatherClientTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WeatherClient weatherClient;
    private MockRestServiceServer mockRestServiceServer;
    private ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getWeatherInCityNotFound() {
      String expectedUri = "http://api.openweathermap.org/data/2.5/weather?q=ABC&appid=e617cfacae8e5b0dfc77a7b8044c3d1d";
        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(expectedUri))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON));
        assertNull(weatherClient.getWeatherInCity("ABC"));
    }

    @Test
    void getWeatherInCityInternalServerError() {
        String expectedUri = "http://api.openweathermap.org/data/2.5/weather?q=ABC&appid=e617cfacae8e5b0dfc77a7b8044c3d1d";
        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(expectedUri))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON));
        assertNull(weatherClient.getWeatherInCity("ABC"));
    }

    @Test
    void getWeatherInCityOk() throws JsonProcessingException {
        String expectedUri = "http://api.openweathermap.org/data/2.5/weather?q=Utrecht&appid=e617cfacae8e5b0dfc77a7b8044c3d1d";
        WeatherResponse weatherResponse = createResponse(1, "Utrecht", "Description of Utrecht"
                , 1.5, 20.0, 123456, 654321);

        mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(expectedUri))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(weatherResponse)));

        City response = weatherClient.getWeatherInCity("Utrecht");

        assertEquals(1, response.getId());
        assertEquals("Utrecht", response.getName());
        assertEquals("Description of Utrecht", response.getDescription());
        assertEquals(1.5, response.getTempMin());
        assertEquals(20.0, response.getTempMax());
        assertEquals(123456, response.getSunrise());
        assertEquals(654321, response.getSunset());
    }

    private WeatherResponse createResponse(int id, String name, String description, double tempMin, double tempMax
            ,int sunrise, int sunset) {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setId(id);
        weatherResponse.setName(name);
        Map<String, Object> weather = new HashMap<>();
        weather.put("description", description);
        weatherResponse.setWeather(Arrays.asList(weather));
        Map<String, Object> main = new HashMap<>();
        main.put("temp_min", tempMin);
        main.put("temp_max", tempMax);
        weatherResponse.setMain(main);
        Map<String, Object> sys = new HashMap<>();
        sys.put("sunrise", sunrise);
        sys.put("sunset", sunset);
        weatherResponse.setSys(sys);
        return weatherResponse;
    }

}
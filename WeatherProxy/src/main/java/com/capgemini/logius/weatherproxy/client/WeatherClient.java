package com.capgemini.logius.weatherproxy.client;

import com.capgemini.logius.weatherproxy.client.response.WeatherResponse;
import com.capgemini.logius.weatherproxy.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

public class WeatherClient {
    @Value("${weatherproxy.apikey}")
    private String WEATHER_API_KEY;
    @Value("${weatherproxy.endpoint}")
    private String WEATHER_API_URI;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public City getWeatherInCity(String cityName) {
        try {
            String uri = UriComponentsBuilder.fromHttpUrl(WEATHER_API_URI)
                    .queryParam("q", cityName)
                    .queryParam("appid", WEATHER_API_KEY).toUriString();
            WeatherResponse result = restTemplate.getForObject(uri, WeatherResponse.class);
            if(result == null) {
                return null;
            }
            return result.convertToCity();
        }
        catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException ignored){
            return null;
        }
    }
}

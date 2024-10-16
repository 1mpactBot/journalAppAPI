package dev.saransh.journalApp.service;

import dev.saransh.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}") // Reading from application.properties
    private String apiKey;
    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalApi = API.replace("API_KEY", apiKey).replace("CITY", city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}

package com.denison.project.transportation_recommendation.service;


import com.denison.project.transportation_recommendation.model.CurrentWeatherParameter;
import com.denison.project.transportation_recommendation.model.CurrentWeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    @Autowired
    private HTTPService httpService;

    @Value("${spring.application.azureCurrentWeatherURI}")
    private String currentWeatherURL;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<CurrentWeatherResponse> getCurrentWeather(String lat, String lon) throws IOException, NoSuchFieldException, InterruptedException {
        HttpResponse<String> currentWeather = httpService.apiCall(currentWeatherURL,"GET", new CurrentWeatherParameter(lat,lon),"");
        List<CurrentWeatherResponse>res = new ArrayList<>();
        if (currentWeather.statusCode()==200){
            JsonNode weatherRes = objectMapper.readTree(currentWeather.body()).get("results");
            for (JsonNode weather: weatherRes){
                res.add(CurrentWeatherResponse.builder()
                                .temperature(String.format("%s%s",weather.get("temperature").get("value").toString(),"ºC"))
                                .feelTemperature(String.format("%s%s",weather.get("realFeelTemperature").get("value").toString(),"ºC"))
                                .summary(weather.get("phrase").toString())
                                .humidity(String.format("%s%s",weather.get("relativeHumidity").toString(), "%"))
                                .wind(String.format("%s %s",weather.get("wind").get("speed").get("value").toString(), "km/h"))
                                .build());
            }
            return res;
        }
        return null;
    }
}

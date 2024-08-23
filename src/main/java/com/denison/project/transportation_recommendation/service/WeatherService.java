package com.denison.project.transportation_recommendation.service;


import com.denison.project.transportation_recommendation.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

@Slf4j
@Service
public class WeatherService {
    @Autowired
    private HTTPService httpService;

    @Value("${spring.application.azureCurrentWeatherURI}")
    private String currentWeatherURL;

    @Value("${spring.application.azureForecastWeatherURI}")
    private String forecastWeatherURL;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.application.azureWeatherIcon}")
    private String validWeatherIcon;

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

    public ForecastWeatherResponse getForecastWeather(String lat, String lon, String interval) throws IOException, NoSuchFieldException, InterruptedException {
        HttpResponse<String> res = httpService.apiCall(forecastWeatherURL,"GET", new ForecastWeatherParameter(lat,lon,interval),"");
        JsonNode forecast = objectMapper.readTree(res.body());
        //create a map of valid weather icon
        Map<String,String>validWeatherIcons = buildValidWeatherIcons();
        //validate weatherIcon on response
        if (validWeatherIcons.containsKey(forecast.get("summary").get("iconCode").toString())){
            //intervals
            JsonNode intervalsWeather = forecast.get("intervals");
            List<WeatherInterval> intervals = new ArrayList<>();
            for (JsonNode weather: intervalsWeather){
                if (!Objects.equals(weather.get("dbz").toString(), Double.toString(0.0))
                        && validWeatherIcons.containsKey(weather.get("iconCode").toString())){
                    intervals.add(WeatherInterval.builder()
                                    .time(weather.get("startTime").toString())
                                    .phrase(weather.get("shortPhrase").toString())
                                    .iconCode(weather.get("iconCode").toString())
                                    .weatherByIcon(validWeatherIcons.get(weather.get("iconCode").toString()))
                                    .build());
                }
            }
            System.out.println("Intervals Weather if there is any precipitation "+intervals);
            return ForecastWeatherResponse.builder()
                    .summary(forecast.get("summary").get("longPhrase").toString())
                    .iconCode(forecast.get("summary").get("iconCode").toString())
                    .currentWeather(validWeatherIcons.get(forecast.get("summary").get("iconCode").toString()))
                    .build();
        }
        else{
            log.error("iconCode is invalid");
            return null;
        }
    }

    private Map<String,String> buildValidWeatherIcons(){
        Map<String,String>validIcons = new HashMap<>();
        List<String>iconList = Arrays.asList(validWeatherIcon.split(","));
        ListIterator<String>it=iconList.listIterator();
        while (it.hasNext()) {
            String[] subStrings = it.next().split(":");
            validIcons.put(subStrings[0], subStrings[1]);
        }
        return validIcons;
    }
}

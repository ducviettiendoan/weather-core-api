package com.denison.project.transportation_recommendation.controller;

import com.denison.project.transportation_recommendation.model.CurrentWeatherResponse;
import com.denison.project.transportation_recommendation.model.ForecastWeatherResponse;
import com.denison.project.transportation_recommendation.service.GeoLocationService;
import com.denison.project.transportation_recommendation.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Slf4j
public class WeatherController {
    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/current")
    public ResponseEntity<List<CurrentWeatherResponse>> getCurrentWeatherByAddress(@RequestParam("lat") String lat, @RequestParam("lon") String lon){
        try{
            List<CurrentWeatherResponse> res = weatherService.getCurrentWeather(lat,lon);
            if (res!=null){
                log.info("Get current weather successfully", res.toString());
                return new ResponseEntity<>(res, HttpStatusCode.valueOf(200));
            }
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));

        }catch(Exception ex){
            log.error("Error getting current weather",ex);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/weather/forecast")
    public ResponseEntity<ForecastWeatherResponse> getWeatherForecast(@RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam("interval") String interval){
        try{
            ForecastWeatherResponse res = weatherService.getForecastWeather(lat,lon,interval);
            if (res!=null){
                return new ResponseEntity<>(res,HttpStatusCode.valueOf(200));
            }
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }catch (Exception ex){
            log.error("Error");
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));

        }
    }
}

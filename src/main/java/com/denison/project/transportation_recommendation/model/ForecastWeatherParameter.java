package com.denison.project.transportation_recommendation.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ForecastWeatherParameter implements HttpParameter{
    private Map<String,String> params = new HashMap<>();
    private String lat;
    private String lon;
    private String interval;

    public ForecastWeatherParameter(String lat, String lon, String interval){
        this.params.put("query", String.format("%s,%s",lat,lon));
        this.params.put("interval", interval);
    }
}

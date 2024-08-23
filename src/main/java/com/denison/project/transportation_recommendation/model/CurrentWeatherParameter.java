package com.denison.project.transportation_recommendation.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CurrentWeatherParameter implements HttpParameter{
    private Map<String,String>params = new HashMap<>();
    private String lat;
    private String lon;

    public CurrentWeatherParameter(String lat, String lon){
        this.params.put("query", String.format("%s,%s",lat,lon));
    }
}

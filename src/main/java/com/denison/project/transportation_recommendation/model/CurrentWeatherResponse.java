package com.denison.project.transportation_recommendation.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentWeatherResponse{
    private String temperature;
    private String feelTemperature;
    private String summary;
    private String humidity;
    private String wind;
}

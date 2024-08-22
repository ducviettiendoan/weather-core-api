package com.denison.project.transportation_recommendation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class ForecastWeatherResponse {
    String summary;
    String iconCode;
    String currentWeather;
    List<WeatherInterval>intervals;
}

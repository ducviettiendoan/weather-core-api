package com.denison.project.transportation_recommendation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class WeatherInterval {
    String time;
    String phrase;
    String iconCode;
    @Setter
    String weatherByIcon;
}

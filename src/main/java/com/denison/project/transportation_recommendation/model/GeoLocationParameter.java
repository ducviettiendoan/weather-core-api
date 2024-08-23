package com.denison.project.transportation_recommendation.model;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class GeoLocationParameter implements HttpParameter{
    private Map<String,String> params = new HashMap<>();

    public GeoLocationParameter(String locality, String countryCode, String postalCode){
        this.params.put("locality",locality);
        this.params.put("countryCode", countryCode);
        this.params.put("postalCode", postalCode);
    }
    public GeoLocationParameter(String query){
        this.params.put("query", query);
    }
}

package com.denison.project.transportation_recommendation.service;

import com.denison.project.transportation_recommendation.model.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class GeoLocationService {
    @Autowired
    public HTTPService httpService;

    @Value("${spring.application.azureWeatherURI}")
    private String weatherUrl;

    public ResponseEntity<String> getGeoByLocation(String locality, String countryRegion, String postalCode) throws IOException, InterruptedException, NoSuchFieldException {
        HttpResponse<String> res = httpService.apiCall(weatherUrl,"GET",new GeoLocation(locality,countryRegion,postalCode), "");
        return new ResponseEntity<String>(res.body(),HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<String> getGeoByAddress(String postalCode) throws IOException, InterruptedException, NoSuchFieldException {
        HttpResponse<String> res = httpService.apiCall(weatherUrl,"GET",new GeoLocation(postalCode), "");
        return new ResponseEntity<String>(res.body(),HttpStatusCode.valueOf(200));
    }
}

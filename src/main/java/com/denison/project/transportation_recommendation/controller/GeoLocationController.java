package com.denison.project.transportation_recommendation.controller;

import com.denison.project.transportation_recommendation.service.GeoLocationService;
import com.denison.project.transportation_recommendation.service.ParameterStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class GeoLocationController {
    @Autowired
    public GeoLocationService geoLocationService;

    @Autowired
    public ParameterStoreService parameterStoreService;

    @GetMapping(value="/geo")
    public ResponseEntity<String> getGeoByLocation(@RequestParam("locality") String locality, @RequestParam("countryCode") String countryRegion){
        try{
            ResponseEntity<String> res = geoLocationService.getGeoByLocation(locality, countryRegion);
            log.info("Get location successfully", res.toString());
            return res;
        }catch(Exception ex){
            log.error("Error getting location",ex);
            return new ResponseEntity<String>(HttpStatusCode.valueOf(500));
        }
    }
}

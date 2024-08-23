package com.denison.project.transportation_recommendation.controller;

import com.denison.project.transportation_recommendation.service.GeoLocationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class GeoLocationController {
    @Autowired
    public GeoLocationService geoLocationService;

    @GetMapping(value="/geo/address")
    public ResponseEntity<List<JsonNode>> getGeoByLocation(@RequestParam("query") String query){
        try{
            List<JsonNode> address = geoLocationService.getGeoByAddress(query);
            log.info("Get location successfully", address.toString());
            return new ResponseEntity<>(address,HttpStatusCode.valueOf(200));
        }catch(Exception ex){
            log.error("Error getting location",ex);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value="/geo/locality")
    public ResponseEntity<List<JsonNode>> getGeoByLocation(@RequestParam("locality") String locality, @RequestParam("countryCode") String countryRegion, @RequestParam("postalCode") String postalCode){
        try{
            List<JsonNode> address = geoLocationService.getGeoByLocation(locality, countryRegion, postalCode);
            log.info("Get location successfully", address.toString());
            return new ResponseEntity<>(address,HttpStatusCode.valueOf(200));
        }catch(Exception ex){
            log.error("Error getting location",ex);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}

package com.denison.project.transportation_recommendation.service;

import com.denison.project.transportation_recommendation.model.GeoLocationParameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class GeoLocationService {
    @Autowired
    public HTTPService httpService;

    @Value("${spring.application.azureWeatherGeoCodingURI}")
    private String weatherUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<JsonNode> getGeoByLocation(String locality, String countryRegion, String postalCode) throws IOException, InterruptedException, NoSuchFieldException {
        HttpResponse<String> geoList = httpService.apiCall(weatherUrl,"GET",new GeoLocationParameter(locality,countryRegion,postalCode), "");
        return extractGeoResponse(geoList);
    }

    public List<JsonNode> getGeoByAddress(String address) throws IOException, InterruptedException, NoSuchFieldException {
        HttpResponse<String> geoList = httpService.apiCall(weatherUrl,"GET",new GeoLocationParameter(address), "");
        return extractGeoResponse(geoList);
    }

    private List<JsonNode> extractGeoResponse(HttpResponse<String> geoList) throws JsonProcessingException {
        JsonNode res = objectMapper.readTree(geoList.body());
        log.info("Get location successfully", res.toString());
        JsonNode features = res.get("features");
        Set<Pair<String,String>> geos = new HashSet<>();
        List<JsonNode> foundAddress = new ArrayList<>();
        for (JsonNode feature:features){
            //extract geometry
            JsonNode geometry = feature.get("geometry").get("coordinates");
            Pair<String,String>coordinate = Pair.of(geometry.get(0).toString(),geometry.get(1).toString());
            if (!geos.contains(coordinate)){
                geos.add(coordinate);
                //extract address
                foundAddress.add(feature.get("properties").get("address"));
            }
        }
        if (foundAddress.isEmpty()) return null;
        return foundAddress;
    }
}

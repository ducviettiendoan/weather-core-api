package com.denison.project.transportation_recommendation.service;

import com.denison.project.transportation_recommendation.config.HttpClientConfig;
import com.denison.project.transportation_recommendation.model.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
@Slf4j
public class HTTPService {
    @Autowired
    private ParameterStoreService parameterStoreService;

    public HttpResponse<String> apiCall(String uri, String method, GeoLocation params, String body) throws IOException, InterruptedException, NoSuchFieldException {
        HttpClient httpClient = HttpClientConfig.getInstance().getClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(buildURI(uri, params.getParams())))
                .method(method, HttpRequest.BodyPublishers.ofString(body))
                .header("subscription-key",parameterStoreService.getAzureAPIKey())
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public String buildURI(String uri, Map<String,String> params) throws NoSuchFieldException {
        if (params.isEmpty()){return uri;}
        String newUri = uri;
        for (Map.Entry<String,String>entry:params.entrySet()){
            //strip all white space for RequestParam value
            newUri = String.format("%s&%s=%s",newUri,entry.getKey(),entry.getValue().replaceAll("\\s",""));
        }
        return newUri;
    }
}

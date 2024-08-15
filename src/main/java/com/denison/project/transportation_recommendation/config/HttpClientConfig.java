package com.denison.project.transportation_recommendation.config;

import lombok.Getter;
import java.net.http.HttpClient;


@Getter
public class HttpClientConfig{
    private static volatile HttpClientConfig instance;

    private HttpClient client;

    private HttpClientConfig(HttpClient newClient){
        this.client = newClient;
    }

    public static HttpClientConfig getInstance(){
        HttpClientConfig result = instance;
        if (result!=null){
            return result;
        }
        synchronized (HttpClientConfig.class){
            if (instance==null){
                instance = new HttpClientConfig(HttpClient.newHttpClient());
            }
            return instance;
        }
    }

}

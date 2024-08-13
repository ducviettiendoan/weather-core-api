package com.denison.project.transportation_recommendation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
@Slf4j
public class ParameterStoreService {
    @Autowired
    @Qualifier("secretsManagerClient")
    private SecretsManagerClient secretsManagerClient;

    @Value("${spring.application.aws.secret.apiKey}")
    private String azureAPIKey;

    public String getAzureAPIKey(){
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(azureAPIKey)
                .build();
        try{
            GetSecretValueResponse apiKey = secretsManagerClient.getSecretValue(request);
            return apiKey.secretString();
        }catch (Exception ex){
            log.error("Error retrieving API Key", ex);
        }
        return null;
    }

}

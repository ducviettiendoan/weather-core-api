package com.denison.project.transportation_recommendation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Slf4j
@Configuration
public class AWSConfig {
    @Value("${spring.application.aws.secret.profileName}")
    private String profileName;

    @Bean
    public SecretsManagerClient secretsManagerClient(){
        try{
            DefaultCredentialsProvider credentialsProvider = DefaultCredentialsProvider
                    .builder()
                    .profileName(profileName)
                    .build();
            SecretsManagerClient client = SecretsManagerClient.builder()
                    .region(Region.US_EAST_1)
                    .credentialsProvider(credentialsProvider)
                    .build();
            return client;
        }catch(Exception ex){
            log.error("Cannot verify AWS credential", ex);
        }
        return null;
    }
}

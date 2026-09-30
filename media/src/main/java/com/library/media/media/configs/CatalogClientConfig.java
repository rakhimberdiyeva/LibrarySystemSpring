package com.library.media.media.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class CatalogClientConfig {

    @Bean
    public RestClient catalogRestClient(RestClient.Builder builder, @Value("${services.catalog.url:http://localhost:8082}") String baseUrl) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(2));
        factory.setReadTimeout(Duration.ofSeconds(5));

        return builder
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }
}
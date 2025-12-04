package com.alloBank.service;

import com.alloBank.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("supported_currencies")
public class SupportedCurrenciesFetcher implements IDRDataFetcher {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    @Autowired
    public SupportedCurrenciesFetcher(RestTemplate restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    @Override
    public Map<String, Object> fetchData() {
        String url = appProperties.getBaseUrl() + "/currencies";
        return restTemplate.getForObject(url, Map.class);
    }
}
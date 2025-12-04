package com.alloBank.service;

import com.alloBank.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("historical_idr_usd")
public class HistoricalIDRUSDFetcher implements IDRDataFetcher {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    @Autowired
    public HistoricalIDRUSDFetcher(RestTemplate restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    @Override
    public Map<String, Object> fetchData() {
        String url = appProperties.getBaseUrl() + "/2024-01-01..2024-01-05?from=IDR&to=USD";
        return restTemplate.getForObject(url, Map.class);
    }
}
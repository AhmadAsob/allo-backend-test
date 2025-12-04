package com.alloBank.service;

import com.alloBank.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("latest_idr_rates")
public class LatestIDRRatesFetcher implements IDRDataFetcher {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;
    private final String githubUsername = "AhmadShobari";

    @Autowired
    public LatestIDRRatesFetcher(RestTemplate restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    @Override
    public Map<String, Object> fetchData() {
        String url = appProperties.getBaseUrl() + "/latest?base=IDR";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        Double rateUSD = rates.get("USD");

        double spreadFactor = calculateSpreadFactor(githubUsername.toLowerCase());

        double usdBuySpread = (1 / rateUSD) * (1 + spreadFactor);
        response.put("USD_BuySpread_IDR", usdBuySpread);

        return response;
    }

    private double calculateSpreadFactor(String username) {
        int sum = username.chars().sum();
        return (sum % 1000) / 100000.0;
    }
}
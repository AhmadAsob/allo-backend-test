package com.alloBank.service;

import com.alloBank.config.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class IDRFetchersUnitTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private AppProperties appProperties;

    private LatestIDRRatesFetcher latestIDRRatesFetcher;
    private SupportedCurrenciesFetcher supportedCurrenciesFetcher;
    private HistoricalIDRUSDFetcher historicalIDRUSDFetcher;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        appProperties = new AppProperties();
        appProperties.setBaseUrl("http://dummy-api.com");

        latestIDRRatesFetcher = new LatestIDRRatesFetcher(restTemplate, appProperties);
        supportedCurrenciesFetcher = new SupportedCurrenciesFetcher(restTemplate, appProperties);
        historicalIDRUSDFetcher = new HistoricalIDRUSDFetcher(restTemplate, appProperties);
    }

    @Test
    void testLatestIDRRatesFetcher() {
        String dummyResponse = """
            {
                "base": "IDR",
                "rates": {"USD": 0.000067}
            }
        """;

        mockServer.expect(requestTo(appProperties.getBaseUrl() + "/latest?base=IDR"))
                .andRespond(withSuccess(dummyResponse, MediaType.APPLICATION_JSON));

        Map<String, Object> result = latestIDRRatesFetcher.fetchData();

        assertThat(result).isNotNull();
        assertThat(result).containsKey("USD_BuySpread_IDR");
        assertThat(((Map<?, ?>) result.get("rates")).get("USD")).isEqualTo(0.000067);

        mockServer.verify();
    }

    @Test
    void testSupportedCurrenciesFetcher() {
        String dummyResponse = """
            {
                "USD": "United States Dollar",
                "EUR": "Euro",
                "JPY": "Japanese Yen"
            }
        """;

        mockServer.expect(requestTo(appProperties.getBaseUrl() + "/currencies"))
                .andRespond(withSuccess(dummyResponse, MediaType.APPLICATION_JSON));

        Map<String, Object> result = supportedCurrenciesFetcher.fetchData();

        assertThat(result).isNotNull();
        assertThat(result).containsKeys("USD", "EUR", "JPY");

        mockServer.verify();
    }

    @Test
    void testHistoricalIDRUSDFetcher() {
        String dummyResponse = """
            {
                "2024-01-01": {"USD": 0.000066},
                "2024-01-02": {"USD": 0.000065}
            }
        """;

        mockServer.expect(requestTo(appProperties.getBaseUrl() + "/2024-01-01..2024-01-05?from=IDR&to=USD"))
                .andRespond(withSuccess(dummyResponse, MediaType.APPLICATION_JSON));

        Map<String, Object> result = historicalIDRUSDFetcher.fetchData();

        assertThat(result).isNotNull();
        assertThat(result).containsKeys("2024-01-01", "2024-01-02");

        mockServer.verify();
    }
}

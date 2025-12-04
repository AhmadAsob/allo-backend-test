package com.alloBank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplateFactoryBean restTemplateFactoryBean() {
        return new RestTemplateFactoryBean();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateFactoryBean factoryBean) throws Exception {
        return factoryBean.getObject();
    }
}
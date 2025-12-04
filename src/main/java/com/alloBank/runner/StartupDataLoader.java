package com.alloBank.runner;

import com.alloBank.service.DataStore;
import com.alloBank.service.IDRDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StartupDataLoader implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DataStore dataStore;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) {
        Map<String, IDRDataFetcher> fetchers = context.getBeansOfType(IDRDataFetcher.class);
        fetchers.forEach((name, fetcher) -> {
            dataStore.put(name, fetcher.fetchData());
        });
    }
}
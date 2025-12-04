package com.alloBank.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataStore {

    private final Map<String, Map<String, Object>> store = new HashMap<>();

    public void put(String key, Map<String, Object> value) {
        store.put(key, Collections.unmodifiableMap(value));
    }

    public Map<String, Object> get(String key) {
        return store.get(key);
    }
}
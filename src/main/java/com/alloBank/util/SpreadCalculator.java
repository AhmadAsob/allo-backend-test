package com.alloBank.util;

public class SpreadCalculator {
    public static double calculateSpread(String githubUsername) {
        int sum = githubUsername.toLowerCase().chars().sum();
        return (sum % 1000) / 100000.0;
    }
}

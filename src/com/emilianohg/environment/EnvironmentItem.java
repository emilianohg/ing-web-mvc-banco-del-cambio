package com.emilianohg.environment;

public class EnvironmentItem {
    private String key;
    private String value;

    public EnvironmentItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

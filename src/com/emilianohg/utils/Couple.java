package com.emilianohg.utils;

public class Couple<K, V> {

    private K key;
    private V value;

    public Couple(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

package com.gfs.backend.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Pair<K, V> {
    private final K key;
    private final V value;

    public static <K, V> Pair<K, V> from(K key, V value) {
        return new Pair<>(key, value);
    }
}

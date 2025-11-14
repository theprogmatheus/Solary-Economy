package com.redeskyller.bukkit.solaryeconomy.util;

import java.util.HashMap;
import java.util.Map;

public class ArrayUtils {

    public static <T> Map<T, T> toMap(T[] array) {
        if (array.length % 2 != 0)
            throw new IllegalArgumentException("Array length must be even to form key-value pairs.");

        Map<T, T> map = new HashMap<>();
        for (int i = 0; i < array.length; i += 2)
            map.put(array[i], array[i + 1]);

        return map;
    }
}
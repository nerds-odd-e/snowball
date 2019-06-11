package com.odde.snowball.model.base;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Errors {
    private Map<String, String> map = new HashMap<>();
    public void put(String field, String message) {
        map.put(field, message);
    }

    @Override
    public String toString() {
        return map.entrySet().stream().map(e->String.format("{ %s:\"%s\" }", e.getKey(), e.getValue())).collect(Collectors.joining( "," ) );
    }
}

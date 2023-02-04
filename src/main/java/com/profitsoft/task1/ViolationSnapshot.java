package com.profitsoft.task1;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ViolationSnapshot {

    private Map<String, Double> map = new LinkedHashMap<>();

    public void fillMap(JSONArray arr) {
        for (Object obj : arr) {
            var violation = (JSONObject) obj;
            var type = (String) violation.get("type");
            var amount = (Double) violation.get("fine_amount");

            if (map.containsKey(type)) {
                map.put(type, map.get(type) + amount);
            } else {
                map.put(type, amount);
            }

        }
    }

    public void sortByValue() {
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        map = sortedMap;
    }

    public Iterator<String> iterator() {
        return map.keySet().iterator();
    }

    public Double getValue(String key) {
        return map.get(key);
    }

    public void add(String key, Double value) {
        map.put(key, value);
    }
}

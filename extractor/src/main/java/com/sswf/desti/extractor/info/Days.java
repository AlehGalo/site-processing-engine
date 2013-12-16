package com.sswf.desti.extractor.info;

import java.util.*;

import org.apache.commons.lang3.Validate;

/**
 * @author Alexey Grigorev
 */
public enum Days {
    SUNDAY("sunday", "sundays", "sun"),
    MONDAY("monday", "mondays", "mon"),
    TUESDAY("tuesday", "tuesdays", "tue"),
    WEDNESDAY("wednesday", "wednesdays", "wed"),
    THURSDAY("thursday", "thursdays", "thu"),
    FRIDAY("friday", "fridays", "fri"),
    SATURDAY("saturday", "saturdays", "sat");

    private static final Map<String, Days> INVERTED = createInvertedDaysMap();

    private static Map<String, Days> createInvertedDaysMap() {
        Map<String, Days> map = new LinkedHashMap<>();
        for (Days day : Days.values()) {
            for (String name : day.getNames()) {
                map.put(name, day);
            }
        }
        return map;
    }

    private List<String> names;

    private Days(String... names) {
        this.names = Arrays.asList(names);
    }

    public List<String> getNames() {
        return names;
    }

    public static boolean isDay(String text) {
        String key = text.toLowerCase(Locale.US);
        return INVERTED.containsKey(key);
    }

    public static Days fromString(String text) {
        String key = text.toLowerCase(Locale.US);
        Validate.isTrue(isDay(key));
        return INVERTED.get(key);
    }
}

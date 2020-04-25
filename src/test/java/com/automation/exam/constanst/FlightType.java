package com.automation.exam.constanst;

import java.util.HashMap;
import java.util.Map;

public enum FlightType {

    ROUND_TRIP("Roundtrip"),
    ONE_WAY("One way"),
    MULTI_CITY("Multi-city");

    private static final Map<String, FlightType> BY_LABEL = new HashMap<>();

    static {
        for(FlightType flight : values()) {
            BY_LABEL.put(flight.label, flight);
        }
    }

    public final String label;

    FlightType(String label) {
        this.label = label;
    }

    public static FlightType valueOfFlightType(String label) {
        return BY_LABEL.get(label);
    }

}

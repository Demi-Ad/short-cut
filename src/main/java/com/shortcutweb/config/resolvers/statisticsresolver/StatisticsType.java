package com.shortcutweb.config.resolvers.statisticsresolver;

import java.util.Arrays;

public enum StatisticsType {
    CURRENT,BETWEEN,SINGLE;

    public static StatisticsType of(String type) {
        return Arrays.stream(StatisticsType.values())
                .filter(statisticsType -> statisticsType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(StatisticsType.CURRENT);
    }
}

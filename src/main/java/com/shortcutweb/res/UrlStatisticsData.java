package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class UrlStatisticsData {
    private Map<String,Long> refererCount;
    private Map<String,Long> browserCount;
    private Map<String,Long> osCount;
    private Map<String,Long> deviceCount;
    private Map<String,Long> dateCount;
    private Map<String,Long> countryCount;
}

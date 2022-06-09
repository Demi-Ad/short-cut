package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class UrlStatisticsData {
    private Map<String,Long> referer;
    private Map<String,Long> browser;
    private Map<String,Long> os;
    private Map<String,Long> device;
    private Map<String,Long> date;
    private Map<String,Long> country;
}

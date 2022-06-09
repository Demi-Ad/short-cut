package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class UrlStatisticsResponse {
    private Long hitCount;
    private String originUrl;
    private String documentTitle;
    private UrlStatisticsData data;
    private LocalDate startDate;
    private LocalDate endDate;
}

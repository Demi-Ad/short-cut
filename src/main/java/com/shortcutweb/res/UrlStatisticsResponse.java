package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class UrlStatisticsResponse {
    private Long hitCount;
    private UrlStatisticsData data;
}

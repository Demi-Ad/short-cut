package com.shortcutweb.controller;

import com.shortcutweb.config.resolvers.Resolve;
import com.shortcutweb.config.resolvers.statisticsresolver.StatisticsQuery;
import com.shortcutweb.config.resolvers.statisticsresolver.StatisticsType;
import com.shortcutweb.res.UrlStatisticsResponse;
import com.shortcutweb.service.UrlStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlStatisticsController {

    private final UrlStatisticsService statisticsService;

    @GetMapping("/{param:^[a-zA-Z0-9]{6}-stat$}")
    public ResponseEntity<UrlStatisticsResponse> getUrlStatistics(@PathVariable String param,
                                                                  @Resolve StatisticsQuery query) {
        String uri = param.replace("-stat", "");
        UrlStatisticsResponse statistics = statisticsService.Statistics(uri, query);
        return ResponseEntity.ok(statistics);
    }
}
